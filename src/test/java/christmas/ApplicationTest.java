package christmas;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest extends NsTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Test
    void 모든_타이틀_출력() {
        assertSimpleTest(() -> {
            run("3", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
            assertThat(output()).contains(
                    "<주문 메뉴>",
                    "<할인 전 총주문 금액>",
                    "<증정 메뉴>",
                    "<혜택 내역>",
                    "<총혜택 금액>",
                    "<할인 후 예상 결제 금액>",
                    "<12월 이벤트 배지>"
            );
        });
    }

    @Test
    void 혜택_내역_없음_출력() {
        assertSimpleTest(() -> {
            run("26", "타파스-1,제로콜라-1");
            assertThat(output()).contains("<혜택 내역>" + LINE_SEPARATOR + "없음");
        });
    }

    @Test
    void 날짜_예외_테스트() {
        assertSimpleTest(() -> {
            runException("a");
            assertThat(output()).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 주문_예외_테스트() {
        assertSimpleTest(() -> {
            runException("3", "제로콜라-a");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }

    @Test
    @DisplayName("혜택과 배지가 없는 주문")
    void case1() {
        assertSimpleTest(() -> {
            run("26", "타파스-1,제로콜라-1");
            assertThat(output()).contains(
                    "타파스 1개",
                    "제로콜라 1개",
                    "<할인 전 총주문 금액>\r\n8,500원",
                    "<증정 메뉴>\r\n없음",
                    "<혜택 내역>\r\n없음",
                    "<총혜택 금액>\r\n0원",
                    "<할인 후 예상 결제 금액>\r\n8,500원",
                    "<12월 이벤트 배지>\r\n없음"
            );
        });
    }

    @Test
    @DisplayName("혜택 3가지로 배지가 별인 주문")
    void case2() {
        assertSimpleTest(() -> {
            run("2", "티본스테이크-1,바비큐립-1,제로콜라-1");
            assertThat(output()).contains(
                    "티본스테이크 1개",
                    "바비큐립 1개",
                    "제로콜라 1개",
                    "<할인 전 총주문 금액>\r\n112,000원",
                    "<증정 메뉴>\r\n없음",
                    "크리스마스 디데이 할인: -1,100원",
                    "주말 할인: -4,046원",
                    "<총혜택 금액>\r\n-5,146원",
                    "<할인 후 예상 결제 금액>\r\n106,854원",
                    "<12월 이벤트 배지>\r\n별"
            );
        });
    }

    @Test
    @DisplayName("혜택 3가지로 배지가 트리인 주문")
    void case3() {
        assertSimpleTest(() -> {
            run("25", "아이스크림-5");
            assertThat(output()).contains(
                    "아이스크림 5개",
                    "<할인 전 총주문 금액>\r\n25,000원",
                    "<증정 메뉴>\r\n없음",
                    "크리스마스 디데이 할인: -3,400원",
                    "평일 할인: -10,115원",
                    "특별 할인: -1,000원",
                    "<총혜택 금액>\r\n-14,515원",
                    "<할인 후 예상 결제 금액>\r\n10,485원",
                    "<12월 이벤트 배지>\r\n트리"
            );
        });
    }

    @Test
    @DisplayName("혜택 4가지로 배지가 산타인 주문")
    void case4() {
        assertSimpleTest(() -> {
            run("3", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
            assertThat(output()).contains(
                    "티본스테이크 1개",
                    "바비큐립 1개",
                    "초코케이크 2개",
                    "제로콜라 1개",
                    "<할인 전 총주문 금액>\r\n142,000원",
                    "<증정 메뉴>\r\n샴페인 1개",
                    "크리스마스 디데이 할인: -1,200원",
                    "평일 할인: -4,046원",
                    "특별 할인: -1,000원",
                    "증정 이벤트: -25,000원",
                    "<총혜택 금액>\r\n-31,246원",
                    "<할인 후 예상 결제 금액>\r\n135,754원",
                    "<12월 이벤트 배지>\r\n산타"
            );
        });
    }

    @Test
    @DisplayName("이벤트에서 나올 수 있는 최대 84% 할인")
    void case5() {
        assertSimpleTest(() -> {
            run("25", "아이스크림-2");
            assertThat(output()).contains(
                    "아이스크림 2개",
                    "<할인 전 총주문 금액>\r\n10,000원",
                    "<증정 메뉴>\r\n없음",
                    "크리스마스 디데이 할인: -3,400원",
                    "평일 할인: -4,046원",
                    "특별 할인: -1,000원",
                    "<총혜택 금액>\r\n-8,446원",
                    "<할인 후 예상 결제 금액>\r\n1,554원",
                    "<12월 이벤트 배지>\r\n별"
            );
        });
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
