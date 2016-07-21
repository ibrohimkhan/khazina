package tj.dushanbe.ibrohim.views.ui.dialogs;

import tj.dushanbe.ibrohim.events.StockEvent;
import tj.dushanbe.ibrohim.events.listeners.UpdateStockEventDialogListener;
import tj.dushanbe.ibrohim.models.Product;
import tj.dushanbe.ibrohim.models.Stock;
import tj.dushanbe.ibrohim.services.util.DateUtil;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class UpdateIncomeStockDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel productNameLbl;
    private JComboBox productNameCmb;
    private JLabel dayLbl;
    private JSpinner daySpinner;
    private JLabel monthLbl;
    private JSpinner monthSpinner;
    private JLabel yearLbl;
    private JSpinner yearSpinner;

    private List<Product> products;
    private Stock stock;
    private DefaultComboBoxModel<Product> productModel;
    private UpdateStockEventDialogListener stockEventDialogListener;

    private String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    public UpdateIncomeStockDialog(JPanel parent, List<Product> products, Stock stock) {
        this.products = products;
        this.stock = stock;

        setContentPane(contentPane);
        setModal(true);

        setTitle("Редактировать приход");
        setSize(750, 250);
        setLocationRelativeTo(parent);

        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public DefaultComboBoxModel<Product> getProductModel() {
        return productModel;
    }

    public JSpinner getDaySpinner() {
        return daySpinner;
    }

    public JSpinner getMonthSpinner() {
        return monthSpinner;
    }

    public JSpinner getYearSpinner() {
        return yearSpinner;
    }

    public void setStockEventDialogListener(UpdateStockEventDialogListener stockEventDialogListener) {
        this.stockEventDialogListener = stockEventDialogListener;
    }

    private void onOK() {
        Product product = (Product) productNameCmb.getSelectedItem();
        Integer day = (Integer) daySpinner.getValue();
        Integer month = getMonthNumber((String) monthSpinner.getValue());
        Integer year = (Integer) yearSpinner.getValue();

        Date date = DateUtil.getDate(year, month, day);

        stock.setProduct(product);
        stock.setInStockDate(date.getTime());
        StockEvent event = new StockEvent(this, stock);

        if (stockEventDialogListener != null) {
            stockEventDialogListener.updateStockEvent(event);
        }

        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void createUIComponents() {
        productModel = new DefaultComboBoxModel<Product>();
        for (Product product : products) {
            productModel.addElement(product);
        }

        productNameCmb = new JComboBox(productModel);

        Integer[] days = getDays();
        SpinnerListModel daysModel = new SpinnerListModel(days);
        daySpinner = new JSpinner(daysModel);
        daySpinner.setValue(LocalDate.now().getDayOfMonth());

        SpinnerListModel monthsModel = new SpinnerListModel(months);
        monthSpinner = new JSpinner(monthsModel);
        monthSpinner.setValue(months[LocalDate.now().getMonth().getValue() - 1]);

        Integer[] years = getYears();
        SpinnerListModel yearsModel = new SpinnerListModel(years);
        yearSpinner = new JSpinner(yearsModel);
        yearSpinner.setValue(LocalDate.now().getYear());
    }

    private Integer[] getDays() {
        LocalDate date = LocalDate.now();
        int lastDay = date.getMonth().maxLength();

        Integer[] days = new Integer[lastDay];
        for (int i = 0; i < days.length; i++) {
            days[i] = i + 1;
        }

        return days;
    }

    private Integer[] getYears() {
        Integer[] years = new Integer[100];

        for (int i = 0; i < years.length; i++) {
            years[i] = 2014 + i;
        }
        return years;
    }

    private int getMonthNumber(String month) {
        int selectedMonth = 0;
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(month)) selectedMonth = i + 1;
        }

        return selectedMonth;
    }
}
