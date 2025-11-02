package lotto.view;

import camp.nextstep.edu.missionutils.Console;

/**
 * 사용자로부터 콘솔 입력을 담당하는 클래스입니다.
 */
public class ConsoleInput {

    /**
     * 사용자로부터 구입 금액을 입력받습니다.
     *
     * @return 구입한 금액
     */
    public String inputPrice() {
        System.out.println("구입 금액을 입력해 주세요.");
        return Console.readLine();
    }

    /**
     * 사용자로부터 당첨 번호를 입력받습니다.
     *
     * @return 당첨 번호 6자리
     */
    public String inputWinningNumbers() {
        System.out.println("당첨 번호를 입력해 주세요.");
        return Console.readLine();
    }

    /**
     * 사용자로부터 보너스 번호를 입력받습니다.
     *
     * @return 보너스 번호 1자리
     */
    public String inputBonusNumber() {
        System.out.println("\n보너스 번호를 입력해 주세요.");
        return Console.readLine();
    }
}
