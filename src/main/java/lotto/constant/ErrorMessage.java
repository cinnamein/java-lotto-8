package lotto.constant;

public enum ErrorMessage {

    PREFIX("[ERROR] "),
    MIN_PRICE_ERROR("입력 금액은 1000원 이상이어야 합니다."),
    MAX_PRICE_ERROR("입력 금액은 2147483000원 이하여야 합니다."),
    MULTIPLE_ERROR("입력 금액은 1000원으로 나누어 떨어져야 합니다."),
    PRICE_FORMAT_ERROR("입력 금액은 숫자로만 구성될 수 있습니다."),
    NUMBER_RANGE_ERROR("로또 범위는 1~45까지여야 합니다."),
    NUMBER_COUNT_ERROR("로또 번호는 6개여야 합니다."),
    NUMBER_DUPLICATE_ERROR("로또 번호는 중복될 수 없습니다."),
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX.message + message;
    }
}
