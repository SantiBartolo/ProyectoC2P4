package com.universidad.proyecto.controller;

import com.universidad.proyecto.dto.StudentRequestDTO;
import com.universidad.proyecto.dto.StudentResponseDTO;
import com.universidad.proyecto.service.StudentService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controlador MVC con Thymeleaf para interactuar con el CRUD vía interfaz web.
 */
@Controller
@RequestMapping("/students")
public class StudentViewController {

    private final StudentService studentService;
    private final ModelMapper modelMapper;

    public StudentViewController(StudentService studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String listStudents(Model model) {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new StudentRequestDTO());
        model.addAttribute("formTitle", "Registrar nuevo estudiante");
        model.addAttribute("formAction", "/students");
        model.addAttribute("isEdit", false);
        return "students/form";
    }

    @PostMapping
    public String createStudent(@Valid StudentRequestDTO student,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "students/form";
        }
        studentService.createStudent(student);
        redirectAttributes.addFlashAttribute("successMessage", "Estudiante creado correctamente.");
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            StudentResponseDTO responseDTO = studentService.getStudentById(id);
            StudentRequestDTO student = modelMapper.map(responseDTO, StudentRequestDTO.class);
            model.addAttribute("studentId", id);
            model.addAttribute("student", student);
            model.addAttribute("formTitle", "Editar estudiante");
            model.addAttribute("formAction", "/students/" + id + "/update");
            model.addAttribute("isEdit", true);
            return "students/form";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/students";
        }
    }

    @PostMapping("/{id}/update")
    public String updateStudent(@PathVariable Long id,
                                @Valid StudentRequestDTO student,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("studentId", id);
            model.addAttribute("formTitle", "Editar estudiante");
            model.addAttribute("formAction", "/students/" + id + "/update");
            model.addAttribute("isEdit", true);
            return "students/form";
        }
        try {
            studentService.updateStudent(id, student);
            redirectAttributes.addFlashAttribute("successMessage", "Estudiante actualizado correctamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/students";
    }

    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("successMessage", "Estudiante eliminado correctamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/students";
    }
}

