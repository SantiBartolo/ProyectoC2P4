package com.universidad.proyecto.repository;

import com.universidad.proyecto.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Student
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    /**
     * Busca un estudiante por email
     * @param email Email del estudiante
     * @return Optional con el estudiante si existe
     */
    Optional<Student> findByEmail(String email);
    
    /**
     * Verifica si existe un estudiante con el email dado
     * @param email Email a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByEmail(String email);
}

