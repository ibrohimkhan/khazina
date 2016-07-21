package tj.dushanbe.ibrohim.services;

import org.hibernate.Query;
import org.hibernate.Session;
import tj.dushanbe.ibrohim.models.*;
import tj.dushanbe.ibrohim.services.util.HibernateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by ibrohim on 2/22/2015.
 */
public class AnalyticsService {

    public static List<Stock> getProducts(Product product, ProductCategory productCategory, ProductColor productColor, ProductSize productSize, Date fromDate, Date toDate, boolean isInStock, boolean isOutStock) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select {stock.*} from stock stock\n");
        queryBuilder.append("inner join product product on stock.product_id = product.id\n");
        queryBuilder.append("inner join product_category category on product.category_id = category.id\n");
        queryBuilder.append("inner join product_color color on product.color_id = color.id\n");
        queryBuilder.append("inner join product_size size on product.size_id = size.id\n");

        if (product != null && productCategory != null && productColor != null && productSize != null) {
            queryBuilder.append("where product.id = " + product.getId());
            queryBuilder.append("\nand category.id = " + productCategory.getProductCategoryId());
            queryBuilder.append("\nand color.id = " + productColor.getId());
            queryBuilder.append("\nand size.id = " + productSize.getId());

        } else if (product == null && productCategory != null && productColor != null && productSize != null) {
            queryBuilder.append("where category.id = " + productCategory.getProductCategoryId());
            queryBuilder.append("\nand color.id = " + productColor.getId());
            queryBuilder.append("\nand size.id = " + productSize.getId());

        } else if (product != null && productCategory == null && productColor != null && productSize != null) {
            queryBuilder.append("where product.id = " + product.getId());
            queryBuilder.append("\nand color.id = " + productColor.getId());
            queryBuilder.append("\nand size.id = " + productSize.getId());

        } else if (product != null && productCategory != null && productColor == null && productSize != null) {
            queryBuilder.append("where product.id = " + product.getId());
            queryBuilder.append("\nand category.id = " + productCategory.getProductCategoryId());
            queryBuilder.append("\nand size.id = " + productSize.getId());

        } else if (product != null && productCategory != null && productColor != null && productSize == null) {
            queryBuilder.append("where product.id = " + product.getId());
            queryBuilder.append("\nand category.id = " + productCategory.getProductCategoryId());
            queryBuilder.append("\nand color.id = " + productColor.getId());

        } else if (product == null && productCategory == null && productColor != null && productSize != null) {
            queryBuilder.append("where color.id = " + productColor.getId());
            queryBuilder.append("\nand size.id = " + productSize.getId());

        }  else if (product != null && productCategory != null && productColor == null && productSize == null) {
            queryBuilder.append("where product.id = " + product.getId());
            queryBuilder.append("\nand category.id = " + productCategory.getProductCategoryId());

        }  else if (product == null && productCategory != null && productColor != null && productSize == null) {
            queryBuilder.append("where category.id = " + productCategory.getProductCategoryId());
            queryBuilder.append("\nand color.id = " + productColor.getId());

        }  else if (product != null && productCategory == null && productColor != null && productSize == null) {
            queryBuilder.append("where product.id = " + product.getId());
            queryBuilder.append("\nand color.id = " + productColor.getId());

        }  else if (product != null && productCategory == null && productColor == null && productSize != null) {
            queryBuilder.append("where product.id = " + product.getId());
            queryBuilder.append("\nand size.id = " + productSize.getId());

        }  else if (product == null && productCategory != null && productColor == null && productSize != null) {
            queryBuilder.append("where category.id = " + productCategory.getProductCategoryId());
            queryBuilder.append("\nand size.id = " + productSize.getId());

        } else if (product == null && productCategory == null && productColor == null && productSize != null) {
            queryBuilder.append("where size.id = " + productSize.getId());

        } else if (product == null && productCategory == null && productColor != null && productSize == null) {
            queryBuilder.append("where color.id = " + productColor.getId());

        } else if (product == null && productCategory != null && productColor == null && productSize == null) {
            queryBuilder.append("where category.id = " + productCategory.getProductCategoryId());

        } else if (product != null && productCategory == null && productColor == null && productSize == null) {
            queryBuilder.append("where product.id = " + product.getId());
        }

        if (isOutStock) {
        } else {
            queryBuilder.append("\nand stock.in_stock = " + (isInStock == false ? 0 : 1));
        }

        if (fromDate != null && toDate != null) {
            queryBuilder.append("\nand stock.in_stock_date >= " + fromDate.getTime() + " and stock.in_stock_date <= " + toDate.getTime());
        }

        Query query = session.createSQLQuery(queryBuilder.toString()).addEntity("stock", Stock.class);
        List<Stock> results = query.list();

        session.close();
        return results;
    }
}
