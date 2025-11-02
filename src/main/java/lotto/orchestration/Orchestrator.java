package lotto.orchestration;

import java.util.ArrayList;
import java.util.List;
import lotto.Lotto;
import lotto.domain.LottoResult;
import lotto.domain.PurchasePrice;
import lotto.domain.WinningLotto;
import lotto.service.LottoResultCalculator;
import lotto.view.ConsoleInput;
import lotto.view.ConsoleOutput;

/**
 * 애플리케이션의 전체 흐름을 제어하는 오케스트레이터 클래스입니다.
 */
public class Orchestrator {

    private final ConsoleInput consoleInput;
    private final ConsoleOutput consoleOutput;
    private final LottoResultCalculator lottoResultCalculator;

    public Orchestrator(
            ConsoleInput consoleInput,
            ConsoleOutput consoleOutput,
            LottoResultCalculator lottoResultCalculator
    ) {
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
        this.lottoResultCalculator = lottoResultCalculator;
    }

    /**
     * 로또 애플리케이션을 시작합니다.
     */
    public void run() {
        PurchasePrice purchasePrice = getPurchasePrice();
        int purchaseCount = purchasePrice.getLottoCount();
        List<Lotto> lottos = purchaseLotto(purchaseCount);
        WinningLotto winningLotto = getWinningLotto();
        printResult(lottos, winningLotto, purchasePrice);
    }

    /**
     * 사용자로부터 구입 금액을 입력받고 유효성 검사를 수행한 뒤, 값을 반환합니다.
     *
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

    /**
     * 계산된 개수만큼 로또를 구매하고, 랜덤한 번호를 발급합니다.
     *
     * @param purchaseCount 구매할 로또의 개수
     * @return 발급한 로또 번호 리스트
     */
    private List<Lotto> purchaseLotto(int purchaseCount) {
        consoleOutput.printLottoCount(purchaseCount);
        List<Lotto> purchasedLottos = new ArrayList<>();
        for (int i = 0; i < purchaseCount; i++) {
            Lotto purchasedLotto = Lotto.getRandomNumbers();
            purchasedLottos.add(purchasedLotto);
            consoleOutput.printLottoNumber(purchasedLotto);
        }
        return purchasedLottos;
    }

    /**
     * 사용자로부터 당첨 번호와 보너스 번호를 입력받고 유효성 검사를 수행한 뒤, 값을 반환합니다.
     *
     * @return 생성된 WinningLotto 객체
     */
    private WinningLotto getWinningLotto() {
        List<Integer> winningNumbers = getWinningNumbers();
        while (true) {
            try {
                int bonusNumber = getBonusNumber();
                return WinningLotto.from(winningNumbers, bonusNumber);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 사용자로부터 당첨 번호를 입력받고 유효성 검사를 수행한 뒤, 값을 반환합니다.
     *
     * @return 당첨 번호 6자리 리스트
     */
    private List<Integer> getWinningNumbers() {
        while (true) {
            try {
                String inputWinningNumbers = consoleInput.inputWinningNumbers();
                return WinningLotto.parseWinningNumbers(inputWinningNumbers);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 사용자로부터 보너스 번호를 입력받고 유효성 검사를 수행한 뒤, 값을 반환합니다.
     *
     * @return 보너스 번호
     */
    private int getBonusNumber() {
        while (true) {
            try {
                String inputBonusNumber = consoleInput.inputBonusNumber();
                return WinningLotto.parseBonusNumber(inputBonusNumber);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 로또 당첨 결과를 계산하고 출력합니다.
     *
     * @param lottos        구매한 로또 리스트
     * @param winningLotto  당첨 번호
     * @param purchasePrice 구매한 로또 가격
     */
    private void printResult(List<Lotto> lottos, WinningLotto winningLotto, PurchasePrice purchasePrice) {
        LottoResult lottoResult = lottoResultCalculator.calculateResult(lottos, winningLotto, purchasePrice);
        consoleOutput.printResult(lottoResult);
        consoleOutput.printYield(lottoResult.getYield());
    }
}
