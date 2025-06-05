/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidorLectuNoS;
import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Rafael Barajas Dorado
 */
public class ManejoCliente extends Thread {

    private final Socket socket;

    public ManejoCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String mensaje = entrada.readLine(); 

            if (mensaje != null && mensaje.startsWith("LOGIN:")) {
                String[] partes = mensaje.split(":");
                if (partes.length == 3) {
                    String usuario = partes[1];
                    String contrasena = partes[2];

                    if (validarCredenciales(usuario, contrasena)) {
                        salida.println("OK");
                    } else {
                        salida.println("ERROR");
                    }
                } else {
                    salida.println("ERROR");
                }
            } else {
                salida.println("ERROR");
            }

        } catch (IOException e) {
            System.err.println("Error en ManejoCliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar socket: " + e.getMessage());
            }
        }
    }

    private boolean validarCredenciales(String usuario, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, usuario);
            pst.setString(2, contrasena);
            ResultSet rs = pst.executeQuery();
            return rs.next(); // hay coincidencia
        } catch (SQLException e) {
            System.err.println("Error al validar credenciales: " + e.getMessage());
            return false;
        }
    }
}