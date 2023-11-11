package christmas.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static christmas.constant.ErrorMessage.ORDER_INVALID;
import static christmas.constant.ErrorMessage.ORDER_MENU_COUNT_EXCEED;
import static org.assertj.core.api.Assertions.assertThatCode;

public class OrderTest {
    @ParameterizedTest
    @DisplayName("from 함수를 통한 생성")
    @ValueSource(strings = {"해산물파스타-2,레드와인-1,초코케이크-1"})
    void fromInstance(String menus) {
        assertThatCode(() -> {
            Order order = Order.from(menus);
            System.out.println(order);
        }).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("없는 메뉴 제거 후 어느정도 유연한 생성")
    @ValueSource(strings = {" , ,,해산물파스타-2,레드와인-1, ,,"})
    void fromFlexible(String menus) {
        assertThatCode(() -> Order.from(menus)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("메뉴 이름과 개수 사이에 분리자가 없을 경우 에외 발생")
    @ValueSource(strings = {"해산물파스타-2,레드와인1", "해산물파스타,레드와인"})
    void validateNameCountDelimiter(String menus) {
        assertThatCode(() -> Order.from(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ORDER_INVALID.toString());
    }

    @ParameterizedTest
    @DisplayName("메뉴 개수가 없거나 숫자가 아닐 경우 예외 발생")
    @ValueSource(strings = {"해산물파스타-,레드와인-1", "해산물파스타-둘,레드와인-1"})
    void validateNumeric(String menus) {
        assertThatCode(() -> Order.from(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ORDER_INVALID.toString());
    }

    @ParameterizedTest
    @DisplayName("메뉴 개수가 0 일 경우 예외 발생")
    @ValueSource(strings = {"해산물파스타-0,레드와인-1"})
    void validateCount(String menus) {
        assertThatCode(() -> Order.from(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ORDER_INVALID.toString());
    }

    @ParameterizedTest
    @DisplayName("메뉴가 존재하지 않을 경우 예외 발생")
    @ValueSource(strings = {"해산물파스타-2,오일파스타-1"})
    void validateExistMenu(String menus) {
        assertThatCode(() -> Order.from(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ORDER_INVALID.toString());
    }

    @ParameterizedTest
    @DisplayName("메뉴가 중복될 경우 예외 발생")
    @ValueSource(strings = {"해산물파스타-2,해산물파스타-1"})
    void validateNotDuplicated(String menus) {
        assertThatCode(() -> Order.from(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ORDER_INVALID.toString());
    }

    @ParameterizedTest
    @DisplayName("메뉴를 최대 수량 이상 시켰을 경우 예외 발생")
    @ValueSource(strings = {"해산물파스타-11,레드와인-10"})
    void validateMaxMenuCount(String menus) {
        assertThatCode(() -> Order.from(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ORDER_MENU_COUNT_EXCEED.toString());
    }
}
