package com.nsc.registrar_portal.repository;

import com.nsc.registrar_portal.entity.Registrar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Import this
import java.util.Optional;

@Repository // <--- ADD THIS
public interface RegistrarRepository extends JpaRepository<Registrar, Long> {
    Optional<Registrar> findByRegistrarId(String registrarId);
}