package lotto.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import lotto.Lotto;
import lotto.domain.LottoResult;
import lotto.domain.Prize;
import lotto.domain.PurchasePrice;
import lotto.domain.WinningLotto;

/**
 * 로또 당첨 등수를 계산하는 클래스입니다.
 */
public class LottoResultCalculator {

    /**
     * 로또 당첨 횟수와 수익률을 계산합니다.
     * @param lottos 구매한 로또 리스트
     * @param winningLotto 당첨 숫자
     * @param purchasePrice 구매한 로또 금액
     * @return 로또 당첨 결과
     */
    public LottoResult calculateResult(List<Lotto> lottos, WinningLotto winningLotto, PurchasePrice purchasePrice) {
        HashMap<Prize, Integer> prizeCount = new HashMap<>();
        for (Lotto lotto : lottos) {
            Prize prize = checkWinningPrize(lotto, winningLotto);
            prizeCount.merge(prize, 1, Integer::sum);
        }
        double yield = calculateYield(prizeCount, purchasePrice);
        return LottoResult.create(prizeCount, yield);
    }

    /**
     * 구매한 로또의 당첨 등수를 계산한 뒤 등수를 반환합니다.
     *
     * @param lotto        구매한 로또 객체
     * @param winningLotto 당첨 번호 객체
     */
    private Prize checkWinningPrize(Lotto lotto, WinningLotto winningLotto) {
        boolean isBonusMatch = checkBonusNumber(lotto, winningLotto);
        int matchCount = checkWinningNumber(lotto, winningLotto);
        return Prize.getPrize(matchCount, isBonusMatch);
    }

    /**
     * 당첨 번호가 몇 개인지 계산합니다.
     *
     * @param lotto        구매한 로또 객체
     * @param winningLotto 당첨 번호 객체
     * @return 당첨된 일반 번호 수
     */
    private int checkWinningNumber(Lotto lotto, WinningLotto winningLotto) {
        List<Integer> lottoNumbers = lotto.getNumbers();
        return (int) lottoNumbers.stream()
                .filter(winningLotto.getNumbers()::contains)
                .count();
    }

    /**
     * 보너스 번호 당첨 여부를 판별합니다.
     *
     * @param lotto        구매한 로또 객체
     * @param winningLotto 당첨 번호 객체
     * @return 보너스 번호 당첨 여부
     */
    private boolean checkBonusNumber(Lotto lotto, WinningLotto winningLotto) {
        return lotto.getNumbers().contains(winningLotto.getBonusNumber());
    }

    /**
     * 당첨 금액의 수익률을 계산합니다.
     *
     * @param prizeCount 당첨 등수별 횟수
     * @return 당첨 금액 총합
     */
    private double calculateYield(HashMap<Prize, Integer> prizeCount, PurchasePrice purchasePrice) {
        long totalPrize = prizeCount.entrySet().stream()
                .mapToLong(entry -> entry.getKey().getPrize() * (long) entry.getValue())
                .sum();
        BigDecimal prize = new BigDecimal(totalPrize);
        BigDecimal price = new BigDecimal(purchasePrice.getPrice());
        BigDecimal percentage = new BigDecimal("100");
        BigDecimal yield = prize.multiply(percentage).divide(price, 5, RoundingMode.HALF_UP);
        BigDecimal finalYield = yield.setScale(1, RoundingMode.HALF_UP);
        return finalYield.doubleValue();
    }
}
