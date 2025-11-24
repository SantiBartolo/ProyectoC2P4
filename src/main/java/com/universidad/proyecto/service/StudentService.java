package com.universidad.proyecto.service;

import com.universidad.proyecto.dto.StudentRequestDTO;
import com.universidad.proyecto.dto.StudentResponseDTO;
import com.universidad.proyecto.exception.BadRequestException;
import com.universidad.proyecto.exception.StudentNotFoundException;
import com.universidad.proyecto.model.Student;
import com.universidad.proyecto.repository.StudentRepository;
import com.universidad.proyecto.util.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la lógica de negocio de estudiantes
 */
@Service
@Transactional
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    
    @Autowired
    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }
    
    /**
     * Crea un nuevo estudiante
     * @param requestDTO DTO con los datos del estudiante
     * @return DTO con el estudiante creado
     */
    public StudentResponseDTO createStudent(StudentRequestDTO requestDTO) {
        try {
            // Validar que el email no exista
            if (studentRepository.existsByEmail(requestDTO.getEmail())) {
                throw new BadRequestException(
                    String.format(AppConstants.EMAIL_ALREADY_EXISTS, requestDTO.getEmail())
                );
            }
            
            // Mapear DTO a entidad
            Student student = modelMapper.map(requestDTO, Student.class);
            
            // Guardar en base de datos
            Student savedStudent = studentRepository.save(student);
            
            // Mapear entidad a DTO de respuesta
            return modelMapper.map(savedStudent, StudentResponseDTO.class);
            
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al crear el estudiante: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene todos los estudiantes
     * @return Lista de DTOs de estudiantes
     */
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudents() {
        try {
            return studentRepository.findAll()
                    .stream()
                    .map(student -> modelMapper.map(student, StudentResponseDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BadRequestException("Error al obtener los estudiantes: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene un estudiante por ID
     * @param id ID del estudiante
     * @return DTO del estudiante
     */
    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(Long id) {
        try {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new StudentNotFoundException(
                        String.format(AppConstants.STUDENT_NOT_FOUND, id)
                    ));
            
            return modelMapper.map(student, StudentResponseDTO.class);
        } catch (StudentNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al obtener el estudiante: " + e.getMessage(), e);
        }
    }
    
    /**
     * Actualiza un estudiante existente
     * @param id ID del estudiante a actualizar
     * @param requestDTO DTO con los nuevos datos
     * @return DTO con el estudiante actualizado
     */
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO requestDTO) {
        try {
            // Buscar el estudiante
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new StudentNotFoundException(
                        String.format(AppConstants.STUDENT_NOT_FOUND, id)
                    ));
            
            // Validar que si cambia el email, no exista otro estudiante con ese email
            if (!student.getEmail().equals(requestDTO.getEmail()) && 
                studentRepository.existsByEmail(requestDTO.getEmail())) {
                throw new BadRequestException(
                    String.format(AppConstants.EMAIL_ALREADY_EXISTS, requestDTO.getEmail())
                );
            }
            
            // Actualizar campos
            student.setFirstName(requestDTO.getFirstName());
            student.setLastName(requestDTO.getLastName());
            student.setEmail(requestDTO.getEmail());
            student.setDateOfBirth(requestDTO.getDateOfBirth());
            student.setProgram(requestDTO.getProgram());
            student.setDocumentNumber(requestDTO.getDocumentNumber());
            
            // Guardar cambios
            Student updatedStudent = studentRepository.save(student);
            
            return modelMapper.map(updatedStudent, StudentResponseDTO.class);
            
        } catch (StudentNotFoundException | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al actualizar el estudiante: " + e.getMessage(), e);
        }
    }
    
    /**
     * Elimina un estudiante por ID
     * @param id ID del estudiante a eliminar
     */
    public void deleteStudent(Long id) {
        try {
            if (!studentRepository.existsById(id)) {
                throw new StudentNotFoundException(
                    String.format(AppConstants.STUDENT_NOT_FOUND, id)
                );
            }
            
            studentRepository.deleteById(id);
        } catch (StudentNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al eliminar el estudiante: " + e.getMessage(), e);
        }
    }
    
    /**
     * Filtra estudiantes por programa usando Stream API
     * @param program Programa a filtrar
     * @return Lista de estudiantes del programa
     */
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getStudentsByProgram(String program) {
        try {
            return studentRepository.findAll()
                    .stream()
                    .filter(student -> student.getProgram() != null && 
                                      student.getProgram().equalsIgnoreCase(program))
                    .map(student -> modelMapper.map(student, StudentResponseDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BadRequestException("Error al filtrar estudiantes: " + e.getMessage(), e);
        }
    }
    
    /**
     * Ordena estudiantes por apellido usando Stream API
     * @return Lista de estudiantes ordenados por apellido
     */
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudentsOrderedByLastName() {
        try {
            return studentRepository.findAll()
                    .stream()
                    .sorted((s1, s2) -> s1.getLastName().compareToIgnoreCase(s2.getLastName()))
                    .map(student -> modelMapper.map(student, StudentResponseDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BadRequestException("Error al ordenar estudiantes: " + e.getMessage(), e);
        }
    }
}

