package cs.exercise;

import java.math.BigDecimal;

public class QuantityForPrice implements Comparable<QuantityForPrice> {
    private final BigDecimal price;
    private final BigDecimal quantity;

    public QuantityForPrice(BigDecimal price, BigDecimal quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    @Override
    public int compareTo(QuantityForPrice o) {
        return price.compareTo(o.price);
    }
}
