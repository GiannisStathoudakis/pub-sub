interface Publisher extends Node{

    public void getBrokerList();
    public Broker hashTopic(Topic x);
    public void push(Topic x, Value y);
    public void notifyFailure(Broker x);
}

class pub implements Publisher{

    public void getBrokerList(){}

    public Broker hashTopic(Topic x){
        return null;
    }

    public void push(Topic x, Value y){}

    public void notifyFailure(Broker x){}

    public void init(int x){}

    public void connect(){}

    public void discconect(){}

    public void updateNodes(){}
}