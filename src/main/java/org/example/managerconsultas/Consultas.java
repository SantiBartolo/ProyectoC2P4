package org.example.managerconsultas;


import org.example.conexion.Conexion;
import org.example.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Consultas {

    public void crearTablaEstudiantes() {
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(Query.CREATE_TABLE_ESTUDIANTES);
            System.out.println("Tabla Estudiante creada o ya existía.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void crearTablaProfesores() {
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(Query.CREATE_TABLE_PROFESOR);
            System.out.println("Tabla Profesores creada o ya existía.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearTablaClases_asignadas() {
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(Query.CREATE_TABLE_CLASES_ASIGNADAS);
            System.out.println("Tabla Clase Asignada creada o ya existía.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insertarUsuario(String nombre, String email) {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(Query.INSERT_USUARIO)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            System.out.println("Usuario insertado: " + nombre);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarUsuarios() {
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(Query.SELECT_ALL)) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("nombre") + " | " +
                        rs.getString("email")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
