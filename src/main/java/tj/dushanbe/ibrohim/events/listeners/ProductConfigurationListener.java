package tj.dushanbe.ibrohim.events.listeners;

import tj.dushanbe.ibrohim.events.ProductConfigurationEvent;

import java.util.EventListener;

/**
 * Created by ibrohim on 1/22/2015.
 */
public interface ProductConfigurationListener extends EventListener {
    void addProductConfiguration(ProductConfigurationEvent event);
    void updateProductConfiguration(ProductConfigurationEvent event);
    void deleteProductConfiguration(ProductConfigurationEvent event);
}
