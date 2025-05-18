import java.util.ArrayList;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.net.ServerSocket;

public class Tarea3 {

  static int N = 1000;
  static double[][] A = new double[N][N];
  static double[][] B = new double[N][N];
  static double[][] C = new double[N][N];
  static double[][] aux = new double[N/2][N/2];

  static ArrayList<double[][]> Matrices = new ArrayList<double[][]>();
  static ArrayList<double[][]> MatrizC = new ArrayList<double[][]>();

  static double[][] transponerMatriz(double[][] M, int N){

    double [][] bTAux = new double[N][N];


    for(int i = 0; i < N; i++){
      for(int j = 0; j < N; j++){
        bTAux[i][j] = M[j][i];
      }
    }

    for(int i = 0; i < N; i++){
      for(int j = 0; j < N; j++){
        M[i][j] = bTAux[i][j];
      }
    }

    return M;
  }

  static double[][] multiplicaMatriz(double[][] A, double[][] B, double[][] C, int I, int N) {

    for(int i = 0; i < I; i++){
      for(int j = 0; j < I; j++){
        for(int k = 0; k < N; k++){
          C[i][j] += A[i][k]*B[j][k];
        }
      }
    }      

    return C;
  }

  static void mostrarMatriz(double[][] M, int N, int I) {
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < I; j++) {
          System.out.print(M[i][j] + "\t ");
        }
      System.out.println("");
    }
    System.out.println("");
  }

  static double[][] dividirMatriz(double[][] M, int N, int I) {

    double[][] X = new double[N/2][N];
    double Limit = (I != 0) ? N : N/2;

    for(int i = I, k = 0; i < Limit; i++, k++){
      for(int j = 0; j < N; j++){
        X[k][j] = M[i][j];
      }
    }
    return X;
  }

  static void enviarMatriz(DataOutputStream salida, double[][] M, int N) throws Exception {
    for(int i = 0; i < N/2; i++){
      for(int j = 0; j < N; j++){
        salida.writeDouble(M[i][j]);
      }
    }

    //salida.flush();
  }

  static double[][] recibirMatriz(DataInputStream entrada, int N) throws Exception {

    double[][] X = new double[N/2][N];

    for(int i = 0; i < N/2; i++){
      for(int j =0; j < N; j++){
        X[i][j] = entrada.readDouble();
      }
    }

    return X;
  }

  static double checksum(double[][] M, int N){
    double sum = 0;
    for(int i = 0; i < N; i++){
      for(int j = 0; j < N; j++) {
        sum += M[i][j];
      }
    }

    return sum;
  }

  static class Cliente extends Thread{
    int puerto;
    String ip;
    double[][] X;
    double[][] M;
    double[][] C;

    Cliente(String ip, int puerto, double[][] X, double[][] M){
      this.puerto = puerto;
      this.ip = ip;
      this.X = X;
      this.M = M;
    }

    public void run() {
      Socket conexion = null;

      for(;;){
        try{

            conexion = new Socket(ip, puerto);

            DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
            DataInputStream entrada = new DataInputStream(conexion.getInputStream());

            enviarMatriz(salida,X,N);
            enviarMatriz(salida,M,N);

            C = recibirMatriz(entrada,N);

            Thread.sleep(1000);
            conexion.close();
            break;

        }catch(Exception e){
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e2) {
                System.out.println(e2.getMessage());
            }
        }
    }
    }    
  }

  static double[][] crearMatrizC(double[][] C, double[][] auxC, int nodo) {
    int filaInicio, filaFin;
    int columnaInicio, columnaFin;
    

    if( nodo == 1){
      filaInicio = 0; 
      filaFin = N/2;
      columnaInicio = 0;
      columnaFin = N/2;
    } else if (nodo == 2){
      filaInicio = 0;
      filaFin = N/2;
      columnaInicio = N/2;
      columnaFin = N;
    } else if (nodo == 3){
      filaInicio = N/2;
      filaFin = N;
      columnaInicio = 0;
      columnaFin = N/2;
    } else{
      filaInicio = N/2;
      filaFin = N;
      columnaInicio = N/2;
      columnaFin = N;
    }

    for(int i = filaInicio, l = 0; i < filaFin; i++, l++){
      for(int j = columnaInicio, k = 0; j < columnaFin; j++, k++){
        C[i][j] = auxC[l][k];
      }
    }

    return C;
  }

  public static void main(String[] args)
  {

    if (Integer.parseInt(args[0])== 0) {
      //inicializacion de las matrices
      for(int i = 0; i < N; i++){
        for(int j = 0; j < N; j++){
          A[i][j] = i+5*j;
          B[i][j] = 5*i-j;
          C[i][j] = 0;
        }
      }

      B = transponerMatriz(B, N);
      
      Matrices.add(dividirMatriz(A,N, 0));
      Matrices.add(dividirMatriz(A,N, N/2));
      Matrices.add(dividirMatriz(B,N, 0));
      Matrices.add(dividirMatriz(B,N, N/2));

      try {
        //Cliente Cli1 = new Cliente("localhost", 50001, Matrices.get(0), Matrices.get(2));
        //Cliente Cli2 = new Cliente("localhost", 50002, Matrices.get(0), Matrices.get(3));
        //Cliente Cli3 = new Cliente("localhost", 50003, Matrices.get(1), Matrices.get(2));

        Cliente Cli1 = new Cliente("20.25.16.244", 50001, Matrices.get(0), Matrices.get(2));
        Cliente Cli2 = new Cliente("20.120.3.8", 50002, Matrices.get(0), Matrices.get(3));
        Cliente Cli3 = new Cliente("20.120.18.196", 50003, Matrices.get(1), Matrices.get(2));

        Cli1.start();
        Cli2.start();
        Cli3.start();

        Cli1.join();
        Cli2.join();
        Cli3.join();

        aux =multiplicaMatriz(Matrices.get(1), Matrices.get(3), aux, N/2,N);

        C = crearMatrizC(C, Cli1.C, 1);
        C = crearMatrizC(C, Cli2.C, 2);
        C = crearMatrizC(C, Cli3.C, 3);
        C = crearMatrizC(C, aux,4);
        
        // mostrarMatriz(C,N,N);

        if (N <= 8){
          mostrarMatriz(A, N, N);
          mostrarMatriz(B, N, N);

          mostrarMatriz(C, N, N);
        }

        System.out.println("Checksum: " + checksum(C, N));


      } catch (Exception e) {
        System.out.println(e.getMessage());
      }

    } else if (Integer.parseInt(args[0]) >= 1 && Integer.parseInt(args[0]) <= 3) {
      try {
        ServerSocket server = new ServerSocket(50000 + Integer.parseInt(args[0]));

        Socket conexion = server.accept();

        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());

        double[][] a = recibirMatriz(entrada, N);
        double[][] b = recibirMatriz(entrada, N);

        double[][] c = new double[N/2][N];
        c = multiplicaMatriz(a,b,c,N/2,N);

        enviarMatriz(salida,c,N);

        server.close();
        salida.close();
        entrada.close();

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    } else {
      throw new RuntimeException("un valor de nodo incorrecto");
    }
  }
}