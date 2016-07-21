package tj.dushanbe.ibrohim.views.ui;

import tj.dushanbe.ibrohim.events.StockEvent;
import tj.dushanbe.ibrohim.events.listeners.StockEventListener;
import tj.dushanbe.ibrohim.events.listeners.UpdateStockEventDialogListener;
import tj.dushanbe.ibrohim.models.Product;
import tj.dushanbe.ibrohim.models.Stock;
import tj.dushanbe.ibrohim.services.util.DateUtil;
import tj.dushanbe.ibrohim.views.ui.dialogs.UpdateIncomeStockDialog;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by ibrohim on 1/31/2015.
 */
public class IncomeStockUI {
    private JPanel contentPanel;
    private JComboBox productNameCmb;
    private JSpinner daySpinner;
    private JSpinner monthSpinner;
    private JSpinner yearSpinner;
    private JButton addBtn;
    private JButton updateBtn;
    private JButton deleteBtn;
    private JTable table;
    private JLabel productNameLbl;
    private JLabel dayLbl;
    private JLabel monthLbl;
    private JLabel yearLbl;
    private JSpinner amountSpr;
    private JLabel amountLbl;
    private JLabel productAmount;
    private JLabel total;

    private SpinnerListModel daysModel;
    private SpinnerListModel monthsModel;

    private List<Product> products;
    private List<Stock> stocks;
    private StockEventListener stockEventListener;
    private StockTableModel stockTableModel;
    private DefaultComboBoxModel<Product> productModel;

    private UpdateIncomeStockDialog incomeStockDialog;

    public IncomeStockUI(final List<Product> products, List<Stock> stocks) {
        this.products = products;
        this.stocks = stocks;

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                table.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            }
        });

        monthSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String selectedMonth = (String) monthSpinner.getValue();
                int month = 0;

                for (int i = 0; i < DateUtil.MONTHS.length; i++) {
                    if (selectedMonth.equals(DateUtil.MONTHS[i])) {
                        month = i + 1;
                        break;
                    }
                }

                Integer[] days = DateUtil.getDaysOfMonth(month, (Integer) yearSpinner.getValue());
                daysModel = new SpinnerListModel(days);
                daySpinner.setModel(daysModel);
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product product = (Product) productNameCmb.getSelectedItem();
                Integer day = (Integer) daySpinner.getValue();
                Integer month = DateUtil.getMonthNumber((String) monthSpinner.getValue());
                Integer year = (Integer) yearSpinner.getValue();
                int amount = (Integer) amountSpr.getValue();

                Date date = DateUtil.getDate(year, month, day);

                if (product != null) {
                    Stock stock = new Stock(product, date.getTime());
                    StockEvent event = new StockEvent(this, stock);

                    if (stockEventListener != null) {
                        for (int i = 0; i < amount; i++) {
                            stockEventListener.addStockAction(event);
                        }
                    }
                }
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                int row = table.getSelectedRow();

                if (row > -1) {
                    Stock stock = stockTableModel.getStockList().get(row);
                    incomeStockDialog = new UpdateIncomeStockDialog(getContentPanel(), products, stock);
                    incomeStockDialog.setStockEventDialogListener(new UpdateStockEventDialogListener() {
                        @Override
                        public void updateStockEvent(StockEvent event) {
                            stockEventListener.updateStockAction(event);
                        }
                    });

                    incomeStockDialog.getProductModel().setSelectedItem(stock.getProduct());
                    LocalDate localDate = DateUtil.getLocalDate(new Date(stock.getInStockDate()));

                    incomeStockDialog.getDaySpinner().setValue(localDate.getDayOfMonth());
                    incomeStockDialog.getMonthSpinner().setValue(DateUtil.MONTHS[localDate.getMonthValue() - 1]);
                    incomeStockDialog.getYearSpinner().setValue(localDate.getYear());

                    incomeStockDialog.setVisible(true);
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fromRow = table.getSelectionModel().getMinSelectionIndex();
                int toRow = table.getSelectionModel().getMaxSelectionIndex();

                if (fromRow < 0 && toRow < 0) {
                    JOptionPane.showMessageDialog(getContentPanel(),
                            "Выберите конкретную запись для удаления", "Ошибка", JOptionPane.OK_OPTION);
                } else {
                    int action = JOptionPane.showConfirmDialog(getContentPanel(), "Удалить данную запись о приходе товара?", "Удалить запись", JOptionPane.OK_CANCEL_OPTION);
                    if (action == JOptionPane.OK_OPTION) {
                        for (int row = toRow; row >= fromRow; row--) {
                            Stock stock = stockTableModel.getStockList().get(row);
                            StockEvent event = new StockEvent(this, stock);

                            if (stockEventListener != null) {
                                stockEventListener.deleteStockAction(event);
                            }
                        }
                    }
                }
            }
        });
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public StockTableModel getStockTableModel() {
        return stockTableModel;
    }

    public DefaultComboBoxModel<Product> getProductModel() {
        return productModel;
    }

    public JTable getTable() {
        return table;
    }

    public JLabel getTotal() {
        return total;
    }

    public void setStockEventListener(StockEventListener stockEventListener) {
        this.stockEventListener = stockEventListener;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    private void createUIComponents() {
        Integer[] days = DateUtil.getCurrentDays();
        daysModel = new SpinnerListModel(days);
        daySpinner = new JSpinner(daysModel);
        daySpinner.setValue(LocalDate.now().getDayOfMonth());

        monthsModel = new SpinnerListModel(DateUtil.MONTHS);
        monthSpinner = new JSpinner(monthsModel);
        monthSpinner.setValue(DateUtil.MONTHS[LocalDate.now().getMonth().getValue() - 1]);

        Integer[] years = DateUtil.getYears();
        SpinnerListModel yearsModel = new SpinnerListModel(years);
        yearSpinner = new JSpinner(yearsModel);
        yearSpinner.setValue(LocalDate.now().getYear());

        SpinnerNumberModel amount = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        amountSpr = new JSpinner(amount);

        productModel = new DefaultComboBoxModel<Product>();
        for (Product product : products) {
            productModel.addElement(product);
        }

        productNameCmb = new JComboBox(productModel);

        stockTableModel = new StockTableModel(stocks);
        table = new JTable(stockTableModel);
    }
}
