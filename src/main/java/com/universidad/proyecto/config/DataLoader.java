package com.universidad.proyecto.config;

import com.universidad.proyecto.model.Student;
import com.universidad.proyecto.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Random;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(StudentRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                String[] firstNames = { "Ana", "Carlos", "Beatriz", "David", "Elena", "Fernando", "Gabriela", "Hugo",
                        "Isabel", "Javier" };
                String[] lastNames = { "Gomez", "Lopez", "Perez", "Rodriguez", "Martinez", "Sanchez", "Fernandez",
                        "Gonzalez", "Diaz", "Torres" };
                String[] programs = { "Ingenieria de Sistemas", "Derecho", "Medicina", "Arquitectura",
                        "Administracion" };

                Random random = new Random();

                for (int i = 0; i < 10; i++) {
                    String firstName = firstNames[i];
                    String lastName = lastNames[i];
                    String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + i + "@university.com";
                    String program = programs[random.nextInt(programs.length)];
                    String documentNumber = String.valueOf(100000000 + i);
                    LocalDate dateOfBirth = LocalDate.of(1995 + random.nextInt(10), 1 + random.nextInt(12),
                            1 + random.nextInt(28));

                    Student student = new Student();
                    student.setFirstName(firstName);
                    student.setLastName(lastName);
                    student.setEmail(email);
                    student.setProgram(program);
                    student.setDocumentNumber(documentNumber);
                    student.setDateOfBirth(dateOfBirth);

                    repository.save(student);
                }
                System.out.println("Base de datos poblada con 10 estudiantes de prueba.");
            }
        };
    }
}
