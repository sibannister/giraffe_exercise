package cs.exercise;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrderBoardSummaryTests {
    private OrderBoardSummary orderBoardSummary;
    private OrderBoard orderBoard;
    private final String boardName = "TEST";
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

    private Order firstBuyOrder = new Order("user1", firstQuantity, lowestPrice, OrderType.BUY);
    private Order secondBuyOrder = new Order("user2", secondQuantity, highestPrice, OrderType.BUY);
    private Order thirdBuyOrder = new Order("user3", thirdQuantity, midPrice, OrderType.BUY);
    private Order fourthBuyOrder = new Order("user4", fourthQuantity, lowestPrice, OrderType.BUY);


    @Before
    public void setUp() {
        orderBoardSummary = new OrderBoardSummary(boardName);
        orderBoard = new OrderBoard(boardName, orderBoardSummary);
    }

    @Test
    public void shouldReturnEmptySetsForEmptyBoard() {
        assertThat(orderBoardSummary.getOrderBoardName(), is(boardName));
        assertThat(orderBoardSummary.getSellOrders(), is(empty()));
        assertThat(orderBoardSummary.getBuyOrders(), is(empty()));
    }

    @Test
    public void shouldReturnSellOrderAfterSellRegistered() {
        orderBoard.registerOrder(firstSellOrder);
        Set<QuantityForPrice> sellOrders = orderBoardSummary.getSellOrders();
        Iterator<QuantityForPrice> sellIterator = sellOrders.iterator();
        assertQuantityForPrice(sellIterator.next(), lowestPrice, firstQuantity);
        assertThat(sellIterator.hasNext(), is(false));
        assertThat(orderBoardSummary.getBuyOrders(), is(empty()));
    }

    @Test
    public void shouldReturnCumulativeOrderAfterTwoSellsRegistered() {
        orderBoard.registerOrder(firstSellOrder);
        BigDecimal anotherQuantity = BigDecimal.valueOf(2);
        Order anotherOrder = new Order("anotherUser", anotherQuantity, lowestPrice, OrderType.SELL);
        orderBoard.registerOrder(anotherOrder);
        Set<QuantityForPrice> sellOrders = orderBoardSummary.getSellOrders();
        Iterator<QuantityForPrice> sellIterator = sellOrders.iterator();
        assertQuantityForPrice(sellIterator.next(), lowestPrice, firstQuantity.add(anotherQuantity));
        assertThat(sellIterator.hasNext(), is(false));
        assertThat(orderBoardSummary.getBuyOrders(), is(empty()));
    }

    @Test
    public void shouldOrderSellsInAscendingOrder() {
        orderBoard.registerOrder(firstSellOrder);
        orderBoard.registerOrder(secondSellOrder);
        orderBoard.registerOrder(thirdSellOrder);
        orderBoard.registerOrder(fourthSellOrder);

        Set<QuantityForPrice> sellOrders = orderBoardSummary.getSellOrders();
        Iterator<QuantityForPrice> sellIterator = sellOrders.iterator();
        assertQuantityForPrice(sellIterator.next(), lowestPrice, firstQuantity.add(fourthQuantity));
        assertQuantityForPrice(sellIterator.next(), midPrice, thirdQuantity);
        assertQuantityForPrice(sellIterator.next(), highestPrice, secondQuantity);
        assertThat(sellIterator.hasNext(), is(false));
        assertThat(orderBoardSummary.getBuyOrders(), is(empty()));
    }

    @Test
    public void shouldOrderBuysInDescendingOrder() {
        orderBoard.registerOrder(firstBuyOrder);
        orderBoard.registerOrder(secondBuyOrder);
        orderBoard.registerOrder(thirdBuyOrder);
        orderBoard.registerOrder(fourthBuyOrder);

        Set<QuantityForPrice> buyOrders = orderBoardSummary.getBuyOrders();
        Iterator<QuantityForPrice> buyIterator = buyOrders.iterator();
        assertQuantityForPrice(buyIterator.next(), highestPrice, secondQuantity);
        assertQuantityForPrice(buyIterator.next(), midPrice, thirdQuantity);
        assertQuantityForPrice(buyIterator.next(), lowestPrice, firstQuantity.add(fourthQuantity));
        assertThat(buyIterator.hasNext(), is(false));
        assertThat(orderBoardSummary.getSellOrders(), is(empty()));
    }

    @Test
    public void shouldHandleCancelOfBuyOrder() {
        orderBoard.registerOrder(firstBuyOrder);
        orderBoard.registerOrder(secondBuyOrder);
        orderBoard.registerOrder(thirdBuyOrder);
        orderBoard.registerOrder(fourthBuyOrder);

        orderBoard.cancelOrder(firstBuyOrder);

        Set<QuantityForPrice> buyOrders = orderBoardSummary.getBuyOrders();
        Iterator<QuantityForPrice> buyIterator = buyOrders.iterator();
        assertQuantityForPrice(buyIterator.next(), highestPrice, secondQuantity);
        assertQuantityForPrice(buyIterator.next(), midPrice, thirdQuantity);
        assertQuantityForPrice(buyIterator.next(), lowestPrice, fourthQuantity);
        assertThat(buyIterator.hasNext(), is(false));

        assertThat(orderBoardSummary.getSellOrders(), is(empty()));
    }

    private void assertQuantityForPrice(QuantityForPrice quantityForPrice, BigDecimal expectedPrice, BigDecimal expectedQuantity) {
        assertThat(quantityForPrice.getPrice(), is(expectedPrice));
        assertThat(quantityForPrice.getQuantity(), is(expectedQuantity));
    }
}
