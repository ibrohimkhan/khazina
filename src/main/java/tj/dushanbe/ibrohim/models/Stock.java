package tj.dushanbe.ibrohim.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="stock")
public class Stock {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	@Column(name="in_stock", nullable=false)
	private Boolean isInStock;
	
	@Column(name="in_stock_date", nullable=false)
	private Long inStockDate;

	@Column(name="out_of_stock_date")
	private Long outStockDate;
	
	@Column(name="price")
	private Double price;

	public Stock() {
		this.isInStock = true;
		this.inStockDate = new Date().getTime();
		this.price = 0d;
	}
	
	public Stock(Product product) {
		this.isInStock = true;
		this.inStockDate = new Date().getTime();
		this.product = product;
		this.price = 0d;
	}

	public Stock(Product product, Long inStockDate) {
		this.isInStock = true;
		this.inStockDate = inStockDate;
		this.product = product;
		this.price = 0d;
	}
	
	public Stock(Product product, Long inStockDate, Double price) {
		this.price = price;
		this.product = product;
		this.isInStock = true;
		this.inStockDate = inStockDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Boolean getIsInStock() {
		return isInStock;
	}

	public void setIsInStock(Boolean isInStock) {
		this.isInStock = isInStock;
	}

	public Long getInStockDate() {
		return inStockDate;
	}

	public void setInStockDate(Long inStockDate) {
		this.inStockDate = inStockDate;
	}

	public Long getOutStockDate() {
		return outStockDate;
	}

	public void setOutStockDate(Long outStockDate) {
		this.outStockDate = outStockDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Stock stock = (Stock) o;

		if (id != stock.id) return false;
		if (!inStockDate.equals(stock.inStockDate)) return false;
		if (!isInStock.equals(stock.isInStock)) return false;
		if (outStockDate != null ? !outStockDate.equals(stock.outStockDate) : stock.outStockDate != null) return false;
		if (price != null ? !price.equals(stock.price) : stock.price != null) return false;
		if (!product.equals(stock.product)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + product.hashCode();
		result = 31 * result + isInStock.hashCode();
		result = 31 * result + inStockDate.hashCode();
		result = 31 * result + (outStockDate != null ? outStockDate.hashCode() : 0);
		result = 31 * result + (price != null ? price.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", product=" + product + ", isInStock=" + isInStock + ", inStockDate=" + inStockDate
				+ ", outStockDate=" + outStockDate + ", price=" + price + "]";
	}
}
