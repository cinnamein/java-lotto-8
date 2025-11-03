package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PurchasePriceTest {

    @DisplayName("유효한 금액(경계값 포함)으로 PurchasePrice 객체가 정상적으로 생성되고 금액을 반환한다.")
    @ParameterizedTest(name = "입력: {0}")
    @ValueSource(strings = {"1000", "50000", "2147483000"})
    void from_유효한_금액으로_객체_생성_및_반환_검증(String inputPrice) {
        long expectedPrice = Long.parseLong(inputPrice);
        assertThat(PurchasePrice.from(inputPrice).getPrice()).isEqualTo(expectedPrice);
    }

    @DisplayName("구매 금액에 따른 getLottoCount()가 정확히 로또 개수를 반환하는지 검증한다.")
    @ParameterizedTest(name = "금액: {0}, 로또 개수: {1}")
    @CsvSource(value = {"1000, 1", "3000, 3", "2147483000, 2147483"})
    void getLottoCount_로또개수_반환_검증(String inputPrice, int expectedCount) {
        assertThat(PurchasePrice.from(inputPrice).getLottoCount()).isEqualTo(expectedCount);
    }

    @DisplayName("구매금액이 허용 범위를 벗어난 경우 IllegalArgumentException이 발생한다.")
    @ParameterizedTest(name = "입력: {0}")
    @ValueSource(strings = {"500", "999", "2147484000"})
    void from_구매금액_범위_예외_테스트(String invalidInput) {
        assertThatThrownBy(() -> PurchasePrice.from(invalidInput))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구매금액이 1000원 단위가 아닐 경우 IllegalArgumentException이 발생한다.")
    @ParameterizedTest(name = "입력: {0}")
    @ValueSource(strings = {"1500", "1001"})
    void from_구매금액_단위_예외_테스트(String invalidInput) {
        assertThatThrownBy(() -> PurchasePrice.from(invalidInput))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구매금액에 문자나 공란이 포함된 경우 IllegalArgumentException이 발생한다.")
    @ParameterizedTest(name = "입력: '{0}'")
    @ValueSource(strings = {"1000a", "abc", " ", ""})
    void from_구매금액_형식_예외_테스트(String invalidInput) {
        assertThatThrownBy(() -> PurchasePrice.from(invalidInput))
                .isInstanceOf(IllegalArgumentException.class);
    }
}