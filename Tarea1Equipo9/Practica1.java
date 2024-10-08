/*
INSTITUTO POLITÉCNICO NACIONAL 
Escuela Superior de Cómputo
Desarrollo de Sistemas Distribuidos
Ramos Gomez Elisa
Erick Eduardo Ramírez Arellano 
Omar Ramos Herrera 

Tarea 1 
4CV13
  
*/


import java.net.Socket;
import java.io.DataInput;
import java.io.DataOutput;
import java.nio.ByteBuffer;
import java.net.ServerSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;


public class Practica1{

    static class Servidor extends Thread{

        Socket conexion;
        int numNodo;

        Servidor(Socket conexion, int numNodo){
            this.conexion = conexion;
            this.numNodo = numNodo;
        }

        public double calculaTerminos(){

            double sumaTotal = 0.0;

            for(int i = 0; i < 1000000; i++){
                sumaTotal += (4.0/(8*i+2*(numNodo-2)+3));
            }

            if(numNodo%2 == 0){
                return -sumaTotal;
            }

            return sumaTotal;
        }
        

        //Metodo run que se ejecuta al iniciar un hilo
        public void run(){
            try {

                System.out.println("Cliente conectado, calculando Serie Gregory-Leibniz...");
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());

                // System.out.println("El valor es: ");
                double valor = calculaTerminos();
                // System.out.println(valor);

                salida.writeDouble(valor);
                Thread.sleep(1000);

                
            } catch (Exception e) {
                //System.out.println("Se murio");
                System.out.println(e.getMessage());
            }

        }


    }



    static class Cliente extends Thread{
        
        int numServidor;
        static double PI = 0.0;
        static Object obj = new Object();
        


        Cliente(int numServidor){
            this.numServidor = numServidor;
        }

        public void run(){

            Socket conexion = null; 
            for(;;){
                try{

                    conexion = new Socket("localhost", 50000+ numServidor);
                    System.out.println("Cliente conectato con el servidor #: " + numServidor);

                    DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                    // System.out.println(PI + " ANTES DE SUMA ");

                    synchronized(obj){
                        PI += entrada.readDouble();
                        // System.out.println(PI + " en DE SUMA ");
                    }

                    // System.out.println(PI + " despues DE SUMA ");

                    Thread.sleep(1000);
                    conexion.close();
                    break;

                }catch(Exception e){
                    
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e2) {
                        // e2.printStackTrace();
                        System.out.println(e2.getMessage());
                    }
                }
            }

        }
    }


    public static void main(String[] args) {
        
        System.out.print("Hola, has seleccionado el #: ");
        System.out.print(args[0]);
        System.out.print(" que es ");

        int numNodo = Integer.parseInt(args[0]);

        if(numNodo == 0){
            System.out.println("el Cliente");

            try{

                Cliente c1 = new Cliente(1);
                Cliente c2 = new Cliente(2);
                Cliente c3 = new Cliente(3);
                Cliente c4 = new Cliente(4);

                c1.start();
                c2.start();
                c3.start();
                c4.start();

                c1.join();
                c2.join();
                c3.join();
                c4.join();

                System.out.println("Finalizado el valor de PI es " + Cliente.PI);

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        

        }else if (numNodo>= 1 && numNodo <= 4){
            System.out.println("el Servidor " + numNodo);
            
            try{

                ServerSocket servidor = new ServerSocket(50000+numNodo);
                System.out.println("Servidor inciado, esperando clientes.");

                Socket conecta = servidor.accept();

                Servidor calcula = new Servidor(conecta, numNodo);
                calcula.start();

                // conecta.close();
                servidor.close();

            }catch(Exception e){
                System.out.println(e.getMessage());
            }

        }else{
            throw new RuntimeException("un valor de nodo incorrecto");
        }
        

    }


}

