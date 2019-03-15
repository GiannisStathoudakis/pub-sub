import java.util.ArrayList;
import java.util.List;

interface Broker extends Node{

    List<Subscriber> registeredSubscribers = new ArrayList<Subscriber>();
    List<Publisher> registeredPublishers = new ArrayList<Publisher>();

    public void calculateKeys();
    public Publisher acceptConection(Publisher x);
    public Subscriber acceptConection(Subscriber x);
    public void notifyPublisher(String x);
    public void pull(Topic x);
}