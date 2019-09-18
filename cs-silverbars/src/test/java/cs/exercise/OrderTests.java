package cs.exercise;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class OrderTests {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private final String userId = "userId";
    private final BigDecimal quantity = BigDecimal.ONE;
    private final BigDecimal price = BigDecimal.TEN;

    @Test
    public void shouldThrowExceptionForMissingUserId() {
        expectedException.expect(IllegalArgumentException.class);
        new Order(null, quantity, price, OrderType.SELL);
    }

    @Test
    public void shouldThrowExceptionForMissingQuantity() {
        expectedException.expect(IllegalArgumentException.class);
        new Order(userId, null, price, OrderType.SELL);
    }

    @Test
    public void shouldThrowExceptionForMissingPrice() {
        expectedException.expect(IllegalArgumentException.class);
        new Order(userId, quantity, null, OrderType.SELL);
    }

    @Test
    public void shouldThrowExceptionForMissingOrderType() {
        expectedException.expect(IllegalArgumentException.class);
        new Order(userId, quantity, price, null);
    }

    @Test
    public void shouldThrowExceptionForNegativeQuantity() {
        expectedException.expect(IllegalArgumentException.class);
        new Order(userId, BigDecimal.valueOf(-1), price, OrderType.SELL);
    }

    @Test
    public void shouldThrowExceptionForZeroQuantity() {
        expectedException.expect(IllegalArgumentException.class);
        new Order(userId, BigDecimal.ZERO, price, OrderType.SELL);
    }

    @Test
    public void shouldThrowExceptionForNegativePrice() {
        expectedException.expect(IllegalArgumentException.class);
        new Order(userId, quantity, BigDecimal.valueOf(-1), OrderType.SELL);
    }

    @Test
    public void shouldThrowExceptionForZeroPrice() {
        expectedException.expect(IllegalArgumentException.class);
        new Order(userId, quantity, BigDecimal.ZERO, OrderType.SELL);
    }

    @Test
    public void shouldCreateOrderWithOrderId() {
        Order order = new Order(userId, quantity, price, OrderType.SELL);
        assertThat(order.getOrderId(), is(notNullValue()));
        assertThat(order.getUserId(), is(userId));
        assertThat(order.getQuantity(), is(quantity));
        assertThat(order.getPrice(), is(price));
        assertThat(order.getOrderType(), is(OrderType.SELL));
    }
}
