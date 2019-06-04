import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class publisher extends Thread {

    private static ArrayList<String> coordinates = new ArrayList<>();

    private String routeCode;
    private int where_i_go_in_arraylist;

    private publisher(String RouteCode, int i){
        routeCode = RouteCode;
        where_i_go_in_arraylist = i;
    }

    //check if given ip is valid
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

    public void run(){
        //return the same bus object that we have in main but also with coordinates for this route
        readFile rf = new readFile();
        ArrayList<String[]> x_y = null;

        try {
            x_y = rf.bus_Position(routeCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String message;

        while (true){
            int i = 0;

            do{
                message =x_y.get(i)[0] + " " + x_y.get(i)[1];

                coordinates.set(where_i_go_in_arraylist, message);
                i++;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }while (i < x_y.size());
        }
    }

    public static void main(String[] args) {

        readFile rf = new readFile();
        bus b = null;
        try {
            //return bus Object
            b = rf.readBusLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Socket requestSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("Please insert Megabroker's ip");
        String ip;

        //receive and check if the given ip is valid
        while (true){
            ip = sc.nextLine();
            if(validIP(ip)){
                break;
            }
            System.out.println("no valid ip, please try again");
        }

		// EINAI SE COMMENT GIA TO ANDROID APP
        //request MegaBroker
        /*try {
            requestSocket = new Socket(InetAddress.getByName(ip), 4321);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            in = new ObjectInputStream(requestSocket.getInputStream());

            out.writeObject(b.getLineId());//send the line id of the bus that the publisher has
            out.flush();

            ip = String.valueOf(in.readObject());//receive the ip of broker that we should connect to
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }*/

        //request broker
        try{
            //creating a publisher client
            requestSocket = new Socket(InetAddress.getByName(ip), 4321);

            out = new ObjectOutputStream(requestSocket.getOutputStream());
            in = new ObjectInputStream(requestSocket.getInputStream());

            //we create as many threads as routes the bus has
            for (int i = 0; i < b.getRouteCode().size(); i++){
                coordinates.add(null);
                Thread t = new publisher(b.getRouteCode().get(i), i);
                t.start();
            }
            //inform broker that this connection is from a publisher
            out.writeObject("publisher");
            out.flush();
            //send bus id
            out.writeObject(b.getLineId());
            out.flush();
            //send the name of the routes
            out.writeObject(b.getDromologioInfo());
            out.flush();
            // send all the time all routes coordinates (every 1 sec)
            while(true){
                TimeUnit.SECONDS.sleep(1);
                ArrayList<String> arr = coordinates;
                out.writeObject(arr);
                out.flush();
                out.reset();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}