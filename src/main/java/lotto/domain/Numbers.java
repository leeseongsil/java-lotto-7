package lotto.domain;

import java.util.List;
import java.util.Set;

public class Numbers {

    private static final int COUNT_OF_NUMBER = 6;
    private final Set<Number> values;

    public Numbers(List<Number> values) {
        validateSize(values);

        this.values = Set.copyOf(values);
    }

    public static Numbers from(List<Integer> ints) {
        List<Number> numbers = ints.stream()
                .map(Number::new)
                .toList();
        return new Numbers(numbers);
    }

    private void validateSize(List<Number> values) {
        if (values.size() != COUNT_OF_NUMBER) {
            throw new IllegalArgumentException("숫자는 총 %d개 이어야 합니다".formatted(COUNT_OF_NUMBER));
        }
    }
}
