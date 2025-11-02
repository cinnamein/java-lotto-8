package lotto.domain;

import java.util.Collections;
import java.util.Map;

/**
 * 로또 당첨 결과 도메인 클래스입니다.
 */
public class LottoResult {

    private final Map<Prize, Integer> prizeCount;
    private final double rateOfReturn;

    private LottoResult(Map<Prize, Integer> prizeCount, double rateOfReturn) {
        this.prizeCount = Collections.unmodifiableMap(prizeCount);
        this.rateOfReturn = rateOfReturn;
    }

    public static LottoResult create(Map<Prize, Integer> prizeCount, double rateOfReturn) {
        return new LottoResult(prizeCount, rateOfReturn);
    }

    public Map<Prize, Integer> getPrizeCount() {
        return prizeCount;
    }

    public double getRateOfReturn() {
        return rateOfReturn;
    }
}
