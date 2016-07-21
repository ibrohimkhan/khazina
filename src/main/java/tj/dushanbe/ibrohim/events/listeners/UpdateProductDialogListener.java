package tj.dushanbe.ibrohim.events.listeners;

import tj.dushanbe.ibrohim.events.ProductEvent;

import java.util.EventListener;

/**
 * Created by ibrohim on 1/29/2015.
 */
public interface UpdateProductDialogListener extends EventListener {
    void updateProductEvent(ProductEvent event);
}
