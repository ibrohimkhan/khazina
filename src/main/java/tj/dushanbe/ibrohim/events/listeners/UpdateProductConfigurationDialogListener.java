package tj.dushanbe.ibrohim.events.listeners;

import tj.dushanbe.ibrohim.events.ProductConfigurationEvent;

import java.util.EventListener;

/**
 * Created by ibrohim on 1/24/2015.
 */
public interface UpdateProductConfigurationDialogListener extends EventListener {
    void updateProductConfiguration(ProductConfigurationEvent event);
}
