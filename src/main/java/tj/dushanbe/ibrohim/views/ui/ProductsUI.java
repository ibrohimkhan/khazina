package tj.dushanbe.ibrohim.views.ui;

import tj.dushanbe.ibrohim.events.ProductEvent;
import tj.dushanbe.ibrohim.events.listeners.ProductEventListener;
import tj.dushanbe.ibrohim.events.listeners.UpdateProductDialogListener;
import tj.dushanbe.ibrohim.models.Product;
import tj.dushanbe.ibrohim.models.ProductCategory;
import tj.dushanbe.ibrohim.models.ProductColor;
import tj.dushanbe.ibrohim.models.ProductSize;
import tj.dushanbe.ibrohim.views.ui.dialogs.UpdateProductDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by ibrohim on 1/27/2015.
 */
public class ProductsUI {
    private JPanel contentPanel;
    private JPanel productElements;
    private JPanel productTable;
    private JTable productsTbl;
    private JTextField productNameTxtFld;
    private JComboBox<ProductCategory> productCategoryCmb;
    private JComboBox<ProductColor> productColorCmb;
    private JComboBox<ProductSize> productSizeCmb;
    private JTextArea productDescriptionTxtArea;
    private JButton addProductBtn;
    private JButton updateProductBtn;
    private JButton deleteProductBtn;
    private JLabel productNameLbl;
    private JLabel productCategoryLbl;
    private JLabel productColorLbl;
    private JLabel productSizeLbl;
    private JLabel productDescriptionLbl;
    private JLabel totalProductsLbl;

    private ProductTableModel tableModel;
    private DefaultComboBoxModel<ProductCategory> categoryModel;
    private DefaultComboBoxModel<ProductColor> colorModel;
    private DefaultComboBoxModel<ProductSize> sizeModel;

    private List<ProductCategory> categories;
    private List<ProductColor> colors;
    private List<ProductSize> sizes;
    private List<Product> products;

    private UpdateProductDialog productDialog;
    private ProductEventListener productEventListener;

    public ProductsUI(final List<ProductCategory> categories, final List<ProductColor> colors, final List<ProductSize> sizes, List<Product> products) {
        this.categories = categories;
        this.colors = colors;
        this.sizes = sizes;
        this.products = products;

        productsTbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                productsTbl.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            }
        });

        addProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = productNameTxtFld.getText();
                String productDescription = productDescriptionTxtArea.getText();

                boolean valid = !productName.isEmpty() && !productDescription.isEmpty();
                if (valid) {
                    ProductCategory category = (ProductCategory) productCategoryCmb.getSelectedItem();
                    ProductColor color = (ProductColor) productColorCmb.getSelectedItem();
                    ProductSize size = (ProductSize) productSizeCmb.getSelectedItem();

                    Product product = new Product(productName, productDescription, category, size, color);
                    ProductEvent productEvent = new ProductEvent(this, product);

                    if (productEventListener != null) {
                        productEventListener.addProductAction(productEvent);
                    }
                } else {
                    JOptionPane.showMessageDialog(getContentPanel(), "Введите название и описание товара!", "Ошибка", JOptionPane.OK_OPTION);
                }
            }
        });

        updateProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = productsTbl.getSelectedRow();

                if (row > -1) {
                    Product product = tableModel.getProducts().get(row);
                    productDialog = new UpdateProductDialog(categories, colors, sizes,getContentPanel());
                    productDialog.setProduct(product);

                    // Set Texts
                    productDialog.getProductNameTxtFld().setText(product.getName());
                    productDialog.getProductDescriptionTxtArea().setText(product.getDescription());

                    // Set Combo Boxes
                    productDialog.getCategoryModel().setSelectedItem(product.getProductCategory());
                    productDialog.getProductColorModel().setSelectedItem(product.getProductColor());
                    productDialog.getSizeModel().setSelectedItem(product.getProductSize());

                    productDialog.setUpdateProductDialogListener(new UpdateProductDialogListener() {
                        @Override
                        public void updateProductEvent(ProductEvent event) {
                            productEventListener.updateProductAction(event);
                        }
                    });

                    productDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(getContentPanel(), "Выберите конкретный товар для редактирования", "Ошибка", JOptionPane.OK_OPTION);
                }
            }
        });

        deleteProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fromRow = productsTbl.getSelectionModel().getMinSelectionIndex();
                int toRow = productsTbl.getSelectionModel().getMaxSelectionIndex();

                if (fromRow < 0 && toRow < 0) {
                    JOptionPane.showMessageDialog(getContentPanel(),
                            "Выберите конкретный товар для удаления", "Ошибка", JOptionPane.OK_OPTION);
                } else {
                    int action = JOptionPane.showConfirmDialog(getContentPanel(),
                            "Удалить данный товар?", "Удаление товара", JOptionPane.OK_CANCEL_OPTION);

                    if (action == JOptionPane.OK_OPTION) {
                        for (int row = toRow; row >= fromRow; row--) {
                            Product product = tableModel.getProducts().get(row);
                            ProductEvent productEvent = new ProductEvent(this, product);

                            if (productEventListener != null) {
                                productEventListener.deleteProductAction(productEvent);
                            }
                        }
                    }
                }
            }
        });
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

        tableModel = new ProductTableModel(products);
        productsTbl = new JTable(tableModel);
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public JLabel getTotalProductsLbl() {
        return totalProductsLbl;
    }

    public JTable getProductsTbl() {
        return productsTbl;
    }

    public ProductTableModel getTableModel() {
        return tableModel;
    }

    public JTextField getProductNameTxtFld() {
        return productNameTxtFld;
    }

    public JTextArea getProductDescriptionTxtArea() {
        return productDescriptionTxtArea;
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

    public void setProductEventListener(ProductEventListener productEventListener) {
        this.productEventListener = productEventListener;
    }
}
