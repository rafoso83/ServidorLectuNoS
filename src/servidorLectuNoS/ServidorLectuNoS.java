/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package servidorLectuNoS;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author Rafael Barajas Dorado
 */
public class ServidorLectuNoS {

    private static final int PUERTO = 5050;

    public static void main(String[] args) {
        new ServidorLectuNoS().iniciarServidor();
    }

    public void iniciarServidor() {
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            while (true) {
                Socket cliente = servidor.accept(); // Espera cliente
                System.out.println("Nuevo cliente conectado: " + cliente.getInetAddress());

              
                ManejoCliente manejador = new ManejoCliente(cliente);
                new Thread(manejador).start();
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}