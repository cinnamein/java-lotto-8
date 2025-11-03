package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class LottoResultTest {

    private Map<Prize, Integer> prizeCount;
    private double rateOfReturn;

    @BeforeEach
    void setUp() {
        prizeCount = new HashMap<>();
        prizeCount.put(Prize.FULL_BALL, 1);
        prizeCount.put(Prize.FIVE_OF_A_KIND, 3);
        prizeCount.put(Prize.MISS, 9);
        rateOfReturn = 50.5;
    }

    @DisplayName("유효한 데이터로 LottoResult 객체가 정상적으로 생성되고 값을 반환한다.")
    @Test
    void create_객체_생성_및_값_반환_검증() {
        LottoResult result = LottoResult.create(prizeCount, rateOfReturn);
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.getPrizeCount()).isEqualTo(prizeCount),
                () -> assertThat(result.getRateOfReturn()).isEqualTo(rateOfReturn)
        );
    }

    @DisplayName("getPrizeCount()를 통해 반환된 Map은 외부에서 수정할 수 없다.")
    @Test
    void getPrizeCount_반환된_맵_불변성_검증() {
        LottoResult result = LottoResult.create(prizeCount, rateOfReturn);
        Map<Prize, Integer> returnedMap = result.getPrizeCount();
        assertThatThrownBy(() -> returnedMap.put(Prize.FIVE_WITH_A_BONUS, 1))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @DisplayName("Map 인자가 null일 경우 NullPointerException이 발생한다.")
    @Test
    void create_PrizeCount_Null_예외_테스트() {
        assertThatThrownBy(() -> LottoResult.create(null, rateOfReturn))
                .isInstanceOf(NullPointerException.class);
    }
}