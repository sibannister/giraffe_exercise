package cs.exercise;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class OrderBoardTests {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private OrderBoard orderBoard;
    private BigDecimal lowestPrice = BigDecimal.valueOf(306);
    private BigDecimal midPrice = BigDecimal.valueOf(307);
    private BigDecimal highestPrice = BigDecimal.valueOf(310);
    private BigDecimal firstQuantity = BigDecimal.valueOf(3.5);
    private BigDecimal secondQuantity = BigDecimal.valueOf(1.2);
    private BigDecimal thirdQuantity = BigDecimal.valueOf(1.5);
    private BigDecimal fourthQuantity = BigDecimal.valueOf(2.0);
    private Order firstSellOrder = new Order("user1", firstQuantity, lowestPrice, OrderType.SELL);
    private Order secondSellOrder = new Order("user2", secondQuantity, highestPrice, OrderType.SELL);
    private Order thirdSellOrder = new Order("user3", thirdQuantity, midPrice, OrderType.SELL);
    private Order fourthSellOrder = new Order("user4", fourthQuantity, lowestPrice, OrderType.SELL);

    @Before
    public void setUp() {
        orderBoard = new OrderBoard();
    }

    @Test
    public void shouldRegisterOrder() {
        assertThat(orderBoard.registerOrder(firstSellOrder), is(true));
        assertThat(orderBoard.getOrders(), contains(firstSellOrder));
    }

    @Test
    public void shouldNotRegisterDuplicateOrder() {
        assertThat(orderBoard.registerOrder(firstSellOrder), is(true));
        assertThat(orderBoard.getOrders(), contains(firstSellOrder));
        assertThat(orderBoard.registerOrder(firstSellOrder), is(false));
        assertThat(orderBoard.getOrders(), contains(firstSellOrder));
    }

    @Test
    public void shouldThrowExceptionRegisteringNull() {
        expectedException.expect(IllegalArgumentException.class);
        orderBoard.registerOrder(null);
    }

    @Test
    public void shouldThrowExceptionCancellingNull() {
        expectedException.expect(IllegalArgumentException.class);
        orderBoard.cancelOrder(null);
    }

    @Test
    public void shouldCancelOrder() {
        assertThat(orderBoard.registerOrder(firstSellOrder), is(true));
        assertThat(orderBoard.getOrders(), contains(firstSellOrder));
        assertThat(orderBoard.cancelOrder(firstSellOrder), is(true));
        assertThat(orderBoard.getOrders(), not(contains(firstSellOrder)));
    }

    @Test
    public void shouldNotCancelUnknownOrder() {
        assertThat(orderBoard.registerOrder(firstSellOrder), is(true));
        assertThat(orderBoard.getOrders(), contains(firstSellOrder));
        assertThat(orderBoard.cancelOrder(secondSellOrder), is(false));
        assertThat(orderBoard.getOrders(), contains(firstSellOrder));
    }

    @Test
    public void shouldGetOrdersRegistered() {
        orderBoard.registerOrder(firstSellOrder);
        orderBoard.registerOrder(secondSellOrder);
        orderBoard.registerOrder(thirdSellOrder);
        orderBoard.registerOrder(fourthSellOrder);

        assertThat(orderBoard.getOrders(), containsInAnyOrder(firstSellOrder, secondSellOrder, thirdSellOrder, fourthSellOrder));
    }
}
