package tj.dushanbe.ibrohim.views.ui;

import tj.dushanbe.ibrohim.events.AnalyticsEvent;
import tj.dushanbe.ibrohim.events.listeners.AnalyticsEventListener;
import tj.dushanbe.ibrohim.models.*;
import tj.dushanbe.ibrohim.services.util.DateUtil;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by ibrohim on 2/21/2015.
 */
public class AnalyticsUI {
    private JPanel contentPanel;
    private JComboBox productCmb;
    private JComboBox categoryCmb;
    private JComboBox colorCmb;
    private JComboBox sizeCmb;
    private JSpinner fromDay;
    private JSpinner fromMonth;
    private JSpinner fromYear;
    private JSpinner toDay;
    private JSpinner toMonth;
    private JSpinner toYear;
    private JButton searchBtn;
    private JButton excelBtn;
    private JTable table;
    private JLabel productLbl;
    private JLabel categoryLbl;
    private JLabel colorLbl;
    private JLabel sizeLbl;
    private JLabel fromLbl;
    private JLabel toLbl;
    private JPanel filters;
    private JCheckBox isInStockChb;
    private JLabel isInStockLbl;
    private JCheckBox isOutStockChb;
    private JLabel productAmountLbl;
    private JLabel total;

    private DefaultComboBoxModel<ProductCategory> categoryModel;
    private DefaultComboBoxModel<ProductColor> colorModel;
    private DefaultComboBoxModel<ProductSize> sizeModel;
    private DefaultComboBoxModel<Product> productModel;

    private SpinnerListModel daysModel;
    private SpinnerListModel toDaysModel;

    private List<Product> products;
    private List<ProductCategory> categories;
    private List<ProductColor> colors;
    private List<ProductSize> sizes;
    private List<Stock> resultQuery;

    private StockTableModel tableModel;
    private AnalyticsEventListener analyticsEventListener;

