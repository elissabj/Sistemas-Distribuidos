sudo apt update #Actualizar los paquetes
#sudo apt install openjdk-8-jdk-headless #Instalar el JDK 8
wget https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.78/bin/apache-tomcat-8.5.78.zip #Descargar el tomcat
#sudo apt install unzip #Instalar el unzip para descomprir archivos desde la terminal
unzip apache-tomcat-8.5.78.zip #Descomprimimos la carpeta
cd apache-tomcat-8.5.78/ #Nos metemos a la carpeta
rm -r webapps/ #Eliminamos esta carpeta por razones de seguridad
mkdir webapps #Volvemos a crear la carpeta
mkdir webapps/ROOT #Y le ponemos su ROOT
cd .. #Nos regresamos al usuario
wget https://repo1.maven.org/maven2/org/glassfish/jersey/bundles/jaxrs-ri/2.24/jaxrs-ri-2.24.zip #Descargamos Jersey
unzip jaxrs-ri-2.24.zip #Y lo descomprimimos
#Lo que vamos a hacer es de la carpeta jaxrs-ri vamos a copiar todos los .jar de todas las subcarpetas
#a la carpeta apache-tomcat-8.5.77/lib y removemos el jar "rm javax.servlet-api-3.0.1.jar"
cd jaxrs-ri/api
cp javax.ws.rs-api-2.0.1.jar ~/apache-tomcat-8.5.78/lib
cd ..
cd ext
cp *.jar ~/apache-tomcat-8.5.78/lib
cd ..
cd lib
cp *.jar ~/apache-tomcat-8.5.78/lib
cd ../..
cd apache-tomcat-8.5.78/lib
rm javax.servlet-api-3.0.1.jar
#Hasta aqui termina el copiado
cd ../.. #Regresar al ~
wget https://repo1.maven.org/maven2/com/google/code/gson/gson/2.3.1/gson-2.3.1.jar #Descargar el .jar GSON 
cp gson-2.3.1.jar ~/apache-tomcat-8.5.78/lib #Copiar el .jar a la carpeta de apache
wget https://downloads.mysql.com/archives/get/p/3/file/mysql-connector-java-8.0.26.zip #Descargar el mysql
unzip mysql-connector-java-8.0.26.zip #Descomprimirlo
cd mysql-connector-java-8.0.26/ #Ir a la carpeta de mysql
cp mysql-connector-java-8.0.26.jar ~/apache-tomcat-8.5.78/lib/ #Copiar el jar de mysql al bin de tomcat


#AQUI DEFINIR LA RUTA DEL NOMBRE DE LA MAQUINA
export CATALINA_HOME=/home/servidor/apache-tomcat-8.5.78 #Definir variable de entorno


export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/ #definir variable de entorno
sh $CATALINA_HOME/bin/catalina.sh start #Iniciar tomcat
#sh $CATALINA_HOME/bin/catalina.sh stop #Detener tomcat

# Instalacion de MySQL
# sudo apt update
# sudo apt install mysql-server

#sudo mysql_secure_installation
#Aqui toca hacerlo a manita
#Primero es: N
#Contraseña de root: Root12345!
#Despues todo Y

#sudo mysql

#Ya en mysql ejecutar los siguientes comandos
#ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Root12345!';
#FLUSH PRIVILEGES;
#quit;

#mysql -u root -p
#En mysql poner:
#create user hugo@localhost identified by 'Root12345!';
#grant all on servicio_web.* to hugo@localhost;
#quit;

#Entramos al usuario Hugo
#mysql -u hugo -p
#create database servicio_web;
#use servicio_web;
#create table usuarios (id_usuario integer auto_increment primary key, email varchar(256) not null, nombre varchar(100) not null, apellido_paterno varchar(100) not null, apellido_materno varchar(100), fecha_nacimiento datetime not null, telefono varchar(20), genero char(1) );
#create table fotos_usuarios (id_foto integer auto_increment primary key, foto longblob, id_usuario integer not null );
#alter table fotos_usuarios add foreign key (id_usuario) references usuarios(id_usuario);
#create unique index usuarios_1 on usuarios(email);
#quit;

#Hacer el unzip de Servicio.zip

#FIJAR LA RUTA DE LA MAQUINA
export CATALINA_HOME=/home/servidor/apache-tomcat-8.5.78
cd Servicio
javac -cp $CATALINA_HOME/lib/javax.ws.rs-api-2.0.1.jar:$CATALINA_HOME/lib/gson-2.3.1.jar:. negocio/Servicio.java

#Editar el archivo "context.xml" que está en el directorio "META-INF" y definir el username de la base de datos y el password correspondiente. El usuario "hugo" fue creado en el paso 2 de la sección Crear un usuario en MySQL.

rm WEB-INF/classes/negocio/*
cp negocio/*.class WEB-INF/classes/negocio/
jar cvf Servicio.war WEB-INF META-INF
cp Servicio.war ~/apache-tomcat-8.5.78/webapps/
# cd ..
#cp WSClient.js ~/apache-tomcat-8.5.78/webapps/ROOT
#cp prueba.html ~/apache-tomcat-8.5.78/webapps/ROOT
#cp usuario_sin_foto.png ~/apache-tomcat-8.5.78/webapps/ROOT
