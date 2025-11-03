package lotto;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @DisplayName("기능 테스트: 8개 구매 시 수익률 62.5% 검증")
    @Test
    void 기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("8000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "8개를 구매했습니다.",
                            "[8, 21, 23, 41, 42, 43]",
                            "[3, 5, 11, 16, 32, 38]",
                            "[7, 11, 16, 35, 36, 44]",
                            "[1, 8, 11, 31, 41, 42]",
                            "[13, 14, 16, 38, 42, 45]",
                            "[7, 11, 30, 40, 42, 43]",
                            "[2, 13, 22, 32, 38, 45]",
                            "[1, 3, 5, 14, 22, 45]",
                            "3개 일치 (5,000원) - 1개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 0개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 62.5%입니다."
                    );
                },
                List.of(8, 21, 23, 41, 42, 43),
                List.of(3, 5, 11, 16, 32, 38),
                List.of(7, 11, 16, 35, 36, 44),
                List.of(1, 8, 11, 31, 41, 42),
                List.of(13, 14, 16, 38, 42, 45),
                List.of(7, 11, 30, 40, 42, 43),
                List.of(2, 13, 22, 32, 38, 45),
                List.of(1, 3, 5, 14, 22, 45)
        );
    }

    @DisplayName("기능 테스트: 모든 등수 당첨 시 수익률 검증")
    @Test
    void 모든등수_당첨_기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("6000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains("6개를 구매했습니다.");
                    assertThat(output()).contains(
                            "3개 일치 (5,000원) - 1개",
                            "4개 일치 (50,000원) - 1개",
                            "5개 일치 (1,500,000원) - 1개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 1개",
                            "6개 일치 (2,000,000,000원) - 1개",
                            "총 수익률은 33,859,250%입니다."
                    );
                },
                List.of(1, 2, 3, 4, 5, 6),
                List.of(1, 2, 3, 4, 5, 7),
                List.of(1, 2, 3, 4, 5, 8),
                List.of(1, 2, 3, 4, 9, 10),
                List.of(1, 2, 3, 9, 10, 11),
                List.of(10, 20, 30, 40, 41, 42)
        );
    }

    @DisplayName("기능 테스트: 모든 로또 탈락 시 수익률 0% 검증")
    @Test
    void 모든등수_탈락_기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("1000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains("1개를 구매했습니다.");
                    assertThat(output()).contains(
                            "3개 일치 (5,000원) - 0개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 0개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 0%입니다."
                    );
                },
                List.of(7, 8, 9, 10, 11, 12)
        );
    }

    @DisplayName("예외 테스트: 구매 금액 유효성 검사 (문자, 범위, 단위)")
    @ParameterizedTest(name = "입력: {0}")
    @ValueSource(strings = {"1000j", "100", "2147484000", "1500", " \n"})
    void 구매금액_유효성_검사_예외_테스트(String invalidInput) {
        assertSimpleTest(() -> {
            runException(invalidInput);
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("예외 테스트: 당첨 번호 유효성 검사 (형식, 범위, 개수, 중복)")
    @ParameterizedTest(name = "입력: {0}")
    @ValueSource(strings = {
            "1a,2,3,4,5,6",
            "1,2,3,4,5,46",
            "0,2,3,4,5,6",
            "1,2,3,4,5",
            "1,2,3,4,5,6,7",
            "1,1,3,4,5,6",
            "1,,3,4,5,6"
    })
    void 당첨번호_유효성_검사_예외_테스트(String invalidInput) {
        assertSimpleTest(() -> {
            runException("1000", invalidInput);
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("예외 테스트: 보너스 번호 유효성 검사 (형식, 범위)")
    @ParameterizedTest(name = "입력: '{0}'")
    @ValueSource(strings = {"7a", "46", "0", " "})
    void 보너스번호_유효성_검사_예외_테스트(String invalidInput) {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5,6", invalidInput);
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("예외 테스트: 당첨 번호와 보너스 번호가 중복되는 경우")
    @Test
    void 당첨_보너스_번호중복_예외_테스트() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5,6", "6");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}