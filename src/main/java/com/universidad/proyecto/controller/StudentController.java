package com.universidad.proyecto.controller;

import com.universidad.proyecto.dto.StudentRequestDTO;
import com.universidad.proyecto.dto.StudentResponseDTO;
import com.universidad.proyecto.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar estudiantes
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {
    
    private final StudentService studentService;
    
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    
    /**
     * Crea un nuevo estudiante
     * POST /api/students
     */
    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(
            @Valid @RequestBody StudentRequestDTO requestDTO) {
        StudentResponseDTO response = studentService.createStudent(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Obtiene todos los estudiantes
     * GET /api/students
     */
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    /**
     * Obtiene un estudiante por ID
     * GET /api/students/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        StudentResponseDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
    
    /**
     * Actualiza un estudiante existente
     * PUT /api/students/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDTO requestDTO) {
        StudentResponseDTO response = studentService.updateStudent(id, requestDTO);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Elimina un estudiante por ID
     * DELETE /api/students/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Genera un certificado en PDF para un estudiante (BONUS)
     * POST /api/students/{id}/certificate
     */
    @PostMapping(value = "/{id}/certificate", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateCertificate(@PathVariable Long id) {
        try {
            StudentResponseDTO studentDTO = studentService.getStudentById(id);
            
            // Convertir DTO a entidad para el generador de PDF
            com.universidad.proyecto.model.Student student = new com.universidad.proyecto.model.Student();
            student.setId(studentDTO.getId());
            student.setFirstName(studentDTO.getFirstName());
            student.setLastName(studentDTO.getLastName());
            student.setEmail(studentDTO.getEmail());
            student.setDateOfBirth(studentDTO.getDateOfBirth());
            student.setProgram(studentDTO.getProgram());
            student.setDocumentNumber(studentDTO.getDocumentNumber());
            
            byte[] pdfBytes = com.universidad.proyecto.util.PDFGeneratorUtil.generateCertificate(student);
            
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=certificado_" + id + ".pdf")
                    .body(pdfBytes);
                    
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el certificado: " + e.getMessage(), e);
        }
    }
}

