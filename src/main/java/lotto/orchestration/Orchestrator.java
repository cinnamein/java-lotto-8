package lotto.orchestration;

import lotto.view.ConsoleInput;

public class Orchestrator {

    private final ConsoleInput consoleInput;

    public Orchestrator(
            ConsoleInput consoleInput
    ) {
        this.consoleInput = consoleInput;
    }

    /**
     * 로또 애플리케이션을 시작합니다.
     */
    public void run() {
        String purchasePrice = getPurchasePrice();
    }

    /**
     * 사용자로부터 로또 구입 금액을 입력받습니다.
     * @return 사용자가 입력한 구입 금액
     */
    private String getPurchasePrice() {
        String inputPrice = consoleInput.inputPrice();
        return inputPrice;
    }
}
