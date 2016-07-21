package tj.dushanbe.ibrohim.events;

import tj.dushanbe.ibrohim.models.Stock;

import java.util.EventObject;

/**
 * Created by ibrohim on 2/1/2015.
 */
public class StockEvent extends EventObject {

    private Stock stock;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public StockEvent(Object source) {
        super(source);
    }

    public StockEvent(Object source, Stock stock) {
        super(source);
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }
}
