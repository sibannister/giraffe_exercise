package cs.exercise;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

public class Order {
    private final UUID orderId = UUID.randomUUID();
    private final String userId;
    private final BigDecimal quantityKilos;


    private final BigDecimal pricePerKilo;
    private final OrderType buyOrSell;

    public Order(String userId, BigDecimal quantityKilos, BigDecimal pricePerKilo, OrderType buyOrSell) {
        if(StringUtils.isBlank(userId)) {
            throw new IllegalArgumentException("userId cannot be blank");
        }
        if(quantityKilos == null || quantityKilos.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantityKilos cannot be null, zero or negative");
        }
        if(pricePerKilo == null  || pricePerKilo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("pricePerKilo cannot be null, zero or negative");
        }
        if(buyOrSell == null) {
            throw new IllegalArgumentException("buyOrSell cannot be null");
        }

        this.userId = userId;
        this.quantityKilos = quantityKilos;
        this.pricePerKilo = pricePerKilo;
        this.buyOrSell = buyOrSell;
    }

    /**
     * Important as it is very common for repeat orders from the same user at the same price and size.
     * @return
     */
    public UUID getOrderId() { return orderId; }

    public String getUserId() { return userId;  }

    public OrderType getOrderType() {
        return buyOrSell;
    }

    /**
     * Though the example prices of price were integer, I decided on BigDecimal given domain knowledge of GBP.
     * @return
     */
    public BigDecimal getPrice() {
        return pricePerKilo;
    }

    public BigDecimal getQuantity() {
        return quantityKilos;
    }
}
