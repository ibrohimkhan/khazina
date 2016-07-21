package tj.dushanbe.ibrohim.views.ui;

import tj.dushanbe.ibrohim.events.StockEvent;
import tj.dushanbe.ibrohim.events.listeners.ChangeStockEventListener;
import tj.dushanbe.ibrohim.models.Stock;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

/**
 * Created by ibrohim on 2/17/2015.
 */
public class OutgoStockUI {
    private JPanel contentPanel;
    private JTable table;
    private JButton outStockBtn;
    private JTable outStockTable;
    private JPanel inStockPanel;
    private JPanel outStockPanel;
    private JButton cancelBtn;
    private JLabel productsInStock;
    private JLabel totalInStock;
    private JLabel productsOutStock;
    private JLabel totalOutStock;

    private List<Stock> inStock;
    private List<Stock> outStock;
    private StockTableModel stockTableModel;
    private StockTableModel outStockTableModel;

    private ChangeStockEventListener changeStockEventListener;

    public OutgoStockUI(final List<Stock> inStock, final List<Stock> outStock) {
        this.inStock = inStock;
        this.outStock = outStock;

        outStockBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int startRow = table.getSelectionModel().getMinSelectionIndex();
                int endRow = table.getSelectionModel().getMaxSelectionIndex();

                if (startRow < 0 && endRow < 0) {
                    JOptionPane.showMessageDialog(getContentPanel(),
                            "Выберите конкретную запись!", "Внимание", JOptionPane.OK_OPTION);
                } else {
                    Date date = new Date();

                    for (int i = endRow; i >= startRow; i--) {
                        Stock stock = stockTableModel.getStockList().get(i);
                        stock.setIsInStock(false);
                        stock.setOutStockDate(date.getTime());

                        StockEvent event = new StockEvent(this, stock);
                        if (changeStockEventListener != null) {
                            changeStockEventListener.changeStockAction(event);
                        }

                        stockTableModel.getStockList().remove(i);
                        stockTableModel.fireTableRowsDeleted(i, i);

                        outStockTableModel.getStockList().add(stock);
                        outStockTableModel.fireTableRowsInserted(outStockTableModel.getRowCount(), outStockTableModel.getRowCount());
                    }
                }
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int startRow = outStockTable.getSelectionModel().getMinSelectionIndex();
                int endRow = outStockTable.getSelectionModel().getMaxSelectionIndex();

                if (startRow < 0 && endRow < 0) {
                    JOptionPane.showMessageDialog(getContentPanel(),
                            "Выберите конкретную запись!", "Внимание", JOptionPane.OK_OPTION);
                } else {
                    for (int i = endRow; i >= startRow; i--) {
                        Stock stock = outStockTableModel.getStockList().get(i);
                        stock.setIsInStock(true);
                        stock.setOutStockDate(null);

                        StockEvent event = new StockEvent(this, stock);
                        if (changeStockEventListener != null) {
                            changeStockEventListener.changeStockAction(event);
                        }

                        outStockTableModel.getStockList().remove(i);
                        outStockTableModel.fireTableRowsDeleted(i, i);

                        stockTableModel.getStockList().add(stock);
                        stockTableModel.fireTableRowsInserted(stockTableModel.getRowCount(), stockTableModel.getRowCount());
                    }
                }
            }
        });
    }

    private void createUIComponents() {
        stockTableModel = new StockTableModel(inStock);
        table = new JTable(stockTableModel);

        outStockTableModel = new StockTableModel(outStock);
        outStockTable = new JTable(outStockTableModel);
    }

    public void setChangeStockEventListener(ChangeStockEventListener changeStockEventListener) {
        this.changeStockEventListener = changeStockEventListener;
    }

    public void setInStock(List<Stock> inStock) {
        this.inStock = inStock;
    }

    public void setOutStock(List<Stock> outStock) {
        this.outStock = outStock;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public JTable getTable() {
        return table;
    }

    public StockTableModel getStockTableModel() {
        return stockTableModel;
    }

    public JTable getOutStockTable() {
        return outStockTable;
    }

    public StockTableModel getOutStockTableModel() {
        return outStockTableModel;
    }

    public List<Stock> getInStock() {
        return inStock;
    }

    public List<Stock> getOutStock() {
        return outStock;
    }

    public JLabel getTotalOutStock() {
        return totalOutStock;
    }

    public JLabel getTotalInStock() {
        return totalInStock;
    }
}
