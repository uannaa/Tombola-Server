package server;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

    ServerSocket server = null;
    Socket socketClient = null;
    
    int porta = 6789;
    
    DataInputStream in;
    DataOutputStream out;
    
    
    public Socket attendi() throws IOException {
       
        try {
        
            System.out.println("[0] - Inizializzo il server");
            server = new ServerSocket(porta);

            System.out.println("[1] - Server in ascolto sulla porta " + porta);
            socketClient = server.accept();

            System.out.println("[2] - Connessione stabilita!");

            server.close();

            // Inizializza gli stream di input e output
            in = new DataInputStream(socketClient.getInputStream());
            out = new DataOutputStream(socketClient.getOutputStream());

        } catch (IOException e) {
            
            e.printStackTrace();
            
        }

        
        return socketClient;
    }
    
    public void conferma() throws IOException {
        
        String conferma = in.readLine();
        System.out.print("[3] - [C] - " + conferma + "\n");
        
    }
    
    public void ricevi() throws IOException {
        
        String punto = in.readLine();
        System.out.println(punto + "\n");
        
    }
    
    public static void main(String[] args) throws IOException {
        
        int tombolone[][] = new int[9][10];
        
        Server s = new Server();
        s.attendi();
        s.conferma();
        
        System.out.print("[4] - Creazione Tombolone" + "\n");
        createTabellone(tombolone);
        printTombolone(tombolone);
        System.out.println("\n" + "[5] - Tombolone creato e printato.");
        
        
        s.genera();
        System.out.println("[6] - Numeri generati con successo!" + "\n");
        
        s.estrai();
    
    }
    
    //Estrazione
    public int[] genera() throws IOException {
        
        int[] array = new int[90];
        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            
            int numeroCasuale;
            boolean numeroDiverso;
            
            do {
                
                numeroCasuale = random.nextInt(90) + 1;
                numeroDiverso = true;
                
                for (int j = 0; j < i; j++) {
                    
                    if (array[j] == numeroCasuale) {
                        
                        numeroDiverso = false;
                        break;
                        
                    }
                    
                }
                
            } while (!numeroDiverso);
            
            array[i] = numeroCasuale;
        }
        
        return array;
    }
    
    public void estrai() throws IOException {
        
        int estraibili[] = genera();
        int select;
        int estratto;
        
        Scanner s = new Scanner(System.in);

        for (int i = 0; i < estraibili.length; i++) {
            
            System.out.println("Vuoi estrarre un numero?");
            System.out.println("[1] Si" + "\n" + "[2] No");

            select = s.nextInt();

            if (select == 1) {
                
                estratto = estraibili[i];
                out.writeInt(estratto);
                System.out.println("Estratto: " + estratto);
                
            } else if (select == 2) {
                
                server.close();
                break;
                
            } else {
                
                System.err.println("Errore nell'estrazione!");
                
            }
        
        }
        
        // Termino la trasmissione
        out.writeInt(-1);
    }
    
    //Tabellone
    public static void createTabellone(int matrice[][]) {
        
        System.out.println();
        
        int k = 1;
        
        for (int i = 0; i < matrice.length; i++) {
            
            for (int j = 0; j < matrice[i].length; j++) {
                
                matrice[i][j] = k;
                k++;
                
            }
            
        }
        
    }
    
    public static void printTombolone(int matrice[][]) {
        
        for (int i = 0; i < matrice.length; i ++) {
            
            for (int j = 0; j < matrice[i].length; j++) {
                
                if (matrice[i][j] < 10) {
                    
                    System.out.print("0");
                    
                }
                
                
                if (matrice[i][j] == 91) {
                    
                    System.out.print("||" + " ");
                    
                    if (j == 4) {
                        
                        System.out.print("| ");
                        
                    }
                    
                }
                

                if (matrice[i][j] != 91) {
                    
                    System.out.print(matrice[i][j] + " "); 
                    
                }
                
                if(matrice[i][j] % 10 == 5) {
                    
                    System.out.print("| ");
                    
                }
                
                if (j == 9) {
                    
                    System.out.println();
                    
                }
                
            }
            
        }
        
    }
}
