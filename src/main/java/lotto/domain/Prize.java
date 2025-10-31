package lotto.domain;

import java.util.Arrays;

public enum Prize {
    FULL_BALL(6, false, 2_000_000_000L),
    FIVE_WITH_A_BONUS(5, true, 30_000_000L),
    FIVE_OF_A_KIND(5, false, 1_500_000L),
    FOUR_OF_A_KIND(4, false, 50_000L),
    THREE_OF_A_KIND(3, false, 5_000L),
    MISS(0, false, 0);

    private final int matchCount;
    private final boolean bonusBall;
    private final long prize;

    Prize(int matchCount, boolean bonusBall, long prize) {
        this.matchCount = matchCount;
        this.bonusBall = bonusBall;
        this.prize = prize;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public boolean getBonus() {
        return bonusBall;
    }

    public long getPrize() {
        return prize;
    }

    public static Prize getPrize(int matchCount, boolean bonusBall) {
        if (matchCount < THREE_OF_A_KIND.matchCount) {
            return MISS;
        }

        return Arrays.stream(Prize.values())
                .filter(prize -> prize.getMatchCount() == matchCount)
                .filter(prize -> prize.getBonus() == bonusBall)
                .findFirst()
                .orElse(MISS);
    }
}
