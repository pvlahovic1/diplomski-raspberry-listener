package hr.foi.raspberry.listener.threads.observer;

public interface DataSendSubject {

    void addObserver(DataSendObserver observer);

    void removeObserver(DataSendObserver observer);

    void notifyObservers(String data);

}
