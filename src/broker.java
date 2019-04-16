import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class broker extends Thread {

    static ArrayList<bus_broker> bus_have = new ArrayList<>();//which buses this broker has

    private Socket connection;
    private ServerSocket ProviderSocket;

    //broker constructor
    public broker(Socket connection, ServerSocket providerSocket) {
        this.connection = connection;
        this.ProviderSocket = providerSocket;
    }

    private void publisher(ObjectInputStream in) {

        try {
            //receive bus name
            String message = String.valueOf(in.readObject());

            bus_broker b = new bus_broker();

            bus_have.add(b);
            int b_position = bus_have.size() - 1;
            bus_have.get(b_position).setLineId(message);

            //receive the bus's routes names
            Object object = in.readObject();
            bus_have.get(b_position).setdromologioInfo((ArrayList<String>) object);

            ArrayList<String> arr;
            //receive the coordinates for each route
            while (true) {

                object = in.readObject();
                arr = (ArrayList<String>) object;
                bus_have.get(b_position).setCoordinates(arr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                connection.close();
                ProviderSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void subscriber(ObjectInputStream in, ObjectOutputStream out) {

        try {
            //receive which bus the subscriber wants
            String leoforio = String.valueOf(in.readObject());

            bus_broker b = null;
            //find the location of the bus inside the arraylist bus_have
            for (bus_broker i : bus_have) {
                if (i.getLineId().equals(leoforio)) {
                    b = i;
                    break;
                }
            }
            //inform subscriber if the broker has the bus or not
            if(b == null){
                out.writeObject("dont_have_it");
                out.flush();
            }else {
                out.writeObject("have_it");
                out.flush();

                //inform the subscriber about the possible routes
                out.writeObject(b.getdromologioInfo());
                out.flush();
                //receive answer from subscriber
                int apantisi = Integer.parseInt(String.valueOf(in.readObject()));

                //sent the coordinates to the subscriber
                while (true) {
                    String current_coordinates = b.getCoordinates().get(apantisi);
                    out.writeObject(current_coordinates);
                    out.flush();
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                connection.close();
                ProviderSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());

            String with_whom_i_speek = String.valueOf(in.readObject());

            System.out.println(with_whom_i_speek);
            //run the correct method according to type of client
            if (with_whom_i_speek.equals("publisher")) {
                publisher(in);
            } else {
                subscriber(in, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //receive client's connection
    public static void main(String[] args){
        ServerSocket providerSocket = null;
        Socket connection = null;

        try {
            providerSocket = new ServerSocket(4321);
            Thread tr;
            while (true) {
                connection = providerSocket.accept();
                tr = new broker(connection, providerSocket);
                tr.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}