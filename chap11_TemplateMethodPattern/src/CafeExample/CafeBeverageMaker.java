package CafeExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class CafeBeverageMaker {
    public final void prepareBeverage() {
        boilWater();
        brew();
        if (takeIn()) {
            pourInTakeOutCup();
        } else {
            pourInCup();
        }
        if (customTopping())
            addTopping();
        finish();
    }

    public abstract void brew();

    public abstract boolean customTopping();

    public abstract void addTopping();

    public void lemon() {
        System.out.println("레몬을 추가할까요?");
    }

    public void milk() {
        System.out.println("우유를 추가할까요?");
    }

    public void sugar() {
        System.out.println("설탕을 추가할까요?");
    }

    public void shot() {
        System.out.println("샷을 추가할까요?");
    }

    public void boilWater() {
        System.out.println("물을 끓입니다.");
    }

    public boolean takeIn() {
        System.out.println("테이크 아웃입니까? (y/n)");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.readLine().equals("y");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void pourInCup() {
        System.out.println("유리컵에 음료를 따릅니다.");
    }

    public void pourInTakeOutCup() {
        System.out.println("일회용컵에 음료를 따릅니다.");
    }

    public void finish() {
        System.out.println("음료가 준비되었습니다. 맛있게 드세요 :)");
    }

    public String input() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.readLine().substring(0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "n";
    }
}
