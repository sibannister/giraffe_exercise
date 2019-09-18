package cs.exercise;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public class OrderBoard extends Observable {
    private static final String DefaultBoardName = "Live Order Board";
    private final String name;
    private final Set<Order> orders;
    private final OrderBoardSummary orderBoardSummary;
    private Order currentOrder;

    public OrderBoard() {
        this(DefaultBoardName, new OrderBoardSummary(DefaultBoardName));
    }

    public OrderBoard(String name, OrderBoardSummary orderBoardSummary) {
        this.name = name;
        if(orderBoardSummary == null) {
            throw new IllegalArgumentException("'orderBoardSummary' cannot be null");
        }
        this.orderBoardSummary = orderBoardSummary;
        addObserver(orderBoardSummary);
        orders = new HashSet<>();
    }

    public boolean registerOrder(Order order) {
        if(order == null) {
            throw new IllegalArgumentException("Cannot register a null order");
        }
        boolean successful = orders.add(order);
        if(successful) {
            updateAndNotify(order);
        }
        return successful;
    }

    public String getName() {
        return name;
    }

    public Set<Order> getOrders() { return ImmutableSet.copyOf(orders); }

    public boolean cancelOrder(Order order) {
        if(order == null) {
            throw new IllegalArgumentException("Cannot register a null order");
        }
        boolean successful = orders.remove(order);
        if(successful) {
            updateAndNotify(order);
        }
        return successful; }

    private void updateAndNotify(Order order) {
        currentOrder = order;
        setChanged();
        notifyObservers();
    }

    public OrderBoardSummary getSummaryInformation() { return orderBoardSummary;  }

    public Order getCurrentOrder() {
        return currentOrder;
    }
}