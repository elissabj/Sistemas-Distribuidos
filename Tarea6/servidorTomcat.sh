sudo apt update #Actualizar los paquetes
sudo apt install openjdk-8-jdk-headless #Instalar el JDK 8
wget https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.77/bin/apache-tomcat-8.5.77.zip #Descargar el tomcat
sudo apt install unzip #Instalar el unzip para descomprir archivos desde la terminal
unzip apache-tomcat-8.5.77.zip #Descomprimimos la carpeta
cd apache-tomcat-8.5.77/ #Nos metemos a la carpeta
rm -r webapps/ #Eliminamos esta carpeta por razones de seguridad
mkdir webapps #Volvemos a crear la carpeta
mkdir webapps/ROOT #Y le ponemos su ROOT
cd .. #Nos regresamos al usuario
wget https://repo1.maven.org/maven2/org/glassfish/jersey/bundles/jaxrs-ri/2.24/jaxrs-ri-2.24.zip #Descargamos Jersey
unzip jaxrs-ri-2.24.zip #Y lo descomprimimos
#Lo que vamos a hacer es de la carpeta jaxrs-ri vamos a copiar todos los .jar de todas las subcarpetas
#a la carpeta apache-tomcat-8.5.77/lib y removemos el jar "rm javax.servlet-api-3.0.1.jar"
cd jaxrs-ri/api
cp javax.ws.rs-api-2.0.1.jar ~/apache-tomcat-8.5.77/lib
cd ..
cd ext
cp *.jar ~/apache-tomcat-8.5.77/lib
cd ..
cd lib
cp *.jar ~/apache-tomcat-8.5.77/lib
cd ../..
cd apache-tomcat-8.5.77/lib
rm javax.servlet-api-3.0.1.jar
#Hasta aqui termina el copiado
cd ../.. #Regresar al ~
wget https://repo1.maven.org/maven2/com/google/code/gson/gson/2.3.1/gson-2.3.1.jar #Descargar el .jar GSON 
cp gson-2.3.1.jar ~/apache-tomcat-8.5.77/lib #Copiar el .jar a la carpeta de apache
wget https://downloads.mysql.com/archives/get/p/3/file/mysql-connector-java-8.0.26.zip #Descargar el mysql
unzip mysql-connector-java-8.0.26.zip #Descomprimirlo
cd ../.. #Regresamos a la carpeta orginal
wget https://repo1.maven.org/maven2/com/google/code/gson/gson/2.3.1/gson-2.3.1.jar
cp gson-2.3.1.jar ~/apache-tomcat-8.5.78/lib
wget https://downloads.mysql.com/archives/get/p/3/file/mysql-connector-java-8.0.26.zip
unzip mysql-connector-java-8.0.26.zip
cd mysql-connector-java-8.0.26.jar ~/apache-tomcat-8-5-78/lib
export CATALINA_HOME=~/apache-tomcat-8-5-78
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
N
#Escribir password
Y
Y
Y
Y
sudo mysql
mysql -u root -p
#Escribir password
create user hugo@localhost identified by 'contraseñaparahugo'; #crear usuario hugo con su contraseña
quit
mysql -u hugo -p
#Escribir contraseña para Hugo 
