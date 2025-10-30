package lotto.view;

import java.util.Collections;
import java.util.List;
import lotto.Lotto;

public class ConsoleOutput {

    /**
     * 구입한 로또 수를 출력합니다.
     * @param count 구입한 로또 수
     */
    public void printLottoCount(int count) {
        System.out.println("\n" + count + "개를 구매했습니다.");
    }

    /**
     * 로또 번호를 출력합니다.
     *
     * @param lotto 로또 객체
     */
    public void printLottoNumber(Lotto lotto) {
        List<Integer> numbers = lotto.getNumbers();
        Collections.sort(numbers);
        System.out.println(numbers);
    }
}
