package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {

    @DisplayName("유효한 로또 번호로 Lotto 객체가 정상적으로 생성되고 번호를 반환한다.")
    @Test
    void new_유효한_번호로_객체_생성_및_반환_검증() {
        List<Integer> validNumbers = List.of(1, 2, 3, 4, 5, 6);
        Lotto lotto = new Lotto(validNumbers);
        assertThat(lotto.getNumbers()).containsExactlyInAnyOrderElementsOf(validNumbers);
    }

    @DisplayName("getNumbers()로 반환된 리스트는 외부에서 수정할 수 없다.")
    @Test
    void getNumbers_반환된_리스트_불변성_검증() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        assertThatThrownBy(() -> lotto.getNumbers().add(99))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @DisplayName("로또 번호의 개수가 6개를 초과하면 IllegalArgumentException이 발생한다.")
    @Test
    void new_번호_개수_초과_예외_테스트() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 IllegalArgumentException이 발생한다.")
    @Test
    void new_번호_중복_예외_테스트() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 범위(1 미만)이면 IllegalArgumentException이 발생한다.")
    @Test
    void new_번호_범위_미만_예외_테스트() {
        assertThatThrownBy(() -> new Lotto(List.of(0, 1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 범위(45 초과)이면 IllegalArgumentException이 발생한다.")
    @Test
    void new_번호_범위_초과_예외_테스트() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 46)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}