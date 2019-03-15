import java.util.List;
import java.util.ArrayList;

interface Node{

    void init(int x);
    void connect();
    void discconect();
    void updateNodes();
}

class node_class implements Node{

    private static List<Broker> br = new ArrayList<Broker>();

    public void init(int x){}

    public void connect(){}

    public void discconect(){}

    public void updateNodes(){}

    public void setBr(Broker x){
        br.add(x);
    }

    public List<Broker> getBr(){
        return br;
    }
}
