public interface STATE {
    void decimalInput(String value);
    void operatorInput(String oper);
    default void quitInput(){
        System.out.println("연산 종료");
    }
}
