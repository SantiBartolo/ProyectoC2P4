package com.universidad.proyecto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.universidad.proyecto.dto.StudentRequestDTO;
import com.universidad.proyecto.dto.StudentResponseDTO;
import com.universidad.proyecto.exception.GlobalExceptionHandler;
import com.universidad.proyecto.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias para StudentController usando MockMvc
 */
class StudentControllerTest {
    
    private MockMvc mockMvc;
    
    private StudentServiceStub studentService;
    
    private ObjectMapper objectMapper;
    
    private StudentRequestDTO requestDTO;
    private StudentResponseDTO responseDTO;
    
    @BeforeEach
    void setUp() {
        studentService = new StudentServiceStub();
        StudentController controller = new StudentController(studentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        requestDTO = new StudentRequestDTO();
        requestDTO.setFirstName("Ana");
        requestDTO.setLastName("Gomez");
        requestDTO.setEmail("ana.gomez@email.com");
        requestDTO.setDateOfBirth(LocalDate.of(2003, 5, 12));
        requestDTO.setProgram("Ingeniería de Sistemas");
        requestDTO.setDocumentNumber("123456789");
        
        responseDTO = new StudentResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setFirstName("Ana");
        responseDTO.setLastName("Gomez");
        responseDTO.setEmail("ana.gomez@email.com");
        responseDTO.setDateOfBirth(LocalDate.of(2003, 5, 12));
        responseDTO.setProgram("Ingeniería de Sistemas");
        responseDTO.setDocumentNumber("123456789");
    }
    
    @Test
    void testCreateStudent_Success() throws Exception {
        // Arrange
        AtomicBoolean invoked = new AtomicBoolean(false);
        studentService.onCreate(dto -> {
            invoked.set(true);
            return responseDTO;
        });
        
        // Act & Assert
        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Ana"))
                .andExpect(jsonPath("$.lastName").value("Gomez"))
                .andExpect(jsonPath("$.email").value("ana.gomez@email.com"));
        
        org.junit.jupiter.api.Assertions.assertTrue(invoked.get(), "El servicio debe invocarse para crear");
    }
    
    @Test
    void testCreateStudent_ValidationError() throws Exception {
        // Arrange - DTO inválido (sin nombre)
        StudentRequestDTO invalidDTO = new StudentRequestDTO();
        invalidDTO.setLastName("Gomez");
        invalidDTO.setEmail("ana.gomez@email.com");
        
        // Act & Assert
        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testGetAllStudents_Success() throws Exception {
        // Arrange
        StudentResponseDTO responseDTO2 = new StudentResponseDTO();
        responseDTO2.setId(2L);
        responseDTO2.setFirstName("Juan");
        responseDTO2.setLastName("Perez");
        
        List<StudentResponseDTO> students = Arrays.asList(responseDTO, responseDTO2);
        studentService.onList(() -> students);
        
        // Act & Assert
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }
    
    @Test
    void testGetStudentById_Success() throws Exception {
        // Arrange
        studentService.onGetById(id -> responseDTO);
        
        // Act & Assert
        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Ana"))
                .andExpect(jsonPath("$.lastName").value("Gomez"));
    }
    
    @Test
    void testUpdateStudent_Success() throws Exception {
        // Arrange
        StudentResponseDTO updatedResponse = new StudentResponseDTO();
        updatedResponse.setId(1L);
        updatedResponse.setFirstName("Ana Maria");
        updatedResponse.setLastName("Gomez");
        updatedResponse.setEmail("ana.gomez@email.com");
        
        studentService.onUpdate((id, dto) -> updatedResponse);
        
        // Act & Assert
        mockMvc.perform(put("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Ana Maria"));
    }
    
    @Test
    void testDeleteStudent_Success() throws Exception {
        // Arrange
        AtomicBoolean deleted = new AtomicBoolean(false);
        studentService.onDelete(id -> deleted.set(true));
        
        // Act & Assert
        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());
        
        org.junit.jupiter.api.Assertions.assertTrue(deleted.get(), "El servicio debe invocar eliminación");
    }
    
    /**
     * Stub sencillo para controlar las respuestas del servicio en las pruebas
     */
    private static class StudentServiceStub extends StudentService {
        
        private Function<StudentRequestDTO, StudentResponseDTO> createHandler = dto -> {
            throw new IllegalStateException("Handler de creación no configurado");
        };
        private Supplier<List<StudentResponseDTO>> listHandler = () -> {
            throw new IllegalStateException("Handler de listado no configurado");
        };
        private Function<Long, StudentResponseDTO> getByIdHandler = id -> {
            throw new IllegalStateException("Handler de búsqueda por id no configurado");
        };
        private BiFunction<Long, StudentRequestDTO, StudentResponseDTO> updateHandler = (id, dto) -> {
            throw new IllegalStateException("Handler de actualización no configurado");
        };
        private Consumer<Long> deleteHandler = id -> {
            throw new IllegalStateException("Handler de eliminación no configurado");
        };
        
        StudentServiceStub() {
            super(null, new ModelMapper());
        }
        
        void onCreate(Function<StudentRequestDTO, StudentResponseDTO> handler) {
            this.createHandler = handler;
        }
        
        void onList(Supplier<List<StudentResponseDTO>> handler) {
            this.listHandler = handler;
        }
        
        void onGetById(Function<Long, StudentResponseDTO> handler) {
            this.getByIdHandler = handler;
        }
        
        void onUpdate(BiFunction<Long, StudentRequestDTO, StudentResponseDTO> handler) {
            this.updateHandler = handler;
        }
        
        void onDelete(Consumer<Long> handler) {
            this.deleteHandler = handler;
        }
        
        @Override
        public StudentResponseDTO createStudent(StudentRequestDTO requestDTO) {
            return createHandler.apply(requestDTO);
        }
        
        @Override
        public List<StudentResponseDTO> getAllStudents() {
            return listHandler.get();
        }
        
        @Override
        public StudentResponseDTO getStudentById(Long id) {
            return getByIdHandler.apply(id);
        }
        
        @Override
        public StudentResponseDTO updateStudent(Long id, StudentRequestDTO requestDTO) {
            return updateHandler.apply(id, requestDTO);
        }
        
        @Override
        public void deleteStudent(Long id) {
            deleteHandler.accept(id);
        }
    }
}

