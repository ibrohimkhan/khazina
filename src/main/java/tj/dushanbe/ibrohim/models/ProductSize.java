package tj.dushanbe.ibrohim.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="product_size")
public class ProductSize {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="width", nullable=false)
	private float width;
	
	@Column(name="height", nullable=false)
	private float height;

	@OneToMany(mappedBy="productSize", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Product> products = new ArrayList<Product>();
	
	public ProductSize() {}

	public ProductSize(float width, float height) {
		this.height = height;
		this.width = width;
	}
	
	public long getId() {
		return id;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public List<Product> getProducts() {
		return products;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(height);
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		result = prime * result + Float.floatToIntBits(width);
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
		ProductSize other = (ProductSize) obj;
		if (Float.floatToIntBits(height) != Float.floatToIntBits(other.height))
			return false;
		if (id != other.id)
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (Float.floatToIntBits(width) != Float.floatToIntBits(other.width))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ш: " + width + " - В: " + height;
	}
}
