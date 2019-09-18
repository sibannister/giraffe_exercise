package cs.exercise;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

public class OrderBoard {
    private static final String DefaultBoardName = "Live Order Board";
    private final String name;
    private final Set<Order> orders;

    public OrderBoard() {
        this(DefaultBoardName);
    }

    public OrderBoard(String name) {
        this.name = name;
        orders = new HashSet<>();
    }

    public boolean registerOrder(Order order) {
        if(order == null) {
            throw new IllegalArgumentException("Cannot register a null order");
        }
        return orders.add(order);
    }

    public String getName() {
        return name;
    }

    public Set<Order> getOrders() { return ImmutableSet.copyOf(orders); }

    public boolean cancelOrder(Order order) {
        if(order == null) {
            throw new IllegalArgumentException("Cannot register a null order");
        }
        return orders.remove(order);
    }
}