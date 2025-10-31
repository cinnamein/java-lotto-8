package lotto.domain;

import lotto.constant.ErrorMessage;
import lotto.constant.PriceConfig;

/**
 * 구매한 로또 금액 도메인 클래스입니다.
 */
public class PurchasePrice {

    private final long price;

    private PurchasePrice(long price) {
        this.price = price;
    }

    /**
     * 유효성 검사 후 PurchasePrice 객체 반환
     * @param inputPrice 입력한 구매 금액
     * @return PurchasePrice 객체
     */
    public static PurchasePrice from(String inputPrice) {
        long inputPriceLong = parseToLong(inputPrice);
        validateRange(inputPriceLong);
        validateMultiple(inputPriceLong);
        return new PurchasePrice(inputPriceLong);
    }

    public long getPrice() {
        return price;
    }

    /**
     * 입력한 값을 숫자로 변환합니다.
     * @param inputPrice 입력한 구매 금액
     * @return long으로 변경한 구매 금액
     * @throws IllegalArgumentException 금액에 문자가 포함되었을 경우
     */
    private static long parseToLong(String inputPrice) {
        try {
            return Long.parseLong(inputPrice);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.PRICE_FORMAT_ERROR.getMessage());
        }
    }

    /**
     * 구매 금액이 최소 및 최대 금액을 만족하는지 검증합니다.
     * @param price 구매 금액
     * @throws IllegalArgumentException 금액이 최소 혹은 최대 금액 바깥에 있을 경우
     */
    private static void validateRange(long price) {
        if (price < PriceConfig.MIN_PRICE.getValue()) {
            throw new IllegalArgumentException(ErrorMessage.MIN_PRICE_ERROR.getMessage());
        }
        if (price > PriceConfig.MAX_PRICE.getValue()) {
            throw new IllegalArgumentException(ErrorMessage.MAX_PRICE_ERROR.getMessage());
        }
    }

    /**
     * 구매 금액이 1000원의 배수인지 검증합니다.
     * @param price 구매 금액
     * @throws IllegalArgumentException 금액이 1000원 단위가 아닐 경우
     */
    private static void validateMultiple(long price) {
        if (price % PriceConfig.MULTIPLE_OF.getValue() != 0) {
            throw new IllegalArgumentException(ErrorMessage.MULTIPLE_ERROR.getMessage());
        }
    }

    /**
     * 로또 구매 금액으로 로또를 몇 장 구매할 수 있는지 계산합니다.
     * @return 로또 구매 횟수
     */
    public int getLottoCount() {
        return (int) (this.price / PriceConfig.MULTIPLE_OF.getValue());
    }
}
