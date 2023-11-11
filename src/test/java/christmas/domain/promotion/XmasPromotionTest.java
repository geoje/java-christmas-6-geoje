package christmas.domain.promotion;

import christmas.domain.Order;
import christmas.domain.VisitingDay;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class XmasPromotionTest {
    @ParameterizedTest
    @DisplayName("주문 금액이 14만원에 여부 따라 증정 메뉴 테스트")
    @CsvSource(value = {"타파스-1,제로콜라-1/없음", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1/샴페인 1개"}, delimiter = '/')
    void giftMenu(String menus, String result) {
        assertThat(new XmasPromotion(new VisitingDay(1), Order.from(menus)).giftMenu().toString())
                .contains(result);
    }
}
