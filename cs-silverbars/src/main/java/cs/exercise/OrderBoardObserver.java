package cs.exercise;

import java.util.Observer;

public abstract class OrderBoardObserver implements Observer {
    /**
     * Could have just kept the last OrderType as it is only used as a switch to indicate which set
     * needs to be updated.
     * @param orderBoard
     * @return
     */
    protected Order getOrderUpdate(OrderBoard orderBoard) { return orderBoard.getCurrentOrder();};
}
