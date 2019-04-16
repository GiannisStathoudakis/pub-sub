import java.util.ArrayList;
import java.util.Scanner;
import java.net.*;
import java.io.*;

public class subscriber {

    private static Socket requestSocket = null;
    private static ObjectOutputStream out = null;
    private static ObjectInputStream in = null;
    private static Scanner sc = new Scanner(System.in);

    // connect subscriber to Megabroker and broker
    private static void connect(String ip){
        requestSocket = null;
        try{
            requestSocket = new Socket(InetAddress.getByName(ip), 4321);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            in = new ObjectInputStream(requestSocket.getInputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //checking if the given ip has the correct ip format
    private static boolean validIP(String ip) {
        try {
            if (ip == null || ip.isEmpty()) {
                return false;
            }
            String[] parts = ip.split("\\.");
            if (parts.length != 4) {
                return false;
            }
            for (String s : parts) {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255)) {
                    return false;
                }
            }
            if (ip.endsWith(".")) {
                return false;
            }
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static void main(String[] args) {

        String ip = null;
    //insert Megabroker's ip and check if it's correct
        while (true) {
            System.out.println("Insert Megabroker's ip");
            ip = sc.nextLine();
            if (validIP(ip)) {
                break;
            }
            System.out.println("No valid input");
        }

        Object object;

        System.out.println("Which bus do you want?");
        String leoforio = sc.nextLine();


        //request MegaBroker
        try {
            connect(ip);
            out.writeObject(leoforio);
            out.flush();

            ip = String.valueOf(in.readObject()); // receive the correct broker's ip

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //connect broker
        connect(ip);

        try{
            //inform the broker that we are the subscriber
            out.writeObject("subscriber");
            out.flush();
            //send which bus we want
            out.writeObject(leoforio);
            out.flush();
            // broker informs us if it has our bus
            String bus_exists = String.valueOf(in.readObject());
            if(bus_exists.equals("have_it")){
                // receive an array_list with the names of bus routes
                object = in.readObject();
                ArrayList<String> dromologia = (ArrayList<String>) object;

                int j = 0;// number of routes
                for (String i : dromologia) {
                    j++;
                    System.out.println("Press: " + j + " for " + i);
                }
                //check if the given input is valid or not
                int leo;
                while (true) {
                    leo = sc.nextInt();
                    if (leo > 0 && leo <= j) {
                        break;
                    }
                    System.out.println("No valid input");
                }
                //inform broker for which bus route we want
                out.writeObject(leo - 1);
                out.flush();
                //print the bus coordinates for the route we want
                while (true) {
                    System.out.println(String.valueOf(in.readObject()));
                }
            }else{
                System.out.println("The bus doesn't exist");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}