    public AnalyticsUI(List<Product> products, List<ProductCategory> categories, List<ProductColor> colors, List<ProductSize> sizes) {
        this.products = products;
        this.categories = categories;
        this.colors = colors;
        this.sizes = sizes;

        isInStockChb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getReportData();
            }
        });

        isOutStockChb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getReportData();
            }
        });

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getReportData();
            }
        });

        fromMonth.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String selectedMonth = (String) fromMonth.getValue();
                int month = 0;

                for (int i = 0; i < DateUtil.MONTHS.length; i++) {
                    if (selectedMonth.equals(DateUtil.MONTHS[i])) {
                        month = i + 1;
                        break;
                    }
                }

                Integer[] days = DateUtil.getDaysOfMonth(month, (Integer) fromYear.getValue());
                daysModel = new SpinnerListModel(days);
                fromDay.setModel(daysModel);
            }
        });

        toMonth.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String selectedMonth = (String) toMonth.getValue();
                int month = 0;

                for (int i = 0; i < DateUtil.MONTHS.length; i++) {
                    if (selectedMonth.equals(DateUtil.MONTHS[i])) {
                        month = i + 1;
                        break;
                    }
                }

                Integer[] days = DateUtil.getDaysOfMonth(month, (Integer) toYear.getValue());
                toDaysModel = new SpinnerListModel(days);
                toDay.setModel(toDaysModel);
            }
        });
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setAnalyticsEventListener(AnalyticsEventListener analyticsEventListener) {
        this.analyticsEventListener = analyticsEventListener;
    }

    public void setResultQuery(List<Stock> resultQuery) {
        this.resultQuery = resultQuery;
    }

    public StockTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTable() {
        return table;
    }

    public JLabel getTotal() {
        return total;
    }

    public DefaultComboBoxModel<ProductCategory> getCategoryModel() {
        return categoryModel;
    }

    public DefaultComboBoxModel<ProductColor> getColorModel() {
        return colorModel;
    }

    public DefaultComboBoxModel<ProductSize> getSizeModel() {
        return sizeModel;
    }

    public DefaultComboBoxModel<Product> getProductModel() {
        return productModel;
    }

    private void createUIComponents() {
        Product emptyProduct = new Product("Выберите товар", "");

        productModel = new DefaultComboBoxModel<Product>();
        productModel.addElement(emptyProduct);

        for (Product product : products) {
            productModel.addElement(product);
        }

        ProductCategory emptyCategory = new ProductCategory("Выберите категорию");

        categoryModel = new DefaultComboBoxModel<ProductCategory>();
        categoryModel.addElement(emptyCategory);

        for (ProductCategory category : categories) {
            categoryModel.addElement(category);
        }

        ProductColor emptyColor = new ProductColor("Выберите цвет");

        colorModel = new DefaultComboBoxModel<ProductColor>();
        colorModel.addElement(emptyColor);

        for (ProductColor color : colors) {
            colorModel.addElement(color);
        }

        ProductSize emptySize = new ProductSize();

        sizeModel = new DefaultComboBoxModel<ProductSize>();
        sizeModel.addElement(emptySize);

        for (ProductSize size : sizes) {
            sizeModel.addElement(size);
        }

        productCmb = new JComboBox(productModel);
        categoryCmb = new JComboBox(categoryModel);
        colorCmb = new JComboBox(colorModel);
        sizeCmb = new JComboBox(sizeModel);

        Integer[] days = DateUtil.getCurrentDays();
        daysModel = new SpinnerListModel(days);
        fromDay = new JSpinner(daysModel);
        fromDay.setValue(LocalDate.now().getDayOfMonth());

        SpinnerListModel monthsModel = new SpinnerListModel(DateUtil.MONTHS);
        fromMonth = new JSpinner(monthsModel);
        fromMonth.setValue(DateUtil.MONTHS[LocalDate.now().getMonth().getValue() - 1]);

        Integer[] years = DateUtil.getYears();
        SpinnerListModel yearsModel = new SpinnerListModel(years);
        fromYear = new JSpinner(yearsModel);
        fromYear.setValue(LocalDate.now().getYear());

        toDaysModel = new SpinnerListModel(days);
        toDay = new JSpinner(toDaysModel);
        toDay.setValue(LocalDate.now().getDayOfMonth());

        SpinnerListModel toMonthsModel = new SpinnerListModel(DateUtil.MONTHS);
        toMonth = new JSpinner(toMonthsModel);
        toMonth.setValue(DateUtil.MONTHS[LocalDate.now().getMonth().getValue() - 1]);

        SpinnerListModel toYearsModel = new SpinnerListModel(years);
        toYear = new JSpinner(toYearsModel);
        toYear.setValue(LocalDate.now().getYear());

        tableModel = new StockTableModel();
        table = new JTable(tableModel);
    }

    private void getReportData() {
        if (tableModel.getStockList() != null) {
            tableModel.getStockList().clear();
            tableModel.fireTableDataChanged();
        }

        int productIndex = productCmb.getSelectedIndex();
        int categoryIndex = categoryCmb.getSelectedIndex();
        int colorIndex = colorCmb.getSelectedIndex();
        int sizeIndex = sizeCmb.getSelectedIndex();

        if ((productIndex + categoryIndex + colorIndex + sizeIndex) == 0) {
            JOptionPane.showMessageDialog(getContentPanel(), "Незаданы параметры поска", "Параметры поска", JOptionPane.OK_OPTION);
            total.setText("" + tableModel.getRowCount());
            throw new RuntimeException("Незаданы параметры поска");
        }

        Integer fDay = (Integer) fromDay.getValue();
        Integer fMonth = DateUtil.getMonthNumber((String) fromMonth.getValue());
        Integer fYear = (Integer) fromYear.getValue();

        Integer tDay = (Integer) toDay.getValue();
        Integer tMonth = DateUtil.getMonthNumber((String) toMonth.getValue());
        Integer tYear = (Integer) toYear.getValue();

        Date fromDate = DateUtil.getDate(fYear, fMonth, fDay);
        Date toDate = DateUtil.getDate(tYear, tMonth, tDay);

        if (fromDate.getTime() > toDate.getTime()) {
            JOptionPane.showMessageDialog(getContentPanel(), "Неправильно выбраны даты поиска. Дата начало не может быть больше даты конца!", "Неверная Дата", JOptionPane.OK_OPTION);
            total.setText("" + tableModel.getRowCount());
            throw new RuntimeException("Неправильно выбраны даты поиска. Дата начало не может быть больше даты конца!");
        }

        Product product = null;
        if (productIndex > 0) {
            product = productModel.getElementAt(productIndex);
        }

        ProductCategory productCategory = null;
        if (categoryIndex > 0) {
            productCategory = categoryModel.getElementAt(categoryIndex);
        }

        ProductColor productColor = null;
        if (colorIndex > 0) {
            productColor = colorModel.getElementAt(colorIndex);
        }

        ProductSize productSize = null;
        if (sizeIndex > 0) {
            productSize = sizeModel.getElementAt(sizeIndex);
        }

        boolean selected = isInStockChb.isSelected();
        boolean isOutStock = isOutStockChb.isSelected();

        AnalyticsEvent event = new AnalyticsEvent(this, product, productCategory, productColor, productSize, fromDate, toDate, selected, isOutStock);

        if (analyticsEventListener != null) {
            analyticsEventListener.searchEventAction(event);
        }
    }
}
