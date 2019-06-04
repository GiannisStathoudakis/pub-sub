import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class readFile {
    public bus readBusLine() throws Exception{

        bus b;

        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Which bus do i look for?");
            String leoforio_psaxnw = sc.nextLine();
            // read the given file
            File file = new File("busLinesNew.txt");
            sc = new Scanner(file).useDelimiter(",|\n");
            b = new bus();
            boolean f = true;
            // search the file for the given bus and store its info
            do {
                String LineCode = sc.next();
                String LineId = sc.next();
                if (LineId.equals(leoforio_psaxnw)) {
                    b.setLineCode(LineCode);
                    b.setLineId(LineId);
                    b.setDescriptionEnglish(sc.next());
                    f = false;
                    System.out.println("Found it");
                    break;
                } else {
                    sc.next();
                }
            } while (sc.hasNext());

            if (!f) {
                break;
            }
        }
        // read the given file
        File file = new File("RouteCodesNew.txt");
        Scanner sc = new Scanner(file).useDelimiter(",|\n");
        // search the file for the given bus and store its info
        String RouteCode;
        do{
            RouteCode = sc.next();
            if (sc.next().equals(b.getLineCode())){
                b.setRouteCode(RouteCode);
                sc.next();
                b.setDromologioInfo(sc.next());
            }
            else{
                sc.next();
                sc.next();
            }
        }while (sc.hasNext());

        return b;
    }
    // store the coordinates of a specific bus's route in an array_list
    public ArrayList<String[]> bus_Position(String RouteCode) throws Exception {

        String[] Sintetagmenes_twra;
        // read the given file
        File file = new File("busPositionsNew.txt");
        Scanner sc = new Scanner(file).useDelimiter(",|\n");
        //search the file for the route's coordinates
        ArrayList<String[]> sintentagmenes = new ArrayList<>();
        String routcode;
        do {
            sc.next();
            routcode = sc.next();
            if (routcode.equals(RouteCode)) {
                Sintetagmenes_twra = new String[2];
                sc.next();
                Sintetagmenes_twra[0] = sc.next();
                Sintetagmenes_twra[1] = sc.next();

                sc.next();
                sintentagmenes.add(Sintetagmenes_twra);
            } else {
                sc.next();
                sc.next();
                sc.next();
                sc.next();
            }

        } while (sc.hasNext());

        return sintentagmenes;
    }
}
