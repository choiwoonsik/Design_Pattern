package CafeExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Coffee extends CafeBeverageMaker {

    @Override
    public void brew() {
        System.out.println("커피원두를 우려냅니다.");
    }

    @Override
    public boolean customTopping() {
        System.out.println("추가 옵션을 선택하시겠습니까?");
        return input().equals("y");
    }

    @Override
    public void addTopping() {
        milk();
        if (input().equals("y")) System.out.println("우유 추가...");
        sugar();
        if (input().equals("y")) System.out.println("설탕 추가...");
        shot();
        if (input().equals("y")) System.out.println("샷 추가...");
    }
}
