package tj.dushanbe.ibrohim.views.ui.dialogs;

import tj.dushanbe.ibrohim.events.EventType;
import tj.dushanbe.ibrohim.events.ProductConfigurationEvent;
import tj.dushanbe.ibrohim.events.listeners.UpdateProductConfigurationDialogListener;
import tj.dushanbe.ibrohim.models.ProductCategory;
import tj.dushanbe.ibrohim.models.ProductColor;
import tj.dushanbe.ibrohim.models.ProductSize;

import javax.swing.*;
import java.awt.event.*;

public class UpdateProductConfigurationDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField oldNameTxtFld;
    private JLabel oldNameLbl;
    private JTextField newNameTxtFld;
    private JLabel newNameLbl;
    private JPanel controllPanel;
    private JPanel elementsPanel;
    private JPanel freeSpacePanel;
    private JTextField newNameTxtFldExtra;
    private JLabel newNameLblExtra;

    private UpdateProductConfigurationDialogListener updateConfigurationListener;
    private ProductCategory productCategory;
    private ProductColor productColor;
    private ProductSize productSize;

    private EventType type;

    public UpdateProductConfigurationDialog(String title, JPanel parent) {
        setContentPane(contentPane);
        setModal(true);

        setSize(370, 200);
        setTitle(title);
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

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public void setProductColor(ProductColor productColor) {
        this.productColor = productColor;
    }

    public void setProductSize(ProductSize productSize) {
        this.productSize = productSize;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public void setUpdateConfigurationListener(UpdateProductConfigurationDialogListener updateConfigurationListener) {
        this.updateConfigurationListener = updateConfigurationListener;
    }

    public JLabel getOldNameLbl() {
        return oldNameLbl;
    }

    public JTextField getOldNameTxtFld() {
        return oldNameTxtFld;
    }

    public JLabel getNewNameLbl() {
        return newNameLbl;
    }

    public JLabel getNewNameLblExtra() {
        return newNameLblExtra;
    }

    public JTextField getNewNameTxtFldExtra() {
        return newNameTxtFldExtra;
    }

    private void onOK() {
        String name = newNameTxtFld.getText();
        String height = null;

        if (newNameTxtFldExtra.isVisible()) height = newNameTxtFldExtra.getText();

        if (name != null && !name.isEmpty()) {
            if (updateConfigurationListener != null) {
                switch (type) {
                    case CATEGORY:
                        productCategory.setName(name);
                        ProductConfigurationEvent pCategoryEvent = new ProductConfigurationEvent(this, productCategory, EventType.CATEGORY);
                        updateConfigurationListener.updateProductConfiguration(pCategoryEvent);

                        break;

                    case COLOR:
                        productColor.setColor(name);
                        ProductConfigurationEvent pColorEvent = new ProductConfigurationEvent(this, productColor, EventType.COLOR);
                        updateConfigurationListener.updateProductConfiguration(pColorEvent);

                        break;

                    case SIZE:
                        if (height != null && !height.isEmpty()) {
                            productSize.setWidth(Float.parseFloat(name));
                            productSize.setHeight(Float.parseFloat(height));

                            ProductConfigurationEvent pSizeEvent = new ProductConfigurationEvent(this, productSize, EventType.SIZE);
                            updateConfigurationListener.updateProductConfiguration(pSizeEvent);

                            newNameLblExtra.setVisible(false);
                            newNameTxtFldExtra.setVisible(false);
                        }

                        break;
                }
            }
        }

        dispose();
    }

    private void onCancel() {
        if (newNameLblExtra.isVisible()) {
            newNameLblExtra.setVisible(false);
        }

        if (newNameTxtFldExtra.isVisible()) {
            newNameTxtFldExtra.setVisible(false);
        }

        dispose();
    }
}
