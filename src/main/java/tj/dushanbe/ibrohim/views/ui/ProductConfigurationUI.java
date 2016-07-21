package tj.dushanbe.ibrohim.views.ui;

import tj.dushanbe.ibrohim.events.EventType;
import tj.dushanbe.ibrohim.events.ProductConfigurationEvent;
import tj.dushanbe.ibrohim.events.listeners.ProductConfigurationListener;
import tj.dushanbe.ibrohim.events.listeners.UpdateProductConfigurationDialogListener;
import tj.dushanbe.ibrohim.models.ProductCategory;
import tj.dushanbe.ibrohim.models.ProductColor;
import tj.dushanbe.ibrohim.models.ProductSize;
import tj.dushanbe.ibrohim.views.ui.dialogs.UpdateProductConfigurationDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by ibrohim on 1/20/2015.
 */
public class ProductConfigurationUI {
    private JPanel contentPanel;
    private JButton addCategoryBtn;
    private JButton addColorBtn;
    private JTextField categoryNameTxtFld;
    private JTextField colorNameTxtFld;
    private JTextField widthTxtFld;
    private JButton addSizeBtn;
    private JList categoryList;
    private JList colorList;
    private JButton updateCategoryBtn;
    private JButton deleteCategoryBtn;
    private JButton updateColorBtn;
    private JButton deleteColorBtn;
    private JButton updateSizeBtn;
    private JTextField heightTxtFld;
    private JPanel categoryPanel;
    private JLabel categoryNameLbl;
    private JPanel colorPanel;
    private JLabel colorNameLbl;
    private JPanel sizePanel;
    private JLabel widthLbl;
    private JList sizeList;
    private JButton deleteSizeBtn;
    private JLabel heightLbl;
    private DefaultListModel<ProductCategory> categoryModel;
    private DefaultListModel<ProductColor> colorModel;
    private DefaultListModel<ProductSize> sizeModel;
    private List<ProductCategory> categories;
    private List<ProductColor> colors;
    private List<ProductSize> sizes;

    private ProductConfigurationListener configurationListener;
    private UpdateProductConfigurationDialog configDialog;

