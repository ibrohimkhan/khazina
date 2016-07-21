package tj.dushanbe.ibrohim.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="description", nullable=false)
	private String description;
	
	@ManyToOne(cascade={CascadeType.PERSIST})
	@JoinColumn(name="category_id")
	private ProductCategory productCategory;
	
	@ManyToOne(cascade={CascadeType.PERSIST})
	@JoinColumn(name="size_id")
	private ProductSize productSize;
	
	@ManyToOne(cascade={CascadeType.PERSIST})
	@JoinColumn(name="color_id")
	private ProductColor productColor;
	
	@OneToMany(mappedBy="product", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Stock> stocks = new ArrayList<Stock>();
	
	public Product() {}

	public Product(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Product(String name, String description, ProductCategory productCategory, ProductSize productSize, ProductColor productColor) {
		this.name = name;
		this.description = description;
		this.productSize = productSize;
		this.productColor = productColor;
		this.productCategory = productCategory;
	}

	public List<Stock> getStock() {
		return stocks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public ProductSize getProductSize() {
		return productSize;
	}

	public void setProductSize(ProductSize productSize) {
		this.productSize = productSize;
	}

	public ProductColor getProductColor() {
		return productColor;
	}

	public void setProductColor(ProductColor productColor) {
		this.productColor = productColor;
	}

	public long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((productCategory == null) ? 0 : productCategory.hashCode());
		result = prime * result + ((productColor == null) ? 0 : productColor.hashCode());
		result = prime * result + ((productSize == null) ? 0 : productSize.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (productCategory == null) {
			if (other.productCategory != null)
				return false;
		} else if (!productCategory.equals(other.productCategory))
			return false;
		if (productColor == null) {
			if (other.productColor != null)
				return false;
		} else if (!productColor.equals(other.productColor))
			return false;
		if (productSize == null) {
			if (other.productSize != null)
				return false;
		} else if (!productSize.equals(other.productSize))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}
