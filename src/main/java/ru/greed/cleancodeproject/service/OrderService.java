package ru.greed.cleancodeproject.service;

import org.apache.commons.collections4.CollectionUtils;
import ru.greed.cleancodeproject.config.DiscountRules;
import ru.greed.cleancodeproject.model.CustomerType;
import ru.greed.cleancodeproject.model.Item;

import java.util.List;
import java.util.Objects;

public class OrderService {

    /**
     * Считает итоговую стоимость корзины с учетом скидок.
     *
     * @param cart товары в корзине
     * @param clientType статус клиента (VIP, NEW, REGULAR)
     * @return сумма к оплате
     */
    public double calc(List<Item> cart, String clientType) {
        if (CollectionUtils.isEmpty(cart)) {
            return 0.0;
        }

        double total = getBaseTotal(cart);
        total = applyClientDiscount(total, clientType);
        total = applyBulkDiscount(total, cart);
        total = applyBigCheckDiscount(total);

        return total;
    }

    private double getBaseTotal(List<Item> cart) {
        return cart.stream()
                .filter(Objects::nonNull)
                .mapToDouble(item -> item.price() * item.quantity())
                .sum();
    }

    private double applyClientDiscount(double total, String clientType) {
        CustomerType type = CustomerType.fromString(clientType);
        return total * type.getDiscountRate();
    }

    private double applyBulkDiscount(double total, List<Item> cart) {
        int totalQuantity = cart.stream()
                .filter(Objects::nonNull)
                .mapToInt(Item::quantity)
                .sum();

        if (totalQuantity > DiscountRules.BULK_LIMIT) {
            return total * DiscountRules.BULK_DISCOUNT;
        }
        return total;
    }

    private double applyBigCheckDiscount(double total) {
        if (total > DiscountRules.BIG_CHECK_LIMIT) {
            return total - DiscountRules.BIG_CHECK_DISCOUNT;
        }
        return total;
    }
}
