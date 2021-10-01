public interface Subject {
    void subscribe(Observer observer);
    void unSubscribe(Observer observer);
    void notifyWindow(String msg);
}
