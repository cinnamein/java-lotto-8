package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lotto.constant.ErrorMessage;
import lotto.constant.LottoConfig;

/**
 * 구매한 로또 도메인 클래스입니다.
 */
public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = Collections.unmodifiableList(numbers);
    }

    private void validate(List<Integer> numbers) {
        validateCount(numbers);
        validateRange(numbers);
        validateDuplication(numbers);
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

    /**
     * 입력된 번호가 6개인지 검증합니다.
     *
     * @param numbers 당첨 번호
     * @throws IllegalArgumentException 입력된 당첨 번호가 여섯 개가 아닌 경우
     */
    private static void validateCount(List<Integer> numbers) {
        if (numbers.size() != LottoConfig.NUMBER_COUNT.getValue()) {
            throw new IllegalArgumentException(ErrorMessage.NUMBER_COUNT_ERROR.getMessage());
        }
    }

    /**
     * 범위 내 번호인지 검증합니다.
     *
     * @param numbers 당첨 번호 6자리
     * @throws IllegalArgumentException 입력된 당첨 번호가 제한된 범위 밖의 숫자일 경우
     */
    private static void validateRange(List<Integer> numbers) {
        for (int number : numbers) {
            validateRange(number);
        }
    }

    /**
     * 범위 내 번호인지 검증합니다.
     *
     * @param number 당첨 번호
     */
    private static void validateRange(int number) {
        if (number < LottoConfig.MIN_NUMBER.getValue() || number > LottoConfig.MAX_NUMBER.getValue()) {
            throw new IllegalArgumentException(ErrorMessage.NUMBER_RANGE_ERROR.getMessage());
        }
    }

    /**
     * 번호가 중복되지 않았는지 검증합니다.
     *
     * @param numbers 당첨 번호
     * @throws IllegalArgumentException 입력된 당첨번호에 중복이 있을 경우
     */
    private static void validateDuplication(List<Integer> numbers) {
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);

        if (uniqueNumbers.size() != LottoConfig.NUMBER_COUNT.getValue()) {
            throw new IllegalArgumentException(ErrorMessage.NUMBER_DUPLICATE_ERROR.getMessage());
        }
    }

    /**
     * 당첨 번호와 보너스 번호가 중복되지 않았는지 검증합니다.
     *
     * @param winningNumbers 당첨 번호 6자리 배열
     * @param bonusNumber    보너스 번호
     * @throws IllegalArgumentException 입력된 당첨번호에 중복이 있을 경우
     */
    private static void validateDuplication(List<Integer> winningNumbers, int bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException(ErrorMessage.NUMBER_DUPLICATE_ERROR.getMessage());
        }
    }
}
