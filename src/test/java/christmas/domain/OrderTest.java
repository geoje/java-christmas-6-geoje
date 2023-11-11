package christmas.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;

public class OrderTest {
    @ParameterizedTest
    @DisplayName("from 함수를 통한 생성")
    @ValueSource(strings = {"해산물파스타-2,레드와인-1,초코케이크-1"})
    void fromInstance(String menu) {
        assertThatCode(() -> {
            Order order = Order.from(menu);
            System.out.println(order);
        }).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("어느정도 유연한 생성")
    @ValueSource(strings = {",,,해산물파스타-2,레드와인-1,,,"})
    void fromFlexible(String menu) {
        assertThatCode(() -> Order.from(menu)).doesNotThrowAnyException();
    }

    // TODO: 예외를 적용 하여 validate 체크
}
