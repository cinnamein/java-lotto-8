package lotto;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import lotto.constant.LottoConfig;

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
     * 랜덤한 6자리의 숫자를 발급합니다.
     * @return 랜덤한 6자리 숫자
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
     * 로또 번호를 불러옵니다.
     * @return 로또 번호
     */
    public List<Integer> getNumbers() {
        return numbers;
    }
}
