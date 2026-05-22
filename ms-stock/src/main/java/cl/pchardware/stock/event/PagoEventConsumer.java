package cl.pchardware.stock.event;

import cl.pchardware.common.event.PagoAprobadoEvent;
import cl.pchardware.stock.client.PedidoClient;
import cl.pchardware.stock.dto.DetallePedido;
import cl.pchardware.stock.model.Bodega;
import cl.pchardware.stock.model.Inventario;
import cl.pchardware.stock.model.Movimiento;
import cl.pchardware.stock.repository.InventarioRepository;
import cl.pchardware.stock.repository.MovimientoRepository;
import cl.pchardware.stock.service.BodegaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class PagoEventConsumer {

    private final InventarioRepository inventarioRepository;
    private final MovimientoRepository movimientoRepository;
    private final BodegaService bodegaService;
    private final PedidoClient pedidoClient;

    @Transactional
    @KafkaListener(
        topics = "pago.aprobado",
        groupId = "ms-stock",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void onPagoAprobado(PagoAprobadoEvent event) {
        log.info("Recibido PagoAprobadoEvent → idPedido={} monto={}",
                event.getIdPedido(), event.getMontoClp());

        try {
            // 1. Obtener la bodega principal
            Bodega bodegaVenta = bodegaService.getBodegaByCodigo("NUEVOS");

            // 2. Consultar a ms-pedidos los detalles usando el Integer idPedido
            List<DetallePedido> detalles = pedidoClient.getDetallesByPedidoId(event.getIdPedido());

            if (detalles == null || detalles.isEmpty()) {
                log.warn("El pedido {} consultado no tiene items asociados o no fue encontrado.", event.getIdPedido());
                return;
            }

            // 3. Iterar los detalles y descontar
            for (DetallePedido item : detalles) {
                
                Inventario inventario = inventarioRepository
                        .findByBodegaIdBodegaAndSkuProducto(bodegaVenta.getIdBodega(), item.getSkuProducto())
                        .orElseThrow(() -> new RuntimeException("No existe inventario para el SKU: " + item.getSkuProducto()));

                if (inventario.getCantidad() < item.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente en bodega NUEVOS para el SKU: " + item.getSkuProducto());
                }

                // Descontar la cantidad
                inventario.setCantidad(inventario.getCantidad() - item.getCantidad());
                inventarioRepository.save(inventario);

                // Registrar el movimiento con los campos correctos del modelo
                Movimiento movimiento = Movimiento.builder()
                        .inventario(inventario)
                        .tipoMovimiento("SALIDA") 
                        .cantidadVariacion(item.getCantidad())
                        .build();
                
                movimientoRepository.save(Objects.requireNonNull(movimiento));
            }
            
            log.info("Descuento de inventario completado con éxito para pedido {}", event.getIdPedido());
            
        } catch (Exception e) {
            log.error("Error procesando descuento para el pedido {}: {}", event.getIdPedido(), e.getMessage());
            throw e; 
        }
    }
}