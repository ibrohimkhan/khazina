package tj.dushanbe.ibrohim.services;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import tj.dushanbe.ibrohim.models.Stock;
import tj.dushanbe.ibrohim.services.util.HibernateUtil;

public class StockServices {
	public static Long addStock(Stock stock) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Long id = (Long) session.save(stock);
		session.getTransaction().commit();
		session.close();

		return id;
	}

	@SuppressWarnings("unchecked")
	public static List<Stock> getAllStocks() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Stock> results = session.createCriteria(Stock.class)
				.list();
		session.close();

		return results;
	}

	public static List<Stock> getProductsFromStock() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Stock> results = session.createCriteria(Stock.class)
				.add(Restrictions.eq("isInStock", true))
				.list();
		session.close();

		return results;
	}

	public static List<Stock> getSoldProductsFromStock() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Stock> results = session.createCriteria(Stock.class)
				.add(Restrictions.eq("isInStock", false))
				.list();
		session.close();

		return results;
	}

	public static Stock getStock(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Stock stock = (Stock) session.createCriteria(Stock.class)
				.add(Restrictions.idEq(id))
				.uniqueResult();
		session.close();

		return stock;
	}

	public static void updateStock(Stock stock) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.update(stock);
		session.getTransaction().commit();
		session.close();
	}

	public static void deleteStock(Stock stock) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(stock);
		session.getTransaction().commit();
		session.close();
	}
}
