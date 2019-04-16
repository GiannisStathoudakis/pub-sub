import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

// given that we have ONLY 2 brokers
public class MegaBroker extends Thread{

    private static ArrayList<String> brokers = new ArrayList<>();
    private Socket connection;
    private ServerSocket ProviderSocket;

    private MegaBroker(Socket connection, ServerSocket providerSocket) {
        this.connection = connection;
        this.ProviderSocket = providerSocket;
    }

    //read a file that contains brokers ip and store it
    private static void broker() throws Exception{
        File file = new File("ip.txt");
        Scanner sc = new Scanner(file).useDelimiter("\n");

        do{
            brokers.add(sc.next());
        }while (sc.hasNext());
    }

    //find the correct broker and sent its ip to the clients
    public void run(){
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try{
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());

            int arithmos_leoforiou = Integer.parseInt(String.valueOf(in.readObject()));
            if (arithmos_leoforiou % 2 == 1) {
                out.writeObject(brokers.get(0));
                out.flush();
            } else {
                out.writeObject(brokers.get(1));
                out.flush();
            }
            in.close();
            out.close();
            connection.close();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    //connect with a client
    public static void main(String[] args) throws Exception{
        broker();
        ServerSocket providerSocket = null;
        Socket connection = null;

        providerSocket = new ServerSocket(4321);
        Thread tr;
        while (true) {
            connection = providerSocket.accept();
            tr = new MegaBroker(connection, providerSocket);
            tr.start();
        }
    }
}