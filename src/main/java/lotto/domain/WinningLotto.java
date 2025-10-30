package lotto.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lotto.constant.ErrorMessage;
import lotto.constant.LottoConfig;

/**
 * 당첨 번호 도메인 클래스입니다.
 */
public class WinningLotto {

    private final List<Integer> numbers;
    private final int bonusNumber;

    private WinningLotto(
            List<Integer> numbers,
            int bonusNumber
    ) {
        this.numbers = Collections.unmodifiableList(numbers);
        this.bonusNumber = bonusNumber;
    }

    /**
     * 당첨 번호 객체를 생성합니다.
     *
     * @param winningNumbers 6자리 당첨 번호 배열
     * @param bonusNumber    보너스 당첨 번호
     * @return WinningLotto 객체
     */
    public static WinningLotto from(List<Integer> winningNumbers, int bonusNumber) {
        validateWinningNumbers(winningNumbers);
        validateRange(bonusNumber);
        validateDuplication(winningNumbers, bonusNumber);
        return new WinningLotto(winningNumbers, bonusNumber);
    }

    /**
     * 당첨 번호 6자리를 숫자 배열로 변환합니다.
     *
     * @param inputWinningNumbers 당첨 번호 입력값 문자열
     * @return 6자리 당첨 번호 배열
     */
    public static List<Integer> getWinningNumbers(String inputWinningNumbers) {
        try {
            List<Integer> numbers = parseNumbers(inputWinningNumbers);
            validateWinningNumbers(numbers);
            return numbers;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * 보너스 번호를 숫자로 변환합니다.
     *
     * @param inputBonusNumber 당첨 보너스 번호 문자열
     * @return 보너스 번호
     */
    public static int getBonusNumber(String inputBonusNumber) {
        try {
            int number = Integer.parseInt(inputBonusNumber.trim());
            validateRange(number);
            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.NUMBER_FORMAT_ERROR.getMessage());
        }
    }

    /**
     * 입력한 값을 숫자로 변환합니다.
     *
     * @param inputWinningNumbers 입력한 당첨 번호
     * @return 파싱되어 정수 배열로 변경된 당첨 번호
     * @throws IllegalArgumentException 당첨 번호에 숫자나 공백이 아닌 문자가 포함되었을 경우
     */
    private static List<Integer> parseNumbers(String inputWinningNumbers) {
        String[] winningNumbers = inputWinningNumbers.split(",");
        return Arrays.stream(winningNumbers)
                .map(String::trim)
                .map(s -> {
                    try {
                        return Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(ErrorMessage.NUMBER_FORMAT_ERROR.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * 당첨 번호를 검증합니다.
     *
     * @param numbers 입력받은 당첨 번호
     */
    private static void validateWinningNumbers(List<Integer> numbers) {
        validateCount(numbers);
        validateRange(numbers);
        validateDuplication(numbers);
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
