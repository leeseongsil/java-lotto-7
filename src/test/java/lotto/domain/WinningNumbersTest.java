package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class WinningNumbersTest {

    @Test
    void validateTest_whenNumberIsOverlapped_throwException() {
        Numbers winningNumbers = Numbers.from(List.of(1, 2, 3, 4, 5, 6));
        Number bonusNumber = new Number(6);

        assertThatThrownBy(() -> new WinningNumbers(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("당첨 번호와 보너스 번호가 겹치면 안됩니다");
    }

    @Test
    void validateTest_whenNumberIsNotOverlapped() {
        Numbers winningNumbers = Numbers.from(List.of(1, 2, 3, 4, 5, 6));
        Number bonusNumber = new Number(7);

        assertThatCode(() -> new WinningNumbers(winningNumbers, bonusNumber))
                .doesNotThrowAnyException();
    }

    @Nested
    class FindResultTest {

        private final Numbers NUMBERS = Numbers.from(List.of(1, 2, 3, 4, 5, 6));
        private final Number BOUNS_NUMBER = new Number(7);
        private final WinningNumbers WINNING_NUMBERS = new WinningNumbers(NUMBERS, BOUNS_NUMBER);

        private final Lotto FIRST_LOTTO = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        private final Lotto SECOND_LOTTO = new Lotto(List.of(1, 2, 3, 4, 5, 7));
        private final Lotto FIFTH_LOTTO = new Lotto(List.of(4, 5, 6, 7, 8, 9));
        private final Lotto NOTHING_LOTTO = new Lotto(List.of(8, 9, 10, 11, 12, 13));

        @Test
        void whenFirstOne_SecondOne() {
            Lotties lotties = new Lotties(List.of(FIRST_LOTTO, SECOND_LOTTO));
            Map<WinningRank, Long> expected = Map.of(WinningRank.FIRST_RANK, 1L, WinningRank.SECOND_RANK, 1L);

            Map<WinningRank, Long> actual = WINNING_NUMBERS.findResult(lotties);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void whenFifthThree_NothingOne() {
            Lotties lotties = new Lotties(List.of(FIFTH_LOTTO, FIFTH_LOTTO, FIFTH_LOTTO, NOTHING_LOTTO));
            Map<WinningRank, Long> expected = Map.of(WinningRank.FIFTH_RANK, 3L, WinningRank.NOTHING, 1L);

            Map<WinningRank, Long> actual = WINNING_NUMBERS.findResult(lotties);

            assertThat(actual).isEqualTo(expected);
        }
    }
}