package lotto.orchestration;

import lotto.domain.PurchasePrice;
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
        PurchasePrice purchasePrice = getPurchasePrice();
    }

    /**
     * 사용자로부터 구입 금액을 입력받고 유효성 검사를 수행한 뒤, 값을 반환합니다.
     * @return 생성된 PurchasePrice 객체
     */
    private PurchasePrice getPurchasePrice() {
        while (true) {
            try {
                String inputPrice = consoleInput.inputPrice();
                return PurchasePrice.from(inputPrice);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
