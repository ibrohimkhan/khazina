package tj.dushanbe.ibrohim.events.listeners;

import tj.dushanbe.ibrohim.events.StockEvent;

import java.util.EventListener;

/**
 * Created by ibrohim on 2/1/2015.
 */
public interface StockEventListener extends EventListener {
    void addStockAction(StockEvent event);
    void updateStockAction(StockEvent event);
    void deleteStockAction(StockEvent event);
}
