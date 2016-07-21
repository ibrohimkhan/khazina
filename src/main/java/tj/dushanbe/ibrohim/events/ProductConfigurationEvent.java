package tj.dushanbe.ibrohim.events;

import tj.dushanbe.ibrohim.models.ProductCategory;
import tj.dushanbe.ibrohim.models.ProductColor;
import tj.dushanbe.ibrohim.models.ProductSize;

import java.util.EventObject;

/**
 * Created by ibrohim on 1/22/2015.
 */
public class ProductConfigurationEvent extends EventObject {

    private ProductCategory productCategory;
    private ProductColor productColor;
    private ProductSize productSize;

    private EventType type;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ProductConfigurationEvent(Object source) {
        super(source);
    }

    public ProductConfigurationEvent(Object source, ProductCategory productCategory, EventType type) {
        super(source);
        this.productCategory = productCategory;
        this.type = type;
    }

    public ProductConfigurationEvent(Object source, ProductColor productColor, EventType type) {
        super(source);
        this.productColor = productColor;
        this.type = type;
    }

    public ProductConfigurationEvent(Object source, ProductSize productSize, EventType type) {
        super(source);
        this.productSize = productSize;
        this.type = type;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public ProductColor getProductColor() {
        return productColor;
    }

    public ProductSize getProductSize() {
        return productSize;
    }

    public EventType getType() {
        return type;
    }
}