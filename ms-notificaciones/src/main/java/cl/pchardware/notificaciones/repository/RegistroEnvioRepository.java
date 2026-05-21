package cl.pchardware.notificaciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.notificaciones.model.RegistroEnvio;

@Repository
public interface RegistroEnvioRepository extends JpaRepository<RegistroEnvio, Integer> {
    
    // Busca todos los registros de envío asociados a un mensaje específico
    List<RegistroEnvio> findByMensaje_IdMensaje(Integer idMensaje);
    
    // Opcional: Busca registros por proveedor SMTP
    List<RegistroEnvio> findByProveedorSmtp(String proveedorSmtp);
}