package com.api.devmed.repositories;

import com.api.devmed.model.entities.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByIdAndAtivoTrue(Long id);
    Page<Paciente> findAllByAtivoTrue(Pageable pageable);
}
