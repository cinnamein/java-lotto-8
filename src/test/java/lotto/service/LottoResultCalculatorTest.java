package lotto.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.LottoResult;
import lotto.domain.Prize;
import lotto.domain.PurchasePrice;
import lotto.domain.WinningLotto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoResultCalculatorTest {

    private LottoResultCalculator lottoResultCalculator;

    @BeforeEach
    void setUp() {
        lottoResultCalculator = new LottoResultCalculator();
    }

    @DisplayName("calculateResult가 등수별 당첨 횟수를 정확히 집계하는지 검증한다.")
    @Test
    void calculateResult_등수별_당첨횟수_집계_검증() {
        List<Lotto> lottos = getLottos();
        WinningLotto winningLotto = WinningLotto.from(List.of(1, 2, 3, 4, 5, 6), 7);
        PurchasePrice price = PurchasePrice.from("1000");
        LottoResult lottoResult = lottoResultCalculator.calculateResult(lottos, winningLotto, price);

        assertAll(
                () -> assertThat(lottoResult.getPrizeCount().get(Prize.FULL_BALL)).isEqualTo(1),
                () -> assertThat(lottoResult.getPrizeCount().get(Prize.FIVE_WITH_A_BONUS)).isEqualTo(1),
                () -> assertThat(lottoResult.getPrizeCount().get(Prize.FIVE_OF_A_KIND)).isEqualTo(1),
                () -> assertThat(lottoResult.getPrizeCount().get(Prize.FOUR_OF_A_KIND)).isEqualTo(1),
                () -> assertThat(lottoResult.getPrizeCount().get(Prize.THREE_OF_A_KIND)).isEqualTo(1),
                () -> assertThat(lottoResult.getPrizeCount().get(Prize.MISS)).isEqualTo(1)
        );
    }

    @DisplayName("calculateResult가 수익률 계산 결과를 정확히 반환하는지 검증한다.")
    @Test
    void calculateResult_수익률_반환_검증() {
        List<Lotto> lottos = List.of(new Lotto(List.of(1, 2, 3, 4, 5, 6)));
        WinningLotto winningLotto = WinningLotto.from(List.of(1, 2, 3, 4, 5, 6), 7);
        PurchasePrice price = PurchasePrice.from("1000");
        LottoResult lottoResult = lottoResultCalculator.calculateResult(lottos, winningLotto, price);
        assertThat(lottoResult.getRateOfReturn()).isEqualTo(200000000);
    }

    private static List<Lotto> getLottos() {
        Lotto fullBallLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto fiveBallWithBonusLotto = new Lotto(List.of(1, 2, 3, 4, 5, 7));
        Lotto fiveBallLotto = new Lotto(List.of(1, 2, 3, 4, 5, 45));
        Lotto fourBallLotto = new Lotto(List.of(1, 2, 3, 4, 44, 45));
        Lotto threeBallLotto = new Lotto(List.of(1, 2, 3, 43, 44, 45));
        Lotto missLotto = new Lotto(List.of(40, 41, 42, 43, 44, 45));
        List<Lotto> lottos = List.of(
                fullBallLotto,
                fiveBallWithBonusLotto,
                fiveBallLotto,
                fourBallLotto,
                threeBallLotto,
                missLotto
        );
        return lottos;
    }
}