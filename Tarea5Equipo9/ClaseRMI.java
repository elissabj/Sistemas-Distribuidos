import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClaseRMI extends UnicastRemoteObject implements InterfaceRMI {
    
    public ClaseRMI () throws RemoteException {
        super();
    }


    public float checksum(float[][] m) throws RemoteException {
        float s = 0;

        for(int i = 0; i < m.length; i++){
            for(int j = 0; j < m[0].length; j++){
                s += m[i][j];
            }
        }
        return s;
    }


    public float [][] transpuesta (float [][] A) throws RemoteException {

          float [][] aT = new float[A.length][A[0].length];
        
          for(int i = 0; i < A.length; i++){
              for(int j = 0; j < A[0].length; j++){
                  aT[i][j] = A[j][i];
              }   
          }

          for(int i = 0; i < A.length; i++){
              for(int j = 0; j < A[0].length; j++){
                  A[i][j] = aT[i][j];
              }   
          }

          return A;
    }


    public float [][] separa_matriz(float [][] A, int inicio) throws RemoteException {

        float [][] C = new float[A.length/4][A[0].length];
        
        for(int i = 0; i < A.length/4; i++){
            for(int j = 0; j < A[0].length; j++){
                C[i][j] = A[i+inicio][j];
            }
        }

        return C;
    }

    public float [][] multiplica_matrices(float [][]A, float[][] B) throws RemoteException{

        float [][] C = new float[A.length][A.length];

        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < B.length; j++){
                for(int k = 0; k < A[0].length; k++){

                    C[i][j] += ( A[i][k] * B[j][k] );
                }
            }
        }
        return C;

    }


}
