import java.rmi.RemoteException;
import java.rmi.Remote;

public interface InterfaceRMI extends Remote {

    public float checksum(float[][] m) throws RemoteException;
    public float [][] multiplica_matrices(float [][]A, float[][] B) throws RemoteException;
    public float [][] separa_matriz(float [][] A, int inicio) throws RemoteException;
    public float [][] transpuesta (float [][] A) throws RemoteException;

}