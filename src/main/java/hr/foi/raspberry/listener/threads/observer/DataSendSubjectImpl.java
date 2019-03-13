package hr.foi.raspberry.listener.threads.observer;

import java.util.ArrayList;
import java.util.List;

public class DataSendSubjectImpl implements DataSendSubject {

    List<DataSendObserver> observers;

    public DataSendSubjectImpl() {
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(DataSendObserver observer) {
        if (!this.observers.contains(observer)) {
            this.observers.add(observer);
        }
    }

    @Override
    public void removeObserver(DataSendObserver observer) {
        if (this.observers.contains(observer)) {
            this.observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers(String data) {
        for (DataSendObserver o : observers) {
            o.update(data);
        }
    }
}
