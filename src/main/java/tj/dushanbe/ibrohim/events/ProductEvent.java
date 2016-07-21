package tj.dushanbe.ibrohim.events;

import tj.dushanbe.ibrohim.models.Product;

import java.util.EventObject;

/**
 * Created by ibrohim on 1/28/2015.
 */
public class ProductEvent extends EventObject {

    private Product product;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ProductEvent(Object source) {
        super(source);
    }

    public ProductEvent(Object source, Product product) {
        super(source);
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
