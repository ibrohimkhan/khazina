package tj.dushanbe.ibrohim.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibrohim on 1/20/2015.
 */
@Entity
@Table(name="product_category")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private long productCategoryId;

    @Column(name="name", unique=true, nullable=false)
    private String name;

    @OneToMany(mappedBy="productCategory", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Product> products = new ArrayList<Product>();

    public ProductCategory() {}

    public ProductCategory(String name) {
        this.name = name;
    }

    public long getProductCategoryId() {
        return productCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductCategory category = (ProductCategory) o;

        if (productCategoryId != category.productCategoryId) return false;
        if (!name.equals(category.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (productCategoryId ^ (productCategoryId >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
