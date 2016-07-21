package tj.dushanbe.ibrohim.controllers;

import tj.dushanbe.ibrohim.events.AnalyticsEvent;
import tj.dushanbe.ibrohim.models.Stock;
import tj.dushanbe.ibrohim.services.AnalyticsService;

import java.util.List;

/**
 * Created by ibrohim on 2/22/2015.
 */
public class AnalyticsController {

    public List<Stock> getProducts(AnalyticsEvent event) {
        List<Stock> results = AnalyticsService.getProducts(event.getProduct(), event.getProductCategory(),
                event.getProductColor(), event.getProductSize(), event.getStartDate(), event.getEndDate(), event.isInStock(), event.isOutStock());
        return results;
    }
}
