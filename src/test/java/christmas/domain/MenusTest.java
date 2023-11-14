package christmas.domain;

import christmas.constant.Menu;
import christmas.constant.MenuType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MenusTest {
    @Test
    @DisplayName("총 메뉴 개수")
    void totalCount() {
        Menus menus = new Menus(Map.of(Menu.TAPAS, 1, Menu.ICE_CREAM, 2));
        assertThat(menus.totalCount()).isEqualTo(3);
    }

    @Test
    @DisplayName("총 메뉴 가격")
    void totalAmount() {
        Menus menus = new Menus(Map.of(Menu.TAPAS, 1, Menu.ICE_CREAM, 2));
        assertThat(menus.totalAmount()).isEqualTo(Menu.TAPAS.getPrice() + Menu.ICE_CREAM.getPrice() * 2);
    }

    @Test
    @DisplayName("특정 종류의 총 메뉴 개수")
    void countMenuType() {
        Menus menus = new Menus(Map.of(Menu.TAPAS, 1, Menu.ICE_CREAM, 2, Menu.CHOCOLATE_CAKE, 3));
        assertThat(menus.countMenuType(MenuType.DESSERT)).isEqualTo(5);
    }

    @Test
    @DisplayName("메뉴 목록 출력")
    void stringify() {
        Menus menus = new Menus(Map.of(Menu.TAPAS, 1, Menu.ICE_CREAM, 2, Menu.CHOCOLATE_CAKE, 3));
        assertThat(menus.toString()).contains("타파스 1개", "아이스크림 2개", "초코케이크 3개");
    }
}
