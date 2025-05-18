/*
INSTITUTO POLITÉCNICO NACIONAL 
Escuela Superior de Cómputo
Desarrollo de Sistemas Distribuidos
Ramos Gomez Elisa
Tarea 7
4CV13
*/

package negocio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import javax.swing.event.SwingPropertyChangeSupport;
import com.google.gson.*;

public class Tarea7 {

    static String IP = "20.232.174.40";
    static String puerto = "8080";

    
    public static void impresionUsuario(Usuario usuario){

        System.out.println("Email: " + usuario.email);
        System.out.println("Nombre: " + usuario.nombre);
        System.out.println("Apellido Paterno: " + usuario.apellido_paterno);
        System.out.println("Apellido Materno: " + usuario.apellido_materno);
        System.out.println("Fecha: " + usuario.fecha_nacimiento);
        System.out.println("Telefono: " + usuario.telefono);
        System.out.println("Genero: " + usuario.genero);

    }

    public static void altaUsuario (Usuario usuario) {

        //Creamos un objeto de tipo GSON y configuramos como se van a guardar las fechas
        Gson j = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();
        String usuarioEnJson = j.toJson(usuario); //Pasamos el usuario de la clase Usuario a una string en formato JSON
        
        //Para ver como se ve la string descomente lo siguiente:
        //System.out.println("\nLa string GSON es: " + usuarioEnJson);

        //Aqui inicia la peticion
        try {

            URL url = new URL("http://" + IP + ":"+ puerto +"/Servicio/rest/ws/alta_usuario");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setDoOutput(true);
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                
            String parametros = "usuario=" + URLEncoder.encode(usuarioEnJson, "UTF-8");

            //String parametros = URLEncoder.encode(s, "UTF-8");

            //System.out.println( "String a enviar: " + parametros);

                
            OutputStream os = conexion.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();


            if(conexion.getResponseCode() == 200){

                System.out.println("OK");
                BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
                String respuesta;
                    // el método web regresa una string en formato JSON
                while ((respuesta = br.readLine()) != null) System.out.println(respuesta);

            } else {

                //System.out.println("Otro Codigo");
                BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getErrorStream())));
                
