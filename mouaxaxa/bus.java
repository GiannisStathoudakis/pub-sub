import java.util.ArrayList;

public class bus {

    private String LineCode; //bus id
    private String LineId;// bus number
    private String DescriptionEnglish;//route name
    private ArrayList<String> RouteCode = new ArrayList<>();//all the codes of buses routes
    private ArrayList<String> dromologioInfo = new ArrayList<>();//all routes names

    public void setLineCode(String LineCode_isodos) {
        LineCode = LineCode_isodos;
    }

    public void setLineId(String LineId_isodos) {
        LineId = LineId_isodos;
    }

    public void setDescriptionEnglish(String descriptionEnglish) {
        DescriptionEnglish = descriptionEnglish;
    }

    public void setRouteCode(String routeCode) {
        RouteCode.add(routeCode);
    }

    public void setDromologioInfo(String dromologio) {
        dromologioInfo.add(dromologio);
    }

    public String getLineCode() {
        return LineCode;
    }

    public String getLineId() {
        return LineId;
    }

    public String getDescriptionEnglish() {
        return DescriptionEnglish;
    }

    public ArrayList<String> getRouteCode() {
        return RouteCode;
    }

    public ArrayList<String> getDromologioInfo() {
        return dromologioInfo;
    }
}