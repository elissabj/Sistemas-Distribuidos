/*
  Carlos Pineda Guerrero, marzo 2022.
*/

package negocio;

import com.google.gson.*;

public class Usuario {

  String email;
  String nombre;
  String apellido_paterno;
  String apellido_materno;
  String fecha_nacimiento;
  String telefono;
  String genero;
  byte[] foto;

  /*
  public void setEmail(String email) {
    this.email = email;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setApellidoPaterno(String apellido_paterno) {
    this.apellido_paterno = apellido_paterno;
  }

  public void setApellidoMaterno(String apellido_materno) {
    this.apellido_materno = apellido_materno;
  }

  public void setFechaNacimiento(String fecha_nacimiento) {
    this.fecha_nacimiento = fecha_nacimiento;
  }

  public void setTelefono (String telefono) {
    this.telefono = telefono;
  }

  public void setGenero (String genero) {
    this.genero = genero;
  }

  public void setFoto (byte[] foto) {
    this.foto = foto;
  }
  */

  // @FormParam necesita un metodo que convierta una String al objeto de tipo Usuario
  public static Usuario valueOf(String s) throws Exception {

    Gson j = new GsonBuilder().registerTypeAdapter(byte[].class,new AdaptadorGsonBase64()).create();
    return (Usuario)j.fromJson(s,Usuario.class);

  }

}
