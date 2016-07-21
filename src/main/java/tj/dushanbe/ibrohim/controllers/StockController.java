package tj.dushanbe.ibrohim.controllers;

import tj.dushanbe.ibrohim.events.StockEvent;
import tj.dushanbe.ibrohim.models.Stock;
import tj.dushanbe.ibrohim.services.StockServices;

import java.util.List;

/**
 * Created by ibrohim on 2/1/2015.
 */
public class StockController {

    private List<Stock> stockList;

    // List of Stocks
    public List<Stock> getStockList() {
        stockList = StockServices.getAllStocks();
        return stockList;
    }

    // List of products in Stock
    public List<Stock> getProductsFromStock() {
        List<Stock> products = StockServices.getProductsFromStock();
        return products;
    }

    // List of sold products
    public List<Stock> getSoldProductsFromStock() {
        List<Stock> products = StockServices.getSoldProductsFromStock();
        return products;
    }

    // Stock Actions
    public Long addStock(StockEvent event) {
        Long id = StockServices.addStock(event.getStock());
        return id;
    }

    public void deleteStock(StockEvent event) {
        StockServices.deleteStock(event.getStock());
    }

    public void updateStock(StockEvent event) {
        StockServices.updateStock(event.getStock());
    }
}
