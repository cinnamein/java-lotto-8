package lotto.view;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lotto.Lotto;
import lotto.domain.LottoResult;
import lotto.domain.Prize;

public class ConsoleOutput {

    /**
     * 구입한 로또 수를 출력합니다.
     *
     * @param count 구입한 로또 수
     */
    public void printLottoCount(int count) {
        System.out.println("\n" + count + "개를 구매했습니다.");
    }

    /**
     * 로또 번호를 출력합니다.
     *
     * @param lotto 로또 객체
     */
    public void printLottoNumber(Lotto lotto) {
        List<Integer> numbers = lotto.getNumbers();
        Collections.sort(numbers);
        System.out.println(numbers);
    }

    /**
     * 로또 당첨 결과를 출력합니다.
     *
     * @param lottoResult
     */
    public void printResult(LottoResult lottoResult) {
        Map<Prize, Integer> prizeCount = lottoResult.getPrizeCount();
        List<Prize> allPrizes = sortPrizes();
        System.out.println("\n당첨 통계");
        System.out.println("---");
        for (Prize prize : allPrizes) {
            int count = prizeCount.getOrDefault(prize, 0);
            System.out.println(formatPrizeLine(prize, count));
        }
    }

    /**
     * 총 수익률을 출력합니다.
     *
     * @param yield 수익률
     */
    public void printYield(double yield) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###.#");
        System.out.println("총 수익률은 " + decimalFormat.format(yield) + "%입니다.");
    }

    /**
     * 당첨 결과를 순서에 맞게 정렬합니다.
     *
     * @return 정렬된 결과
     */
    private List<Prize> sortPrizes() {
        return Arrays.stream(Prize.values())
                .filter(prize -> prize != Prize.MISS)
                .sorted(Comparator.comparing(Prize::getMatchCount)
                        .thenComparing(Prize::getBonus))
                .collect(Collectors.toList());
    }

    /**
     * 주어진 당첨 등수과 횟수를 출력 형식에 맞춰 포맷합니다.
     *
     * @param prize 포맷팅할 당첨 등급 객체
     * @param count 해당 등급의 당첨 횟수
     * @return 포맷팅된 결과 문자열
     */
    private String formatPrizeLine(Prize prize, int count) {
        if (prize == Prize.FIVE_WITH_A_BONUS) {
            return prize.getMatchCount() + "개 일치, 보너스 볼 일치 (" + formatPrice(prize.getPrize()) + ") - " + count + "개";
        }
        return prize.getMatchCount() + "개 일치 (" + formatPrice(prize.getPrize()) + ") - " + count + "개";
    }

    /**
     * 금액을 천 단위마다 쉼표로 구분하고 '원'을 붙인 문자열로 반환합니다.
     *
     * @param price 포맷팅할 금액
     * @return 쉼표와 '원'이 포함된 포맷팅된 문자열
     */
    private String formatPrice(long price) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###원");
        return decimalFormat.format(price);
    }
}
