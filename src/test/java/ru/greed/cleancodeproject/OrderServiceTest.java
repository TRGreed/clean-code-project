package ru.greed.cleancodeproject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.greed.cleancodeproject.model.Item;
import ru.greed.cleancodeproject.service.OrderService;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Расчет корзины")
class OrderServiceTest {

    private final OrderService orderService = new OrderService();

    @Test
    @DisplayName("Обычный тариф")
    void regular() {
        List<Item> cart = Collections.singletonList(new Item("Книга", 100.0, 2));
        double total = orderService.calc(cart, "REGULAR");
        assertEquals(200.0, total, 0.001);
    }

    @Test
    @DisplayName("Скидка VIP")
    void vip() {
        List<Item> cart = Collections.singletonList(new Item("Книга", 100.0, 2));
        double total = orderService.calc(cart, "VIP");
        assertEquals(180.0, total, 0.001);
    }

    @Test
    @DisplayName("Скидка NEW")
    void newCustomer() {
        List<Item> cart = Collections.singletonList(new Item("Книга", 100.0, 2));
        double total = orderService.calc(cart, "NEW");
        assertEquals(190.0, total, 0.001);
    }

    @Test
    @DisplayName("Чек выше 1000")
    void expensive() {
        List<Item> cart = Collections.singletonList(new Item("Ноутбук", 1200.0, 1));
        double total = orderService.calc(cart, "REGULAR");
        assertEquals(1150.0, total, 0.001);
    }

    @Test
    @DisplayName("Скидка за опт")
    void bulk() {
        // 11 товаров по 10.0 = 110.0. Ожидаем скидку 1%: 110.0 * 0.99 = 108.9
        List<Item> cart = Collections.singletonList(new Item("Ручка", 10.0, 11));
        double total = orderService.calc(cart, "REGULAR");
        assertEquals(108.9, total, 0.001);
    }
}
