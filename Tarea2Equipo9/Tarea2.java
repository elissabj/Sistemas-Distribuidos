/*
INSTITUTO POLITÉCNICO NACIONAL 
Escuela Superior de Cómputo
Desarrollo de Sistemas Distribuidos
Ramos Gomez Elisa
Erick Eduardo Ramírez Arellano 
Omar Ramos Herrera 

Tarea 2 
4CV13

Ejecutar nodo 2 ejemplo:
java -Djavax.net.ssl.keyStore=keystore_servidor.jks -Djavax.net.ssl.keyStorePassword=1234567 -Djavax.net.ssl.trustStore=keystore_cliente.jks -Djavax.net.ssl.trustStorePassword=123456 Tarea2 2
  
*/


import java.net.Socket;
import java.io.DataInput;
import java.io.DataOutput;
import java.nio.ByteBuffer;
import java.net.ServerSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import javax.net.ssl.SSLSocketFactory;
import javax.swing.plaf.synth.SynthSplitPaneUI;


public class Tarea2{

    static class Proceso extends Thread{

        int numNodo; 

        Proceso(int numNodo){
            this.numNodo = numNodo;
        }

        public void clienteSSL(short token){

            Socket conexion = null;
            for(;;){
                try{

                   
                    SSLSocketFactory cliente = (SSLSocketFactory) SSLSocketFactory.getDefault();

                    conexion = cliente.createSocket("localhost", 50000  + ((numNodo + 1) %6));

                    // conexion = new Socket("localhost", 50000 + ((numNodo + 1) %6));

                    DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                    System.out.println("Cliente conectato con el servidor #: " + ((numNodo + 1) %6));

                    salida.writeShort(token);

                    Thread.sleep(1000);
                    conexion.close();
                    // cliente.close();

                    break;

                }catch(Exception e){

                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException e2){
                        System.out.println(e2.getMessage());
                    }
                }
            }


        }


        public void run(){  
            //Servidor
            try{
                
                SSLServerSocketFactory socket_factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

                ServerSocket servidor = socket_factory.createServerSocket(50000 + numNodo);
                //  System.out.println("Servidor iniciado");

                // ServerSocket servidor = new ServerSocket(50000 + numNodo);

                for(;;){

                    // System.out.println("Servidor inciado, esperando clientes.");

                    Socket conectado = servidor.accept();

                    

                    DataInputStream entrada = new DataInputStream(conectado.getInputStream());
                    
                    System.out.print("El valor es: ");
                    short token = entrada.readShort();
                    token++;
                    System.out.println(token);

                    if(numNodo == 0 && token >= 500){
                        System.out.println("Hemos finalizado");
                        break;
                    }
                    
                    clienteSSL(token);

                    conectado.close();
        
                    // socket_factory.close();

                }

                servidor.close();

            }catch(Exception e){
                System.out.println(e.getMessage());
            }

        }
    }


    public static void main(String[] args) {
        
        System.out.print("Hola, has seleccionado el #: ");
        System.out.print(args[0]);


        /*
            definicion de el keystore
        */
        // java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        // System.setProperty("javax.net.ssl.keyStore","keystore_servidor.jks");
        // System.setProperty("javax.net.ssl.keyStorePassword","1234567");

        // System.setProperty("javax.net.ssl.trueStore","keystore_cliente.jks");
        // System.setProperty("javax.net.ssl.trueStorePassword","123456");

        int numNodo = Integer.parseInt(args[0]);

        if(numNodo == 0){

            Proceso inicia = new Proceso(numNodo);
            short valorToken = 0;
            inicia.clienteSSL(valorToken);
            inicia.start();

        }else if (numNodo>= 1 && numNodo <= 5){
            
            
            Proceso inicia = new Proceso(numNodo);
            inicia.start();

        }else{
            throw new RuntimeException("un valor de nodo incorrecto");
        }
        
    }

}

