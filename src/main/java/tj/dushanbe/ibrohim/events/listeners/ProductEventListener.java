package tj.dushanbe.ibrohim.events.listeners;

import tj.dushanbe.ibrohim.events.ProductEvent;

import java.util.EventListener;

/**
 * Created by ibrohim on 1/28/2015.
 */
public interface ProductEventListener extends EventListener {
    void addProductAction(ProductEvent event);
    void updateProductAction(ProductEvent event);
    void deleteProductAction(ProductEvent event);
}
