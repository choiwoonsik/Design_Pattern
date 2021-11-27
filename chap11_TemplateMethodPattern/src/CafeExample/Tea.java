package CafeExample;

public class Tea extends CafeBeverageMaker {
    @Override
    public void brew() {
        System.out.println("1: 우롱티 2: 보리티 3: 녹차티 4: 보스니아티 5: 티티");
        switch (Integer.parseInt(input())) {
            case 1:
                System.out.println("우롱티를 우립니다.");
                break;
            case 2:
                System.out.println("보리티를 우립니다.");
                break;
            case 3:
                System.out.println("녹차티를 우립니다.");
                break;
            case 4:
                System.out.println("보스니아티를 우립니다.");
                break;
            case 5:
                System.out.println("TT ~ TT");
                break;
            default:
                System.out.println("메뉴에 없는 주문입니다. 탈락~ 빵!");
        }
    }

    @Override
    public boolean customTopping() {
        System.out.println("추가 옵션을 선택하시겠습니까?");
        return input().equals("y");
    }

    @Override
    public void addTopping() {
        lemon();
        if (input().equals("y")) System.out.println("레몬을 올립니다...");
    }
}
