package tj.dushanbe.ibrohim.events.listeners;

import tj.dushanbe.ibrohim.events.StockEvent;

import java.util.EventListener;

/**
 * Created by ibrohim on 2/1/2015.
 */
public interface UpdateStockEventDialogListener extends EventListener {
    void updateStockEvent(StockEvent event);
}
