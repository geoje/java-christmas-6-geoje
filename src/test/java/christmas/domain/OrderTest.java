package christmas.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static christmas.constant.ErrorMessage.ORDER_INVALID;
import static christmas.constant.ErrorMessage.ORDER_MENU_COUNT_EXCEED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class OrderTest {
    @Test
    @DisplayName("메뉴가 없는 주문 생성")
    void newWithoutMenu() {
        assertThatCode(() -> {
            new Order(new Menus(Map.of()));
        }).doesNotThrowAnyException();
    }

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
    @DisplayName("입력 중 없는 메뉴 제거 후 유연한 생성")
    @ValueSource(strings = {" , ,,해산물파스타-2,레드와인-1, ,,"})
    void fromFlexible(String menus) {
        assertThatCode(() -> Order.from(menus)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("할인 전 총주문 금액")
    @CsvSource(value = {"타파스-1,제로콜라-1/8500", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1/142000"}, delimiter = '/')
    void giftMenu(String menus, int amount) {
        assertThat(Order.from(menus).menus().totalAmount()).isEqualTo(amount);
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
    @DisplayName("음료만 시켰을 경우 예외 발생")
    @ValueSource(strings = {"제로콜라-1,레드와인-1"})
    void validateNotOnlyDrink(String menus) {
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

    @ParameterizedTest
    @DisplayName("주문 총 금액 출력")
    @ValueSource(strings = {"해산물파스타-2,레드와인-1"})
    void buildTotalAmountAsString(String menus) {
        assertThat(Order.from(menus).buildTotalAmountAsString())
                .contains("130,000원");
    }

    @Test
    @DisplayName("메뉴 목록 출력")
    void stringify() {
        Order order = Order.from("타파스-1,아이스크림-2,초코케이크-3");
        assertThat(order.toString()).contains("타파스 1개", "아이스크림 2개", "초코케이크 3개");
    }
}
