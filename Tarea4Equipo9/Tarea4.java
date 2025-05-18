/*
INSTITUTO POLITÉCNICO NACIONAL 
Escuela Superior de Cómputo
Desarrollo de Sistemas Distribuidos
Ramos Gomez Elisa
Erick Eduardo Ramírez Arellano 
Omar Ramos Herrera 

Tarea 4
4CV13

Parametro el nombre del usuario que va a escribir en el chat
Juan, Pedro y Ana
*/


import java.io.IOError;
import java.util.Scanner;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;


public class Tarea4 {

    static void envia_mensaje_multicast (byte[] buffer, String ip, int puerto) throws IOException {
        DatagramSocket socket = new DatagramSocket ();
        socket.send( new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip), puerto ) );
        socket.close();
    }

    static byte[] recibe_mensaje_multicast (MulticastSocket socket, int longitud_mensaje) throws IOException {
        
        byte [] buffer = new byte[longitud_mensaje];
        DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
        socket.receive(paquete);
        return paquete.getData();
    }


    static class Worker extends Thread {

        public void run() {

            // En un ciclo infinito se recibiran los mensajes enviados al grupo
            // 230.0.0.0 a traves del puerto 50000 y se desplegaran en la pantalla
            try {

                InetAddress grupo = InetAddress.getByName("230.0.0.0");
                MulticastSocket socket = new MulticastSocket(50000);
                socket.joinGroup(grupo);

                for(;;){

                    byte [] mensaje = recibe_mensaje_multicast(socket, 1000);

                    //System.out.println("Mensaje recibido");

                    /*
                    for(int i = 0; i < mensaje.length; i++){
                        System.out.print(mensaje[i]);
                        System.out.print(" ");
                    }
                    */

                    //System.out.println("\n Decodificado: ");

                    String mensajeStr = new String(mensaje, "IBM850");
                    //System.out.println("El mensaje es: " + mensajeStr);
                    System.out.println();
                    System.out.println(mensajeStr);
                    System.out.print("Ingrese el mensaje a enviar: ");

    
                }
                
            } catch (Exception e) {

                System.out.println(e.getMessage());
            
            }
            
        }

    }

    public static void main(String[] args) throws Exception {
        

        if (args[0].length() == 0){
            System.out.println("No se han pasado argumetos");
            return;
        }

        new Worker().start();
        String nombre = args[0];

        System.out.println("Hola, " + nombre);

        System.setProperty("java.net.preferIPv4Stack", "true");

        // En un ciclo infinito se leera cada mensaje del teclado y se enviara el mensaje al 
        // grupo 230.0.0.0 a traves del puerto 50000.
        Scanner sc = new Scanner(System.in);

        String IP = "230.0.0.0";
        int puerto = 50000; 

        System.out.print("Ingrese el mensaje a enviar: ");
        
        for(;;){    

            String mensaje = sc.nextLine();

            mensaje = nombre + " dice " + mensaje; 

            //System.out.println("El mensaje a enviar: " + mensaje);

            byte [] buffer = mensaje.getBytes();

            /*
            for(int i = 0; i < buffer.length; i++){
                System.out.print(buffer[i]);
                System.out.print(" ");
            }
            */

            //System.out.println();

            envia_mensaje_multicast(buffer, IP, puerto);


        }

    }

} 