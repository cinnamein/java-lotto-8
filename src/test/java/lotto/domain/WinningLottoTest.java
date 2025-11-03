package lotto.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class WinningLottoTest {

    private List<Integer> validWinningNumbers;
    private int validBonusNumber;

    @BeforeEach
    void setUp() {
        validWinningNumbers = List.of(1, 2, 3, 4, 5, 6);
        validBonusNumber = 7;
    }

    @DisplayName("유효한 번호로 WinningLotto 객체가 정상적으로 생성되고 번호를 반환한다.")
    @Test
    void from_유효한_번호로_객체_생성_및_반환_검증() {
        List<Integer> expectedNumbers = List.of(1, 2, 3, 4, 5, 6);
        WinningLotto winningLotto = WinningLotto.from(expectedNumbers, validBonusNumber);
        assertAll(
                () -> assertThat(winningLotto).isNotNull(),
                () -> assertThat(winningLotto.getNumbers()).isEqualTo(expectedNumbers),
                () -> assertThat(winningLotto.getBonusNumber()).isEqualTo(validBonusNumber)
        );
    }

    @DisplayName("당첨 번호와 보너스 번호가 중복될 경우 예외가 발생한다.")
    @Test
    void from_당첨_보너스_번호중복_예외_테스트() {
        int duplicatedBonus = 6;
        assertThatThrownBy(() -> WinningLotto.from(validWinningNumbers, duplicatedBonus))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호가 로또 범위(1~45)를 벗어나면 예외가 발생한다.")
    @ParameterizedTest(name = "보너스: {0}")
    @ValueSource(ints = {0, 46})
    void from_보너스_번호_범위_예외_테스트(int invalidBonus) {
        assertThatThrownBy(() -> WinningLotto.from(validWinningNumbers, invalidBonus))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호 문자열을 파싱하여 List<Integer>를 정상적으로 반환한다.")
    @ParameterizedTest(name = "입력: {0}")
    @ValueSource(strings = {"1,2,3,4,5,6"})
    void parseWinningNumbers_파싱_성공_검증(String input) {
        List<Integer> numbers = WinningLotto.parseWinningNumbers(input);
        assertThat(numbers).hasSize(6);
        assertThat(numbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("당첨 번호 파싱 시 문자가 포함되거나 공란이 있으면 예외가 발생한다.")
    @ParameterizedTest(name = "입력: '{0}'")
    @ValueSource(strings = {"1,2a,3,4,5,6", "1,2,3,4,5,", "1, ,3,4,5,6"})
    void parseWinningNumbers_파싱_형식_예외_테스트(String invalidInput) {
        assertThatThrownBy(() -> WinningLotto.parseWinningNumbers(invalidInput))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호의 개수가 6개가 아니거나 중복되면 예외가 발생한다.")
    @ParameterizedTest(name = "입력: {0}")
    @ValueSource(strings = {"1,2,3,4,5", "1,2,3,4,5,6,7", "1,1,2,3,4,5"})
    void parseWinningNumbers_개수_중복_예외_테스트(String invalidInput) {
        assertThatThrownBy(() -> WinningLotto.parseWinningNumbers(invalidInput))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호가 로또 범위(1~45)를 벗어나면 예외가 발생한다.")
    @ParameterizedTest(name = "입력: {0}")
    @ValueSource(strings = {"0,1,2,3,4,5", "1,2,3,4,5,46"})
    void parseWinningNumbers_범위_예외_테스트(String invalidInput) {
        assertThatThrownBy(() -> WinningLotto.parseWinningNumbers(invalidInput))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호 문자열을 파싱하여 int를 정상적으로 반환한다.")
    @ParameterizedTest(name = "입력: {0}")
    @ValueSource(strings = {"7", " 45 ", "1"})
    void parseBonusNumber_파싱_성공_검증(String input) {
        assertThat(WinningLotto.parseBonusNumber("7")).isEqualTo(7);
    }

    @DisplayName("보너스 번호 파싱 시 문자가 포함되거나 범위를 벗어나면 예외가 발생한다.")
    @ParameterizedTest(name = "입력: '{0}'")
    @ValueSource(strings = {"7a", "0", "46", " "})
    void parseBonusNumber_형식_범위_예외_테스트(String invalidInput) {
        assertThatThrownBy(() -> WinningLotto.parseBonusNumber(invalidInput))
                .isInstanceOf(IllegalArgumentException.class);
    }
}