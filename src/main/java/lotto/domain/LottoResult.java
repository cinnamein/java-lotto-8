package lotto.domain;

import java.util.Collections;
import java.util.Map;

public class LottoResult {

    private final Map<Prize, Integer> prizeCount;
    private final double yield;

    private LottoResult(Map<Prize, Integer> prizeCount, double yield) {
        this.prizeCount = Collections.unmodifiableMap(prizeCount);
        this.yield = yield;
    }

    public static LottoResult create(Map<Prize, Integer> prizeCount, double yield) {
        return new LottoResult(prizeCount, yield);
    }

    public Map<Prize, Integer> getPrizeCount() {
        return prizeCount;
    }

    public double getYield() {
        return yield;
    }
}
