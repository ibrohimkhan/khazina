package tj.dushanbe.ibrohim.events.listeners;

import tj.dushanbe.ibrohim.events.StockEvent;

import java.util.EventListener;

/**
 * Created by ibrohim on 2/18/2015.
 */
public interface ChangeStockEventListener extends EventListener {
    void changeStockAction(StockEvent event);
}
