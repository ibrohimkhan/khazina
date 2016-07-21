package tj.dushanbe.ibrohim.services;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import tj.dushanbe.ibrohim.models.Product;
import tj.dushanbe.ibrohim.models.ProductCategory;
import tj.dushanbe.ibrohim.models.ProductColor;
import tj.dushanbe.ibrohim.models.ProductSize;
import tj.dushanbe.ibrohim.services.util.HibernateUtil;

public class ProductServices {

	// Product
	public static Long addProduct(Product product) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Long id = (Long) session.save(product);
		session.getTransaction().commit();
		session.close();

		return id;
	}

	@SuppressWarnings("unchecked")
	public static List<Product> getAllProducts() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Product> results = session.createCriteria(Product.class)
				.list();
		session.close();

		return results;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Product> getAllProductsWithReferences() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Product> results = session.createCriteria(Product.class)
				.setFetchMode("stocks", FetchMode.JOIN)
				.list();
		session.close();
		
		return results;
	}

	public static Product getProductWithReference(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Product product = (Product) session.createCriteria(Product.class)
				.setFetchMode("stocks", FetchMode.JOIN)
				.add(Restrictions.idEq(id))
				.uniqueResult();
		session.close();

		return product;
	}

	public static void updateProduct(Product product) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.update(product);
		session.getTransaction().commit();
		session.close();
	}

	public static void deleteProduct(Product product) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(product);
		session.getTransaction().commit();
		session.close();
	}

	// ProductCategory
	public static Long addProductCategory(ProductCategory category) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Long id = (Long) session.save(category);
		session.getTransaction().commit();
		session.close();

		return id;
	}

	@SuppressWarnings("unchecked")
	public static List<ProductCategory> getAllProductCategories() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ProductCategory> results = session.createCriteria(ProductCategory.class)
				.list();
		session.close();

		return results;
	}
	
	@SuppressWarnings("unchecked")
	public static List<ProductCategory> getAllProductCategoriesWithReferences() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ProductCategory> results = session.createCriteria(ProductCategory.class)
				.setFetchMode("products", FetchMode.JOIN)
				.list();
		session.close();

		return results;
	}

	public static ProductCategory getProductCategoryWithReference(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		ProductCategory category = (ProductCategory) session.createCriteria(ProductCategory.class)
				.setFetchMode("products", FetchMode.JOIN)
				.add(Restrictions.idEq(id))
				.uniqueResult();
		session.close();

		return category;
	}

	public static void updateProductCategory(ProductCategory category) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.update(category);
		session.getTransaction().commit();
		session.close();
	}

	public static void deleteProductCategory(ProductCategory category) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(category);
		session.getTransaction().commit();
		session.close();
	}

	// ProductSize
	public static Long addProductSize(ProductSize size) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Long id = (Long) session.save(size);
		session.getTransaction().commit();
		session.close();

		return id;
	}

	@SuppressWarnings("unchecked")
	public static List<ProductSize> getAllProductSize() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ProductSize> results = session.createCriteria(ProductSize.class)
				.list();
		session.close();

		return results;
	}

	@SuppressWarnings("unchecked")
	public static List<ProductSize> getAllProductSizeWithReferences() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ProductSize> results = session.createCriteria(ProductSize.class)
				.setFetchMode("products", FetchMode.JOIN)
				.list();
		session.close();
		
		return results;
	}

	public static ProductSize getProductSizeWithReference(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		ProductSize size = (ProductSize) session.createCriteria(ProductSize.class)
				.setFetchMode("products", FetchMode.JOIN)
				.add(Restrictions.idEq(id))
				.uniqueResult();
		session.close();

		return size;
	}

	public static void updateProductSize(ProductSize size) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.update(size);
		session.getTransaction().commit();
		session.close();
	}

	public static void deleteProductSize(ProductSize size) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(size);
		session.getTransaction().commit();
		session.close();
	}

	// ProductColor
	public static Long addProductColor(ProductColor color) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Long id = (Long) session.save(color);
		session.getTransaction().commit();
		session.close();

		return id;
	}

	@SuppressWarnings("unchecked")
	public static List<ProductColor> getAllProductColors() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ProductColor> results = session.createCriteria(ProductColor.class)
				.list();
		session.close();

		return results;
	}

	@SuppressWarnings("unchecked")
	public static List<ProductColor> getAllProductColorsWithReferences() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ProductColor> results = session.createCriteria(ProductColor.class)
				.setFetchMode("products", FetchMode.JOIN)
				.list();
		session.close();
		
		return results;
	}

	public static ProductColor getProductColorWithReference(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		ProductColor color = (ProductColor) session.createCriteria(ProductColor.class)
				.setFetchMode("products", FetchMode.JOIN)
				.add(Restrictions.idEq(id))
				.uniqueResult();
		session.close();

		return color;
	}

	public static void updateProductColor(ProductColor color) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.update(color);
		session.getTransaction().commit();
		session.close();
	}

	public static void deleteProductColor(ProductColor color) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(color);
		session.getTransaction().commit();
		session.close();
	}
}