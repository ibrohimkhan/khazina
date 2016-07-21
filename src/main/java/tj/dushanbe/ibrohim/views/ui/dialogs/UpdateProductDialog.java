package tj.dushanbe.ibrohim.views.ui.dialogs;

import tj.dushanbe.ibrohim.events.ProductEvent;
import tj.dushanbe.ibrohim.events.listeners.UpdateProductDialogListener;
import tj.dushanbe.ibrohim.models.Product;
import tj.dushanbe.ibrohim.models.ProductCategory;
import tj.dushanbe.ibrohim.models.ProductColor;
import tj.dushanbe.ibrohim.models.ProductSize;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class UpdateProductDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel productNameLbl;
    private JLabel productCategoryLbl;
    private JLabel productColorLbl;
    private JLabel productSizeLbl;
    private JTextField productNameTxtFld;
    private JComboBox productCategoryCmb;
    private JComboBox productColorCmb;
    private JComboBox productSizeCmb;
    private JLabel productDescriptionLbl;
    private JTextArea productDescriptionTxtArea;

    private DefaultComboBoxModel<ProductCategory> categoryModel;
    private DefaultComboBoxModel<ProductColor> colorModel;
    private DefaultComboBoxModel<ProductSize> sizeModel;

    private List<ProductCategory> categories;
    private List<ProductColor> colors;
    private List<ProductSize> sizes;

    private Product product;
    private UpdateProductDialogListener updateProductDialogListener;

    public UpdateProductDialog(List<ProductCategory> categories, List<ProductColor> colors, List<ProductSize> sizes, JPanel parent) {
        this.categories = categories;
        this.colors = colors;
        this.sizes = sizes;

        setContentPane(contentPane);
        setModal(true);

        setSize(650, 315);
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

    // Getters
    public JTextField getProductNameTxtFld() {
        return productNameTxtFld;
    }

    public JTextArea getProductDescriptionTxtArea() {
        return productDescriptionTxtArea;
    }

    public DefaultComboBoxModel<ProductCategory> getCategoryModel() {
        return categoryModel;
    }

    public DefaultComboBoxModel<ProductColor> getProductColorModel() {
        return colorModel;
    }

    public DefaultComboBoxModel<ProductSize> getSizeModel() {
        return sizeModel;
    }

    // Setters
    public void setProduct(Product product) {
        this.product = product;
    }

    public void setUpdateProductDialogListener(UpdateProductDialogListener updateProductDialogListener) {
        this.updateProductDialogListener = updateProductDialogListener;
    }

    // Internal Methods
    private void onOK() {
        boolean valid = product != null && !productNameTxtFld.getText().isEmpty() && !productDescriptionTxtArea.getText().isEmpty();

        if (valid) {
            product.setName(productNameTxtFld.getText());
            product.setDescription(productDescriptionTxtArea.getText());

            product.setProductCategory((ProductCategory) categoryModel.getSelectedItem());
            product.setProductColor((ProductColor) colorModel.getSelectedItem());
            product.setProductSize((ProductSize) sizeModel.getSelectedItem());

            ProductEvent productEvent = new ProductEvent(this, product);

            if (updateProductDialogListener != null) {
                updateProductDialogListener.updateProductEvent(productEvent);
            }
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        categoryModel = new DefaultComboBoxModel<ProductCategory>();
        for (ProductCategory category : categories) {
            categoryModel.addElement(category);
        }

        colorModel = new DefaultComboBoxModel<ProductColor>();
        for (ProductColor color : colors) {
            colorModel.addElement(color);
        }

        sizeModel = new DefaultComboBoxModel<ProductSize>();
        for (ProductSize size : sizes) {
            sizeModel.addElement(size);
        }

        productCategoryCmb = new JComboBox<ProductCategory>(categoryModel);
        productColorCmb = new JComboBox<ProductColor>(colorModel);
        productSizeCmb = new JComboBox<ProductSize>(sizeModel);
    }
}
