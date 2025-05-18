import java.rmi.Naming;
import java.util.jar.Attributes.Name;
import javax.swing.plaf.synth.SynthStyleFactory;

public class ClienteRMI {

    static int N = 8;
    static float [][] A = new float[N][N];
    static float [][] B = new float[N][N];
    static float [][] C = new float[N][N];


    static void imprimirMatriz(float [][] matriz){

        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz[0].length; j++){
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();   
        }

        return;
    } 


    public static class Cliente extends Thread {

        int nodo;

        Cliente(int nodo){
            this.nodo = nodo;
        }

        public void run() {

            try {
                
                String url = "";

                if(nodo == 1){
                    url = "rmi://20.231.19.203/prueba";
                }else if(nodo == 2){
                    url = "rmi://20.25.64.209/prueba";
                }else if(nodo == 3){
                    url = "rmi://20.231.55.64/prueba";
                }else if(nodo == 4){
                    url = "rmi://20.228.220.68/prueba";
                }

                
                InterfaceRMI r = (InterfaceRMI)Naming.lookup(url);
                
                float [][] A1 = r.separa_matriz(A, 0); 
                float [][] A2 = r.separa_matriz(A, A.length/4); 
                float [][] A3 = r.separa_matriz(A, A.length/2); 
                float [][] A4 = r.separa_matriz(A, (3*A.length)/4); 

                float [][] B1 = r.separa_matriz(B, 0); 
                float [][] B2 = r.separa_matriz(B, B.length/4); 
                float [][] B3 = r.separa_matriz(B, B.length/2); 
                float [][] B4 = r.separa_matriz(B, (3*B.length)/4); 
                
                
                if(nodo == 1){

                    float [][] C1 = r.multiplica_matrices(A1, B1);
                    float [][] C2 = r.multiplica_matrices(A1, B2);
                    float [][] C3 = r.multiplica_matrices(A1, B3);
                    float [][] C4 = r.multiplica_matrices(A1, B4);

                    for(int i = 0; i < N/4; i++){
                        
                        for(int j = 0; j < N/4; j++){
                            
                            C[i][j] = C1[i][j];
                            C[i][j+N/4] = C2[i][j];
                            C[i][j+N/2] = C3[i][j];
                            C[i][j+(3*N)/4] = C4[i][j];
                        
                        }   
                    }
    
                } else if(nodo == 2){

                    float [][] C5 = r.multiplica_matrices(A2, B1);
                    float [][] C6 = r.multiplica_matrices(A2, B2);
                    float [][] C7 = r.multiplica_matrices(A2, B3);
                    float [][] C8 = r.multiplica_matrices(A2, B4);

                    for(int i = 0; i < N/4; i++){
                        
                        for(int j = 0; j < N/4; j++){
                            
                            C[i+N/4][j] = C5[i][j];
                            C[i+N/4][j+N/4] = C6[i][j];
                            C[i+N/4][j+N/2] = C7[i][j];
                            C[i+N/4][j+(3*N)/4] = C8[i][j];
                        
                        }   
                    }
    
                } else if(nodo == 3){

                    float [][] C9 = r.multiplica_matrices(A3, B1);
                    float [][] C10 = r.multiplica_matrices(A3, B2);
                    float [][] C11 = r.multiplica_matrices(A3, B3);
                    float [][] C12 = r.multiplica_matrices(A3, B4);

                    for(int i = 0; i < N/4; i++){
                        
                        for(int j = 0; j < N/4; j++){
                            
                            C[i+N/2][j] = C9[i][j];
                            C[i+N/2][j+N/4] = C10[i][j];
                            C[i+N/2][j+N/2] = C11[i][j];
                            C[i+N/2][j+(3*N)/4] = C12[i][j];
                        
                        }   
                    }
    
                } else {

                    float [][] C13 = r.multiplica_matrices(A4, B1);
                    float [][] C14 = r.multiplica_matrices(A4, B2);
                    float [][] C15 = r.multiplica_matrices(A4, B3);
                    float [][] C16 = r.multiplica_matrices(A4, B4);

                    for(int i = 0; i < N/4; i++){
                        
                        for(int j = 0; j < N/4; j++){
                            
                            C[i+((3*N)/4)][j] = C13[i][j];
                            C[i+((3*N)/4)][j+N/4] = C14[i][j];
                            C[i+((3*N)/4)][j+N/2] = C15[i][j];
                            C[i+((3*N)/4)][j+(3*N)/4] = C16[i][j];
                        
                        }   
                    }
    
                }

            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        } 

    }
    
    public static void main(String[] args) throws Exception {
        
        String url = "rmi://20.231.19.203/prueba";
        InterfaceRMI r = (InterfaceRMI)Naming.lookup(url);
        System.out.println("Soy el cliente");


        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                A[i][j] = i+2*j;
                B[i][j] = 3*i-j;
            }
        }

        if(N == 8) {

            System.out.println("La matriz A es:");
            imprimirMatriz(A);
            System.out.println("La matriz B es:");
            imprimirMatriz(B);
        }

        B = r.transpuesta(B);

        Cliente cliente1 = new Cliente(1);
        cliente1.start();
        cliente1.join();
        System.out.println("\nhilo 1");
       

        Cliente cliente2 = new Cliente(2);
        cliente2.start();
        cliente2.join();
        System.out.println("\nhilo 2");
    

        Cliente cliente3 = new Cliente(3);
        cliente3.start();
        cliente3.join();
        System.out.println("\nhilo 3");
  

        Cliente cliente4 = new Cliente(4);
        cliente4.start();
        cliente4.join();

        if(N == 8) {
            
            System.out.println("La matriz C es:");
            imprimirMatriz(C);

        }

        System.out.println("El checksum es: " + r.checksum(C));

    }
}