                // El método web regresa una string del error en formato JSON
                String respuesta = br.readLine();
                Error error = (Error)j.fromJson(respuesta, Error.class);
                System.out.println(error.message);
                
            }


            conexion.disconnect();
                
        } catch (Exception e) {
                System.out.println(e.getMessage());
                //TODO: handle exception
        }


    } 

    public static void modificaUsuario(Usuario usuario) {

        //Aqui inicia la peticion
        try {

            URL url = new URL("http://" + IP + ":"+ puerto +"/Servicio/rest/ws/modifica_usuario");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setDoOutput(true);
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
            Gson j = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();
            String usuarioAModificar = j.toJson(usuario);

            String parametros = "usuario=" + URLEncoder.encode(usuarioAModificar, "UTF-8");

            //System.out.println( "String a enviar: " + parametros);
                
            OutputStream os = conexion.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();

            if(conexion.getResponseCode() == 200){

                System.out.println("OK");

            } else {

                //System.out.println("Otro Codigo");
                BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getErrorStream())));
                
                // El método web regresa una string del error en formato JSON
                String respuesta = br.readLine();
                Error error = (Error)j.fromJson(respuesta, Error.class);
                System.out.println(error.message);
            }

            conexion.disconnect();
                
        } catch (Exception e) {
                System.out.println(e.getMessage());
        }

    } 

    public static void consultaUsuario(String email){
        
        //Peticion
        try {

            URL url = new URL("http://" + IP + ":"+ puerto +"/Servicio/rest/ws/consulta_usuario");

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setDoOutput(true);
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parametros = "email=" + URLEncoder.encode(email, "UTF-8");

            OutputStream os = conexion.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();

            Gson j = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();

            if(conexion.getResponseCode() == 200){

                System.out.println("OK");
                BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
                //String respuesta;
                // el método web regresa una string en formato JSON
                //while ((respuesta = br.readLine()) != null) System.out.println(respuesta);

                String respuesta = br.readLine();
                //System.out.println("La respuesta es: " + respuesta);

                Usuario usuarioConsultado = (Usuario)j.fromJson(respuesta, Usuario.class);
                impresionUsuario(usuarioConsultado);

                System.out.print("¿Desea modificar el usuario (s/n)? ");
                
                Scanner cin = new Scanner(System.in);
                String opModificar = cin.next();


                if(opModificar.compareTo("s") == 0 || opModificar.compareTo("S") == 0){

                    cin.nextLine(); //Flush

                    System.out.print("Nombre: ");
                    String nombre = cin.nextLine();
                    if(nombre.isEmpty()){
                        nombre = usuarioConsultado.nombre;
                    }

                    System.out.print("Apellido Paterno: ");
                    String apellidoP = cin.nextLine();
                    if(apellidoP.isEmpty()){
                        apellidoP = usuarioConsultado.apellido_paterno;
                    }

                    System.out.print("Apellido Materno: ");
                    String apellidoM = cin.nextLine();
                    if(apellidoM.isEmpty()){
                        apellidoM = usuarioConsultado.apellido_materno;
                    }

                    //cin.nextLine();
                    System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
                    String fecha = cin.nextLine();
                    if(fecha.isEmpty()){
                        fecha = usuarioConsultado.fecha_nacimiento;
                    }

                    System.out.print("Telefono: ");
                    String telefono = cin.nextLine();
                    if(telefono.isEmpty()){
                        telefono = usuarioConsultado.telefono; 
                    }

                    System.out.print("Genero (M ó F): ");
                    String genero = cin.nextLine();
                    if(genero.isEmpty()){
                        genero = usuarioConsultado.genero;
                    }

                    Usuario usr = new Usuario();

                    usr.email = usuarioConsultado.email;                        
                    usr.nombre = nombre;
                    usr.apellido_paterno = apellidoP;
                    usr.apellido_materno = apellidoM;
                    usr.fecha_nacimiento = fecha;
                    usr.telefono = telefono;
                    usr.genero = genero;
                    usr.foto = null;

                    modificaUsuario(usr);


                } else if (opModificar.compareTo("n") == 0){

                    System.out.println("No se realizaron cambios.");

                } else {
                
                    System.out.println("Opcion incorrecta.");
                
                }


            } else { //Si la peticion de la consulta no es exitosa

                BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getErrorStream())));
                
                // el método web regresa una string en formato JSON
                String respuesta = br.readLine();
                Error error = (Error)j.fromJson(respuesta, Error.class);
                System.out.println(error.message);
            }
            conexion.disconnect();
            
        } catch (Exception e) { //Catch de la peticion de consulta
            System.out.println(e.getMessage());
        }


    } 

    public static void borrarUsuario(String email){
        
        try {

            // URL url = new URL("http://20.25.73.107:8080/Servicio/rest/ws/borra_usuario");
            URL url = new URL("http://" + IP + ":"+ puerto +"/Servicio/rest/ws/borra_usuario");

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setDoOutput(true);
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parametros = "email=" + URLEncoder.encode(email, "UTF-8");

            OutputStream os = conexion.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();

            if(conexion.getResponseCode() == 200){

                System.out.println("OK");

            } else { //Si la peticion de la consulta no es exitosa

                BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getErrorStream())));
                
                // El método web regresa una string del error en formato JSON
                Gson j = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();
                String respuesta = br.readLine();
                Error error = (Error)j.fromJson(respuesta, Error.class);
                System.out.println(error.message);

            }

            conexion.disconnect();
            
        } catch (Exception e) { //Catch de la peticion de consulta
            System.out.println(e.getMessage());
        }

    } 


    public static void main(String[] args) {
        
        Scanner cin = new Scanner(System.in);

        String email = "";
        String nombre = "";
        String apellidoP = "";
        String apellidoM = "";
        String fecha = "";
        String telefono = "";
        String genero = "";

        System.out.println("MENU");

        System.out.println("a. Alta usuario");
        System.out.println("b. Consulta usuario");
        System.out.println("c. Borra usuario");
        System.out.println("d. Salir");
        System.out.print("Opción: ");

        String opcion = cin.next();

        if(opcion.compareTo("a") == 0){

            cin.nextLine(); //Flush

            //Pedir al usuario sus datos
            System.out.println("\n-- Alta de Usuario --");
            System.out.print("Email: ");
            email = cin.next();

            cin.nextLine(); //Flush
            System.out.print("Nombre(s): ");
            nombre = cin.nextLine();

            System.out.print("Apellido Paterno: ");
            apellidoP = cin.next();

            System.out.print("Apellido Materno: ");
            apellidoM = cin.next();

            cin.nextLine(); //Flush
            System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
            fecha = cin.nextLine();

            System.out.print("Telefono: ");
            telefono = cin.next();

            System.out.print("Genero (M ó F): ");
            genero = cin.next();

            Usuario usr = new Usuario();
            
            usr.email = email;
            usr.nombre = nombre;
            usr.apellido_paterno = apellidoP;
            usr.apellido_materno = apellidoM;
            usr.fecha_nacimiento = fecha;
            usr.telefono = telefono;
            usr.genero = genero;
            usr.foto = null;

            altaUsuario(usr);

        } else if(opcion.compareTo("b") == 0){

            System.out.println("\n-- Consulta de Usuario --");

            System.out.print("Ingrese el email a consultar: ");
            email = cin.next();

            consultaUsuario(email);
            

        } else if(opcion.compareTo("c") == 0){

            System.out.println("\n-- Borrar Usuario --");
            System.out.print("Ingrese el email a eliminar: ");
            email = cin.next();

            borrarUsuario(email);

        } else if(opcion.compareTo("d") == 0){

            System.out.println("\n\tGracias por tu visita :)");
        
        } else {
            System.out.println("Opcion incorrecta");
        }


    }

}