package lotto.constant;

public enum PriceConfig {

    MAX_PRICE(2147483000),
    MIN_PRICE(1000),
    MULTIPLE_OF(1000),
    ;

    private final long value;

    PriceConfig(long value) {
        this.value = value;
    }
}
