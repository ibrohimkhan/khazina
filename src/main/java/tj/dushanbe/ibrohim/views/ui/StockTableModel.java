package tj.dushanbe.ibrohim.views.ui;

import tj.dushanbe.ibrohim.models.Stock;
import tj.dushanbe.ibrohim.services.util.DateUtil;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by ibrohim on 2/1/2015.
 */
public class StockTableModel extends AbstractTableModel {

    private String[] tableHeader = {"Название", "Категория", "Цвет", "Размер", "В наличии", "Дата прихода", "Дата расхода"};
    private List<Stock> stockList;

    public StockTableModel() {}

    public StockTableModel(List<Stock> stockList) {
        this.stockList = stockList;
    }

    @Override
    public String getColumnName(int column) {
        return tableHeader[column];
    }

    @Override
    public int getRowCount() {
        return stockList != null ? stockList.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return tableHeader.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Stock stock = stockList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return stock.getProduct().getName();

            case 1:
                return stock.getProduct().getProductCategory().getName();

            case 2:
                return stock.getProduct().getProductColor().getColor();

            case 3:
                return stock.getProduct().getProductSize().toString();

            case 4:
                return stock.getIsInStock() == true ? "Да" : "Нет";

            case 5:
                return DateUtil.getDate(stock.getInStockDate());

            case 6:
                if (stock.getOutStockDate() != null) {
                    return DateUtil.getDate(stock.getOutStockDate());
                } else {
                    return null;
                }
        }

        return null;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }
}
