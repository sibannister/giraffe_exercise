package cs.exercise;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class OrderBoardSummary extends OrderBoardObserver {
    private final String name;
    private Set<QuantityForPrice> sellOrders = new HashSet<>();
    private Set<QuantityForPrice> buyOrders = new HashSet<>();

    public OrderBoardSummary(String name) {
        this.name = name;
    }

    public String getOrderBoardName() { return name; }

    public Set<QuantityForPrice> getSellOrders() {
        return sellOrders;
    }

    public Set<QuantityForPrice> getBuyOrders() {
        return buyOrders;
    }

    private Set<QuantityForPrice> groupByOrders(OrderBoard orderBoard, OrderType orderType) {
        Set<Order> filteredOrders = filterByOrderType(orderBoard, orderType);
        Map<BigDecimal, List<Order>> groupedByPrice = groupByPrice(filteredOrders);
        return quantityForPrice(groupedByPrice);
    }

    private Set<QuantityForPrice> quantityForPrice(Map<BigDecimal, List<Order>> ordersByPrice) {
        return ordersByPrice.entrySet().stream()
                .map(x -> new QuantityForPrice(x.getKey(), x.getValue().stream()
                        .map(o -> o.getQuantity())
                        .reduce(BigDecimal.ZERO, BigDecimal::add)))
                .collect(Collectors.toSet());
    }

    private Map<BigDecimal, List<Order>> groupByPrice(Set<Order> orders) {
        return orders.stream().collect(Collectors.groupingBy(Order::getPrice));
    }

    private Set<Order> filterByOrderType(OrderBoard orderBoard, OrderType orderType) {
        return orderBoard.getOrders().stream().filter(order -> order.getOrderType().equals(orderType)).collect(Collectors.toSet());
    }

    /**
     * Could have observed each update to build and manage the two sets independently, I would then need to manage consistency between
     * the orders on the order board and the set modified in here. The consequence is I have had to expose the OrderBoard set of orders,
     * however I have ensured it is immutable....and the overhead of rebuilding the sell or the buy set for each change.
     * Upside of this implementation is it adheres to a single source of truth and it's simple.
     * @param observable
     * @param arg
     */
    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof OrderBoard) {
            OrderBoard orderBoard = (OrderBoard) observable;
            Order order = getOrderUpdate(orderBoard);
            if (order.getOrderType().equals(OrderType.SELL)) {
                sellOrders = new TreeSet<>(groupByOrders(orderBoard, OrderType.SELL));
            } else {
                buyOrders = new TreeSet<>(groupByOrders(orderBoard, OrderType.BUY)).descendingSet();
            }
        }
    }
}
