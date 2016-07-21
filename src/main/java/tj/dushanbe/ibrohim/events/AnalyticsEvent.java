package tj.dushanbe.ibrohim.events;

import tj.dushanbe.ibrohim.models.Product;
import tj.dushanbe.ibrohim.models.ProductCategory;
import tj.dushanbe.ibrohim.models.ProductColor;
import tj.dushanbe.ibrohim.models.ProductSize;

import java.util.Date;
import java.util.EventObject;

/**
 * Created by ibrohim on 2/22/2015.
 */
public class AnalyticsEvent extends EventObject {

    private Product product;
    private ProductCategory productCategory;
    private ProductColor productColor;
    private ProductSize productSize;
    private Date startDate;
    private Date endDate;
    private boolean isInStock;
    private boolean isOutStock;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public AnalyticsEvent(Object source) {
        super(source);
    }

    public AnalyticsEvent(Object source, Product product, ProductCategory productCategory, ProductColor productColor, ProductSize productSize, Date startDate, Date endDate, boolean isInStock, boolean isOutStock) {
        super(source);
        this.product = product;
        this.productCategory = productCategory;
        this.productColor = productColor;
        this.productSize = productSize;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isInStock = isInStock;
        this.isOutStock = isOutStock;
    }

    public Product getProduct() {
        return product;
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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public boolean isOutStock() {
        return isOutStock;
    }
}
