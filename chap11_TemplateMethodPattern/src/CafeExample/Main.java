package CafeExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        CafeBeverageMaker cafe;
        System.out.println("Hi~ 여기는 woonsik Cafe 입니다!\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("메뉴를 골라주세요. (coffee, tea)");
        String s = br.readLine();
        if (s.equals("coffee")) {
            System.out.println("[커피 주문 시작]");
            cafe = new Coffee();
            cafe.prepareBeverage();
        } else if (s.equals("tea")) {
            System.out.println("[티 주문 시작]");
            cafe = new Tea();
            cafe.prepareBeverage();
        } else {
            System.out.println("없는 메뉴입니다.");
        }
    }
}
