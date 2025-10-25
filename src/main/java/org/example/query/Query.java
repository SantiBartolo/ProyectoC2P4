package org.example.query;

public class Query {
        public static final String CREATE_TABLE_ESTUDIANTES =

                        "CREATE TABLE IF NOT EXISTS Estudiantes (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        "nombre TEXT NOT NULL, " +
                                        "apellido TEXT NOT NULL, " +
                                        "fecha_nacimiento TEXT NOT NULL, " +
                                        "email TEXT  NOT NULL" +
                                        ");";

        public static final String CREATE_TABLE_PROFESOR =

                        "CREATE TABLE IF NOT EXISTS Profesor (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        "nombre TEXT NOT NULL, " +
                                        "apellido TEXT NOT NULL, " +
                                        "especialidad TEXT NOT NULL, " +
                                        "email TEXT  NOT NULL" +
                                        ");";

        public static final String CREATE_TABLE_CLASES_ASIGNADAS =

                        "CREATE TABLE IF NOT EXISTS Clases_Asigandas (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        "nombre TEXT NOT NULL, " +
                                        "horario TEXT NOT NULL, " +
                                        "id_Estudiantes INTEGER,\n" +
                                        "id_Profesores INTEGER,\n" +
                                        "FOREIGN KEY (id_Estudiantes) REFERENCES Estudiantes(id)\n" +
                                        "FOREIGN KEY (id_Profesores) REFERENCES Profesor(id)\n" +
                                        ");";

        public static final String INSERT_USUARIO = "INSERT INTO usuario(nombre, email) VALUES(?, ?);";

        public static final String SELECT_ALL = "SELECT * FROM usuario;";
}
