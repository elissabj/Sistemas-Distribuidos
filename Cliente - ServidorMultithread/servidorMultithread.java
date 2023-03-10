import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.net.ServerSocket;


public class servidorMultithread {

    static class Worker extends Thread {
        
        Socket conexion;

        Worker(Socket conexion){
            this.conexion = conexion;
        }

        static void read (DataInputStream f, byte [] b, int posicion, int longitud) throws Exception {

            while(longitud > 0){
                int n = f.read(b, posicion, longitud);
                posicion += n;
                longitud -= n;
            }
        }

        //Metodo run que se ejecuta al iniciar un hilo
        public void run(){

            try {

                //System.out.println("Cliente conectado, recibiendo datos...");
    
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                DataInputStream entrada = new DataInputStream(conexion.getInputStream());
    
                int n = entrada.readInt();
                System.out.println(n);
    
                double x = entrada.readDouble();
                System.out.println(x);
    
                byte [] buffer = new byte[4];
                read(entrada, buffer, 0, 4);
                System.out.println(new String(buffer, "UTF-8"));
    
                salida.write("HOLA".getBytes());
    
                byte[] a = new byte[5*8];
                read(entrada, a, 0, 5*8);
    
                ByteBuffer b = ByteBuffer.wrap(a);
    
                for(int i = 0; i < 5; i++){
                    System.out.println(b.getDouble());
                }
    
                conexion.close();
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public static void main(String[] args) {
        
        try {
            ServerSocket servidor = new ServerSocket(50000);
            
            System.out.println("Servidor inciado, esperando clientes.");

            for(;;){

                Socket conexion = servidor.accept();
                System.out.println("Cliente conectado...");

                Worker w = new Worker(conexion);
                w.start();

            }

            // servidor.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        


    }
}