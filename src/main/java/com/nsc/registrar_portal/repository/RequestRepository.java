package com.nsc.registrar_portal.repository;

import com.nsc.registrar_portal.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Import this
import java.util.List;

@Repository // <--- ADD THIS
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByStatus(String status);
    Request findByTransactionCode(String transactionCode);
    List<Request> findByStudent_StudentId(String studentId);
}