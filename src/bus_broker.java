import java.util.ArrayList;
//this is a class to store all the important information that we need
//to know about the bus
public class bus_broker {

    private String LineId = null;// bus number
    private ArrayList<String> dromologioInfo = new ArrayList<>();//all routes names
    private ArrayList<String> coordinates = new ArrayList<>();//all routes coordinates

    public void setLineId(String lineId) {
        LineId = lineId;
    }

    public void setdromologioInfo(ArrayList<String> dromologia) {
        dromologioInfo = dromologia;
    }

    public void setCoordinates(ArrayList<String> Coordinates) {
        coordinates = Coordinates;
    }

    public String getLineId() {
        return LineId;
    }

    public ArrayList<String> getdromologioInfo() {
        return dromologioInfo;
    }

    public ArrayList<String> getCoordinates() {
        return coordinates;
    }
}