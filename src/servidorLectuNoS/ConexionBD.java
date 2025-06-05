/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidorLectuNoS;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Rafael Barajas Dorado
 */
public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/lectunos";
    private static final String USUARIO = "lectunos";
    private static final String CONTRASENA = "lectunos";

    public static Connection getConexion() {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            return null;
        }
    }

}