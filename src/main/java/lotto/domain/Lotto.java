package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import lotto.constant.LottoConfig;

/**
 * 구매한 로또 도메인 클래스입니다.
 */
public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
    }

    /**
     * 로또 설정에 맞는 범위에서 중복되지 않는 랜덤한 6자리의 로또 객체를 발급합니다.
     *
     * @return 랜덤한 6개의 숫자로 구성된 Lotto 객체
     */
    public static Lotto getRandomNumbers() {
        List<Integer> randomNumbers = Randoms.pickUniqueNumbersInRange(
                LottoConfig.MIN_NUMBER.getValue(),
                LottoConfig.MAX_NUMBER.getValue(),
                LottoConfig.NUMBER_COUNT.getValue()
        );
        return new Lotto(randomNumbers);
    }

    /**
     * 로또 번호 리스트를 반환합니다.
     *
     * @return 로또 번호 리스트
     */
    public List<Integer> getNumbers() {
        return numbers;
    }
}
