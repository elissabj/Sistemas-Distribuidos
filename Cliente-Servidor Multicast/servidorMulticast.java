import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class servidorMulticast{

    static void envia_mensajes(byte[] buffer, String ip, int puerto) throws IOException{
        DatagramSocket socket = new DatagramSocket();
        InetAddress grupo = InetAddress.getByName(ip);
        DatagramPacket paquete = new DatagramPacket(buffer,buffer.length, grupo, puerto);
        socket.send(paquete);
        socket.close();
    }

    public static void main(String[] args){

        System.setProperty("java.net.preferIPv4Stack", "true");

        try{
            envia_mensajes("hola".getBytes(), "230.0.0.0", 50000);


            ByteBuffer b = ByteBuffer.allocate(5*8);
            b.putDouble(1.1);
            b.putDouble(1.2);
            b.putDouble(1.3);
            b.putDouble(1.4);
            b.putDouble(1.5);

            envia_mensajes(b.array(), "230.0.0.0", 50000);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
    }
}