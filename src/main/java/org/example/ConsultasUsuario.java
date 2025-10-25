package org.example;

import org.example.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultasUsuario {

    // public boolean insertar(String nombre, String email) {
    // String sql = "INSERT INTO usuario (nombre, email) VALUES (?, ?)";
    // try (Connection conn = Conexion.getConnection();
    // PreparedStatement stmt = conn.prepareStatement(sql)) {
    // stmt.setString(1, nombre);
    // stmt.setString(2, email);
    // return stmt.executeUpdate() > 0;
    // } catch (SQLException e) {
    // System.out.println("Error insertando usuario: " + e.getMessage());
    // return false;
    // }
    // }

    // public List<String> listar() {
    // List<String> usuarios = new ArrayList<>();
    // String sql = "SELECT * FROM usuario";
    // try (Connection conn = Conexion.getConnection();
    // PreparedStatement stmt = conn.prepareStatement(sql);
    // ResultSet rs = stmt.executeQuery()) {
    // while (rs.next()) {
    // usuarios.add(rs.getInt("id") + " - " +
    // rs.getString("nombre") + " - " +
    // rs.getString("email"));
    // }
    // } catch (SQLException e) {
    // System.out.println("Error listando usuarios: " + e.getMessage());
    // }
    // return usuarios;
    // }

    // public boolean actualizar(int id, String nombre, String email, String
    // Estudiante) {
    // String sql = "UPDATE usuario SET nombre=?, email=? WHERE id=?";
    // try (Connection conn = Conexion.getConnection();
    // PreparedStatement stmt = conn.prepareStatement(sql)) {
    // stmt.setString(1, nombre);
    // stmt.setString(2, email);
    // stmt.setInt(3, id);
    // return stmt.executeUpdate() > 0;
    // } catch (SQLException e) {
    // System.out.println("Error actualizando usuario: " + e.getMessage());
    // return false;
    // }
    // }

    // public boolean eliminar(int id) {
    // String sql = "DELETE FROM usuario WHERE id=?";
    // try (Connection conn = Conexion.getConnection();
    // PreparedStatement stmt = conn.prepareStatement(sql)) {
    // stmt.setInt(1, id);
    // return stmt.executeUpdate() > 0;
    // } catch (SQLException e) {
    // System.out.println("Error eliminando usuario: " + e.getMessage());
    // return false;
    // }
    // }

    // CRUD for Estudiantes
    public boolean insertarEstudiante(String nombre, String apellido, String fechaNacimiento, String email) {
        String sql = "INSERT INTO Estudiantes (nombre, apellido, fecha_nacimiento, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, fechaNacimiento);
            stmt.setString(4, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error insertando estudiante: " + e.getMessage());
            return false;
        }
    }

    public List<String> listarEstudiantes() {
        List<String> estudiantes = new ArrayList<>();
        String sql = "SELECT * FROM Estudiantes";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                estudiantes.add(rs.getInt("id") + " - " +
                        rs.getString("nombre") + " - " +
                        rs.getString("apellido") + " - " +
                        rs.getString("fecha_nacimiento") + " - " +
                        rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Error listando estudiantes: " + e.getMessage());
        }
        return estudiantes;
    }

    public boolean actualizarEstudiante(int id, String nombre, String apellido, String fechaNacimiento, String email) {
        String sql = "UPDATE Estudiantes SET nombre=?, apellido=?, fecha_nacimiento=?, email=? WHERE id=?";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, fechaNacimiento);
            stmt.setString(4, email);
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizando estudiante: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarEstudiante(int id) {
        String sql = "DELETE FROM Estudiantes WHERE id=?";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminando estudiante: " + e.getMessage());
            return false;
        }
    }

    // CRUD for Profesor
    public boolean insertarProfesor(String nombre, String apellido, String especialidad, String email) {
        String sql = "INSERT INTO Profesor (nombre, apellido, especialidad, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, especialidad);
            stmt.setString(4, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error insertando profesor: " + e.getMessage());
            return false;
        }
    }

    public List<String> listarProfesores() {
        List<String> profesores = new ArrayList<>();
        String sql = "SELECT * FROM Profesor";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                profesores.add(rs.getInt("id") + " - " +
                        rs.getString("nombre") + " - " +
                        rs.getString("apellido") + " - " +
                        rs.getString("especialidad") + " - " +
                        rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Error listando profesores: " + e.getMessage());
        }
        return profesores;
    }

    public boolean actualizarProfesor(int id, String nombre, String apellido, String especialidad, String email) {
        String sql = "UPDATE Profesor SET nombre=?, apellido=?, especialidad=?, email=? WHERE id=?";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, especialidad);
            stmt.setString(4, email);
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizando profesor: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarProfesor(int id) {
        String sql = "DELETE FROM Profesor WHERE id=?";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminando profesor: " + e.getMessage());
            return false;
        }
    }

    // CRUD for Clases_Asignadas
    public boolean insertarClaseAsignada(String nombre, String horario, int idEstudiante, int idProfesor) {
        String sql = "INSERT INTO Clases_Asigandas (nombre, horario, id_Estudiantes, id_Profesores) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, horario);
            stmt.setInt(3, idEstudiante);
            stmt.setInt(4, idProfesor);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error insertando clase asignada: " + e.getMessage());
            return false;
        }
    }

    public List<String> listarClasesAsignadas() {
        List<String> clases = new ArrayList<>();
        String sql = "SELECT * FROM Clases_Asigandas";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clases.add(rs.getInt("id") + " - " +
                        rs.getString("nombre") + " - " +
                        rs.getString("horario") + " - " +
                        rs.getInt("id_Estudiantes") + " - " +
                        rs.getInt("id_Profesores"));
            }
        } catch (SQLException e) {
            System.out.println("Error listando clases asignadas: " + e.getMessage());
        }
        return clases;
    }

    public boolean actualizarClaseAsignada(int id, String nombre, String horario, int idEstudiante, int idProfesor) {
        String sql = "UPDATE Clases_Asigandas SET nombre=?, horario=?, id_Estudiantes=?, id_Profesores=? WHERE id=?";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, horario);
            stmt.setInt(3, idEstudiante);
            stmt.setInt(4, idProfesor);
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizando clase asignada: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarClaseAsignada(int id) {
        String sql = "DELETE FROM Clases_Asigandas WHERE id=?";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminando clase asignada: " + e.getMessage());
            return false;
        }
    }
}
