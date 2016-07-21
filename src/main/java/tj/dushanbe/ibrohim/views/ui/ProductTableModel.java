package tj.dushanbe.ibrohim.views.ui;

import tj.dushanbe.ibrohim.models.Product;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {

    private static final long serialVersionUID = -3267422287293332388L;
    private String[] tableHeader = {"Название", "Описание", "Категория", "Цвет", "Размер"};
    private List<Product> products;

    public ProductTableModel(List<Product> products) {
        this.products = products;
    }

    @Override
    public String getColumnName(int column) {
        return tableHeader[column];
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return product.getName();

            case 1:
                return product.getDescription();

            case 2:
                return product.getProductCategory();

            case 3:
                return product.getProductColor();

            case 4:
                return product.getProductSize();
        }

        return null;
    }

    public List<Product> getProducts() {
        return products;
    }
}
