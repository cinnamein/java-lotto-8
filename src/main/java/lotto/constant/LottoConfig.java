package lotto.constant;

public enum LottoConfig {

    MIN_NUMBER(1),
    MAX_NUMBER(45),
    NUMBER_COUNT(6),
    ;

    private final long value;

    LottoConfig(long value) {
        this.value = value;
    }
}