    public ProductConfigurationUI(List<ProductCategory> categories, List<ProductColor> colors, List<ProductSize> sizes) {
        this.categories = categories;
        this.colors = colors;
        this.sizes = sizes;

        categoryList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        colorList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        sizeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Product Category Buttons
        addCategoryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoryName = categoryNameTxtFld.getText();

                if (categoryName != null && !categoryName.isEmpty()) {
                    ProductCategory category = new ProductCategory(categoryName);
                    ProductConfigurationEvent pCategoryEvent = new ProductConfigurationEvent(this, category, EventType.CATEGORY);

                    if (configurationListener != null) {
                        configurationListener.addProductConfiguration(pCategoryEvent);
                    }

                    if (!categoryNameTxtFld.getText().isEmpty()) {
                        categoryNameTxtFld.setText("");
                    }
                }
            }
        });

        updateCategoryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductCategory categoryItem = (ProductCategory) categoryList.getSelectedValue();

                if (categoryItem != null) {
                    configDialog = new UpdateProductConfigurationDialog("Изменить название категории товара", getContentPanel());
                    configDialog.setProductCategory(categoryItem);
                    configDialog.setType(EventType.CATEGORY);

                    configDialog.setUpdateConfigurationListener(new UpdateProductConfigurationDialogListener() {
                        @Override
                        public void updateProductConfiguration(ProductConfigurationEvent event) {
                            configurationListener.updateProductConfiguration(event);
                        }
                    });

                    configDialog.getOldNameLbl().setText("Старая категория");
                    configDialog.getNewNameLbl().setText("Новая категория");
                    configDialog.getOldNameTxtFld().setText(categoryItem.getName());
                    configDialog.setVisible(true);
                }
            }
        });

        deleteCategoryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ProductCategory> categoryItems = categoryList.getSelectedValuesList();

                if (categoryItems != null && categoryItems.size() > 0) {
                    int action = JOptionPane.showConfirmDialog(getContentPanel(),
                            "Удалить данную категорию товара?", "Удаление категории", JOptionPane.OK_CANCEL_OPTION);

                    if (action == JOptionPane.OK_OPTION) {
                        for (ProductCategory categoryItem : categoryItems) {
                            ProductConfigurationEvent pCategoryEvent = new ProductConfigurationEvent(this, categoryItem, EventType.CATEGORY);

                            if (configurationListener != null) {
                                configurationListener.deleteProductConfiguration(pCategoryEvent);
                            }
                        }
                    }
                }
            }
        });

        // Product Color Buttons
        addColorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String colorName = colorNameTxtFld.getText();

                if (colorName != null && !colorName.isEmpty()) {
                    ProductColor color = new ProductColor(colorName);
                    ProductConfigurationEvent pColorEvent = new ProductConfigurationEvent(this, color, EventType.COLOR);

                    if (configurationListener != null) {
                        configurationListener.addProductConfiguration(pColorEvent);
                    }

                    if (!colorNameTxtFld.getText().isEmpty()) {
                        colorNameTxtFld.setText("");
                    }
                }
            }
        });

        updateColorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductColor colorItem = (ProductColor) colorList.getSelectedValue();

                if (colorItem != null) {
                    configDialog = new UpdateProductConfigurationDialog("Изменить цвет товара", getContentPanel());
                    configDialog.setProductColor(colorItem);
                    configDialog.setType(EventType.COLOR);

                    configDialog.setUpdateConfigurationListener(new UpdateProductConfigurationDialogListener() {
                        @Override
                        public void updateProductConfiguration(ProductConfigurationEvent event) {
                            configurationListener.updateProductConfiguration(event);
                        }
                    });

                    configDialog.getOldNameLbl().setText("Старый цвет");
                    configDialog.getNewNameLbl().setText("Новый цвет");
                    configDialog.getOldNameTxtFld().setText(colorItem.getColor());
                    configDialog.setVisible(true);
                }
            }
        });

        deleteColorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ProductColor> colorItems = colorList.getSelectedValuesList();

                if (colorItems != null && colorItems.size() > 0) {
                    int action = JOptionPane.showConfirmDialog(getContentPanel(),
                            "Удалить цвет товара?", "Удаление цвета", JOptionPane.OK_CANCEL_OPTION);

                    if (action == JOptionPane.OK_OPTION) {
                        for (ProductColor colorItem : colorItems) {
                            ProductConfigurationEvent pCategoryEvent = new ProductConfigurationEvent(this, colorItem, EventType.COLOR);

                            if (configurationListener != null) {
                                configurationListener.deleteProductConfiguration(pCategoryEvent);
                            }
                        }
                    }
                }
            }
        });

        // Product Size
        addSizeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    float width = Float.parseFloat(widthTxtFld.getText());
                    float height = Float.parseFloat(heightTxtFld.getText());

                    ProductSize size = new ProductSize(width, height);
                    ProductConfigurationEvent pColorEvent = new ProductConfigurationEvent(this, size, EventType.SIZE);

                    if (configurationListener != null) {
                        configurationListener.addProductConfiguration(pColorEvent);
                    }

                    if (!widthTxtFld.getText().isEmpty()) {
                        widthTxtFld.setText("");
                    }

                    if (!heightTxtFld.getText().isEmpty()) {
                        heightTxtFld.setText("");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(getContentPanel(), ex.getMessage(), "Системная ошибка", JOptionPane.OK_OPTION);
                }
            }
        });

        updateSizeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductSize sizeItem = (ProductSize) sizeList.getSelectedValue();

                if (sizeItem != null) {
                    configDialog = new UpdateProductConfigurationDialog("Изменить размер товара", getContentPanel());
                    configDialog.setProductSize(sizeItem);
                    configDialog.setType(EventType.SIZE);

                    configDialog.setUpdateConfigurationListener(new UpdateProductConfigurationDialogListener() {
                        @Override
                        public void updateProductConfiguration(ProductConfigurationEvent event) {
                            configurationListener.updateProductConfiguration(event);
                        }
                    });

                    configDialog.getOldNameLbl().setText("Старый размер");
                    configDialog.getOldNameTxtFld().setText(sizeItem.toString());
                    configDialog.getNewNameLbl().setText("Новая ширина");
                    configDialog.getNewNameLblExtra().setText("Новая высота");
                    configDialog.getNewNameLblExtra().setVisible(true);
                    configDialog.getNewNameTxtFldExtra().setVisible(true);
                    configDialog.setVisible(true);
                }
            }
        });

        deleteSizeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ProductSize> sizeItems = sizeList.getSelectedValuesList();

                if (sizeItems != null && sizeItems.size() > 0) {
                    int action = JOptionPane.showConfirmDialog(getContentPanel(),
                            "Удалить данный размер товара?", "Удаление размера", JOptionPane.OK_CANCEL_OPTION);

                    if (action == JOptionPane.OK_OPTION) {
                        for (ProductSize sizeItem : sizeItems) {
                            ProductConfigurationEvent pSizeEvent = new ProductConfigurationEvent(this, sizeItem, EventType.SIZE);

                            if (configurationListener != null) {
                                configurationListener.deleteProductConfiguration(pSizeEvent);
                            }
                        }
                    }
                }
            }
        });
    }

    private void createUIComponents() {
        categoryModel = new DefaultListModel<ProductCategory>();
        for (ProductCategory category : categories) {
            categoryModel.addElement(category);
        }

        colorModel = new DefaultListModel<ProductColor>();
        for (ProductColor color : colors) {
            colorModel.addElement(color);
        }

        sizeModel = new DefaultListModel<ProductSize>();
        for (ProductSize size : sizes) {
            sizeModel.addElement(size);
        }

        categoryList = new JList<ProductCategory>(categoryModel);
        colorList = new JList<ProductColor>(colorModel);
        sizeList = new JList<ProductSize>(sizeModel);
    }

    public JList getCategoryList() {
        return categoryList;
    }

    public JList getColorList() {
        return colorList;
    }

    public JList getSizeList() {
        return sizeList;
    }

    public DefaultListModel<ProductCategory> getCategoryModel() {
        return categoryModel;
    }

    public DefaultListModel<ProductColor> getColorModel() {
        return colorModel;
    }

    public DefaultListModel<ProductSize> getSizeModel() {
        return sizeModel;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setConfigurationListener(ProductConfigurationListener configurationListener) {
        this.configurationListener = configurationListener;
    }
}
