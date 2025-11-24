package com.universidad.proyecto.service;

import com.universidad.proyecto.dto.StudentRequestDTO;
import com.universidad.proyecto.dto.StudentResponseDTO;
import com.universidad.proyecto.exception.BadRequestException;
import com.universidad.proyecto.exception.StudentNotFoundException;
import com.universidad.proyecto.model.Student;
import com.universidad.proyecto.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para StudentService
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    
    @Mock
    private StudentRepository studentRepository;
    
    private StudentService studentService;
    private ModelMapper modelMapper;
    
    private Student student;
    private StudentRequestDTO requestDTO;
    
    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        studentService = new StudentService(studentRepository, modelMapper);
        
        student = new Student();
        student.setId(1L);
        student.setFirstName("Ana");
        student.setLastName("Gomez");
        student.setEmail("ana.gomez@email.com");
        student.setDateOfBirth(LocalDate.of(2003, 5, 12));
        student.setProgram("Ingeniería de Sistemas");
        student.setDocumentNumber("123456789");
        
        requestDTO = new StudentRequestDTO();
        requestDTO.setFirstName("Ana");
        requestDTO.setLastName("Gomez");
        requestDTO.setEmail("ana.gomez@email.com");
        requestDTO.setDateOfBirth(LocalDate.of(2003, 5, 12));
        requestDTO.setProgram("Ingeniería de Sistemas");
        requestDTO.setDocumentNumber("123456789");
        
    }
    
    @Test
    void testCreateStudent_Success() {
        // Arrange
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        
        // Act
        StudentResponseDTO result = studentService.createStudent(requestDTO);
        
        // Assert
        assertNotNull(result);
        assertEquals("Ana", result.getFirstName());
        assertEquals("Gomez", result.getLastName());
        verify(studentRepository, times(1)).existsByEmail(anyString());
        verify(studentRepository, times(1)).save(any(Student.class));
    }
    
    @Test
    void testCreateStudent_EmailAlreadyExists() {
        // Arrange
        when(studentRepository.existsByEmail(anyString())).thenReturn(true);
        
        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            studentService.createStudent(requestDTO);
        });
        
        assertTrue(exception.getMessage().contains("ya está registrado"));
        verify(studentRepository, never()).save(any(Student.class));
    }
    
    @Test
    void testGetStudentById_Success() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        
        // Act
        StudentResponseDTO result = studentService.getStudentById(1L);
        
        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Ana", result.getFirstName());
        verify(studentRepository, times(1)).findById(1L);
    }
    
    @Test
    void testGetStudentById_NotFound() {
        // Arrange
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class, () -> {
            studentService.getStudentById(999L);
        });
        
        assertTrue(exception.getMessage().contains("no encontrado"));
        verify(studentRepository, times(1)).findById(999L);
    }
    
    @Test
    void testGetAllStudents_Success() {
        // Arrange
        Student student2 = new Student();
        student2.setId(2L);
        student2.setFirstName("Juan");
        student2.setLastName("Perez");
        
        List<Student> students = Arrays.asList(student, student2);
        when(studentRepository.findAll()).thenReturn(students);
        
        // Act
        List<StudentResponseDTO> result = studentService.getAllStudents();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findAll();
    }
    
    @Test
    void testUpdateStudent_Success() {
        // Arrange
        StudentRequestDTO updateDTO = new StudentRequestDTO();
        updateDTO.setFirstName("Ana Maria");
        updateDTO.setLastName("Gomez");
        updateDTO.setEmail("ana.gomez@email.com");
        updateDTO.setDateOfBirth(LocalDate.of(2003, 5, 12));
        updateDTO.setProgram("Ingeniería de Sistemas");
        updateDTO.setDocumentNumber("123456789");
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        
        // Act
        StudentResponseDTO result = studentService.updateStudent(1L, updateDTO);
        
        // Assert
        assertNotNull(result);
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }
    
    @Test
    void testUpdateStudent_NotFound() {
        // Arrange
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class, () -> {
            studentService.updateStudent(999L, requestDTO);
        });
        
        assertTrue(exception.getMessage().contains("no encontrado"));
        verify(studentRepository, never()).save(any(Student.class));
    }
    
    @Test
    void testDeleteStudent_Success() {
        // Arrange
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);
        
        // Act
        studentService.deleteStudent(1L);
        
        // Assert
        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void testDeleteStudent_NotFound() {
        // Arrange
        when(studentRepository.existsById(999L)).thenReturn(false);
        
        // Act & Assert
        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class, () -> {
            studentService.deleteStudent(999L);
        });
        
        assertTrue(exception.getMessage().contains("no encontrado"));
        verify(studentRepository, never()).deleteById(anyLong());
    }
}

