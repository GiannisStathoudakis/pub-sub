interface Subscriber extends Node{

    public void register(Broker x, Topic y);
    public void disconnect(Broker x, Topic y);
    public void visualiseData(Topic x, Value y);
}

class sub implements Subscriber{

    public void register(Broker x, Topic y){}

    public void disconnect(Broker x, Topic y){}

    public void visualiseData(Topic x, Value y){}

    public void init(int x){}

    public void connect(){}

    public void disconect(){}

    public void updateNodes(){}
}