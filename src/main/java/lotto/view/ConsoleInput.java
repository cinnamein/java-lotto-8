package lotto.view;

import camp.nextstep.edu.missionutils.Console;

public class ConsoleInput {

    public String inputPrice() {
        System.out.println("구입 금액을 입력해 주세요.");
        return Console.readLine();
    }
}
