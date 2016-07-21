package tj.dushanbe.ibrohim.views;

import tj.dushanbe.ibrohim.controllers.AnalyticsController;
import tj.dushanbe.ibrohim.controllers.ProductController;
import tj.dushanbe.ibrohim.controllers.StockController;
import tj.dushanbe.ibrohim.events.AnalyticsEvent;
import tj.dushanbe.ibrohim.events.ProductConfigurationEvent;
import tj.dushanbe.ibrohim.events.ProductEvent;
import tj.dushanbe.ibrohim.events.StockEvent;
import tj.dushanbe.ibrohim.events.listeners.*;
import tj.dushanbe.ibrohim.models.*;
import tj.dushanbe.ibrohim.services.util.HibernateUtil;
import tj.dushanbe.ibrohim.views.ui.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -1485885771518153312L;
	private ProductConfigurationUI productConfigurationUI;
	private ProductsUI productsUI;
	private IncomeStockUI incomeStockUI;
	private OutgoStockUI outgoStockUI;
	private AnalyticsUI analyticsUI;

	private ProductController productController;
	private StockController stockController;
	private AnalyticsController analyticsController;

	private List<ProductCategory> categories;
	private List<ProductColor> colors;
	private List<ProductSize> sizes;
	private List<Product> products;
	private List<Stock> inStock;
	private List<Stock> outStock;

	private JFileChooser exportDatabase;
	private JFileChooser importDatabase;

	public MainFrame() {
		super("Хазина-Software");
		setLayout(new FlowLayout());

		// Product Controller
		productController = new ProductController();

		// Stock Controller
		stockController = new StockController();

		// Analytics Controller
		analyticsController = new AnalyticsController();

		// List of Stocks, Products and Configs
		categories = productController.getProductCategoryList();
		colors = productController.getProductColorList();
		sizes = productController.getProductSizeList();
		products = productController.getProducts();
		inStock = stockController.getProductsFromStock();
		outStock = stockController.getSoldProductsFromStock();

		// Product Configuration Components
		productConfigurationUI = new ProductConfigurationUI(categories, colors, sizes);
		productConfigurationUI.getContentPanel().setVisible(false);
		add(productConfigurationUI.getContentPanel());

		productConfigurationUI.setConfigurationListener(new ProductConfigurationListener() {
			@Override
			public void addProductConfiguration(ProductConfigurationEvent event) {
				switch (event.getType()) {
					case CATEGORY:
						Long categoryId = productController.addProductCategory(event);

						if (categoryId != null) {
							productConfigurationUI.getCategoryModel().addElement(event.getProductCategory());
							int index = productConfigurationUI.getCategoryModel().indexOf(event.getProductCategory());
							productConfigurationUI.getCategoryList().setSelectedIndex(index);
						}

						break;

					case COLOR:
						Long colorId = productController.addProductColor(event);

						if (colorId != null) {
							productConfigurationUI.getColorModel().addElement(event.getProductColor());
							int index = productConfigurationUI.getColorModel().indexOf(event.getProductColor());
							productConfigurationUI.getColorList().setSelectedIndex(index);
						}

						break;

					case SIZE:
						Long sizeId = productController.addProductSize(event);

						if (sizeId != null) {
							productConfigurationUI.getSizeModel().addElement(event.getProductSize());
							int index = productConfigurationUI.getSizeModel().indexOf(event.getProductSize());
							productConfigurationUI.getSizeList().setSelectedIndex(index);
						}

						break;
				}
			}

			@Override
			public void updateProductConfiguration(ProductConfigurationEvent event) {
				switch (event.getType()) {
					case CATEGORY:
						productController.updateProductCategory(event);

						int categoryIndex = productConfigurationUI.getCategoryList().getSelectedIndex();
						productConfigurationUI.getCategoryModel().set(categoryIndex, event.getProductCategory());
						productConfigurationUI.getCategoryList().setSelectedIndex(categoryIndex);

						break;

					case COLOR:
						productController.updateProductColor(event);

						int colorIndex = productConfigurationUI.getColorList().getSelectedIndex();
						productConfigurationUI.getColorModel().set(colorIndex, event.getProductColor());
						productConfigurationUI.getColorList().setSelectedIndex(colorIndex);

						break;

					case SIZE:
						productController.updateProductSize(event);

						int sizeIndex = productConfigurationUI.getSizeList().getSelectedIndex();
						productConfigurationUI.getSizeModel().set(sizeIndex, event.getProductSize());
						productConfigurationUI.getSizeList().setSelectedIndex(sizeIndex);

						break;
				}
			}

			@Override
			public void deleteProductConfiguration(ProductConfigurationEvent event) {
				switch (event.getType()) {
					case CATEGORY:
						productController.deleteProductCategory(event);
						productConfigurationUI.getCategoryModel().removeElement(event.getProductCategory());

						break;

					case COLOR:
						productController.deleteProductColor(event);
						productConfigurationUI.getColorModel().removeElement(event.getProductColor());

						break;

					case SIZE:
						productController.deleteProductSize(event);
						productConfigurationUI.getSizeModel().removeElement(event.getProductSize());

						break;
				}
			}
		});

		// Product UI Components
		productsUI = new ProductsUI(categories, colors, sizes, products);
		productsUI.getContentPanel().setVisible(false);
		add(productsUI.getContentPanel());

		productsUI.setProductEventListener(new ProductEventListener() {
			@Override
			public void addProductAction(ProductEvent event) {
				Long id = productController.addProduct(event);

				if (id != null) {
					productsUI.getTableModel().getProducts().add(event.getProduct());
					productsUI.getTableModel().fireTableDataChanged();

					productsUI.getProductNameTxtFld().setText("");
					productsUI.getProductDescriptionTxtArea().setText("");

					productsUI.getTotalProductsLbl().setText("" + productsUI.getTableModel().getRowCount());
				}
			}

			@Override
			public void updateProductAction(ProductEvent event) {
				productController.updateProduct(event);
				productsUI.getTableModel().getProducts().set(productsUI.getProductsTbl().getSelectedRow(), event.getProduct());
				productsUI.getTableModel().fireTableDataChanged();

				productsUI.getProductNameTxtFld().setText("");
				productsUI.getProductDescriptionTxtArea().setText("");
			}

			@Override
			public void deleteProductAction(ProductEvent event) {
				productController.deleteProduct(event);
				productsUI.getTableModel().getProducts().remove(event.getProduct());
				productsUI.getTableModel().fireTableDataChanged();

				productsUI.getProductNameTxtFld().setText("");
				productsUI.getProductDescriptionTxtArea().setText("");

				productsUI.getTotalProductsLbl().setText("" + productsUI.getTableModel().getRowCount());
			}
		});

		// Stock UI Components
		incomeStockUI = new IncomeStockUI(products, inStock);
		incomeStockUI.getContentPanel().setVisible(false);
		add(incomeStockUI.getContentPanel());

		incomeStockUI.setStockEventListener(new StockEventListener() {
			@Override
			public void addStockAction(StockEvent event) {
				Long id = stockController.addStock(event);

				if (id != null) {
					incomeStockUI.getStockTableModel().getStockList().add(event.getStock());
					incomeStockUI.getStockTableModel().fireTableDataChanged();

					incomeStockUI.getTotal().setText("" + incomeStockUI.getStockTableModel().getRowCount());
				}
			}

			@Override
			public void updateStockAction(StockEvent event) {
				stockController.updateStock(event);
				incomeStockUI.getStockTableModel().getStockList().set(incomeStockUI.getTable().getSelectedRow(), event.getStock());
				incomeStockUI.getStockTableModel().fireTableDataChanged();
			}

			@Override
			public void deleteStockAction(StockEvent event) {
				stockController.deleteStock(event);
				incomeStockUI.getStockTableModel().getStockList().remove(event.getStock());
				incomeStockUI.getStockTableModel().fireTableDataChanged();
				incomeStockUI.getTotal().setText("" + incomeStockUI.getStockTableModel().getRowCount());
			}
		});

		outgoStockUI = new OutgoStockUI(inStock, outStock);
		outgoStockUI.getContentPanel().setVisible(false);
		add(outgoStockUI.getContentPanel());

		outgoStockUI.setChangeStockEventListener(new ChangeStockEventListener() {
			@Override
			public void changeStockAction(StockEvent event) {
				stockController.updateStock(event);
				outgoStockUI.getTotalInStock().setText("" + (outgoStockUI.getStockTableModel().getRowCount() - 1));
				outgoStockUI.getTotalOutStock().setText("" + (outgoStockUI.getOutStockTableModel().getRowCount() + 1));
			}
		});

		// Analytics UI
		analyticsUI = new AnalyticsUI(products, categories, colors, sizes);
		analyticsUI.getContentPanel().setVisible(false);
		add(analyticsUI.getContentPanel());

		analyticsUI.setAnalyticsEventListener(new AnalyticsEventListener() {
			@Override
			public void searchEventAction(AnalyticsEvent event) {
				List<Stock> resultQuery = analyticsController.getProducts(event);
				analyticsUI.setResultQuery(resultQuery);

				analyticsUI.getTableModel().setStockList(resultQuery);
				analyticsUI.getTableModel().fireTableDataChanged();
				analyticsUI.getTotal().setText("" + analyticsUI.getTableModel().getRowCount());
			}
		});

		// Override Window Listener
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				HibernateUtil.getSessionFactory().close();
				dispose();
				System.gc();
			}
		});

		// File Choosers
		exportDatabase = new JFileChooser();
		exportDatabase.setDialogTitle("Резервное копирование данных");
		exportDatabase.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		importDatabase = new JFileChooser();
		importDatabase.setDialogTitle("Восстановление данных");
		importDatabase.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) return true;
				if (f.getName().contains(".db")) return true;
				return false;
			}

			@Override
			public String getDescription() {
				return "khazina.db";
			}
		});

		// Setting Menu Bar
		setJMenuBar(addJMenuBar());

		// Other App Configuration
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(980, 600);
		setVisible(true);
	}

	private JMenuBar addJMenuBar() {
		JMenuBar menu = new JMenuBar();
		menu.setBackground(Color.WHITE);

		// Fiel Menu
		JMenu fileMenu = new JMenu("Файл");

		JMenuItem backupMenuItem = new JMenuItem("Резервное копирование данных");
		backupMenuItem.setBackground(Color.WHITE);
		JMenuItem restoreMenuItem = new JMenuItem("Восстановление данных");
		restoreMenuItem.setBackground(Color.WHITE);
		JMenuItem exitMenuItem = new JMenuItem("Выход из системы");
		exitMenuItem.setBackground(Color.WHITE);

		backupMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (exportDatabase.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					Path source = Paths.get("khazina.db").toAbsolutePath();
					File destination = exportDatabase.getSelectedFile();

					try {
						Files.copy(source, destination.toPath().resolve(source.getFileName()));

					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this, "По техническим причинам не удалось экспортировать" +
								" базу. Ошибка " + e1.getMessage(), "Экспорт Данных", JOptionPane.OK_OPTION);
					}
				}
			}
		});

		restoreMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (importDatabase.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					File source = importDatabase.getSelectedFile();
					Path destination = Paths.get("").toAbsolutePath();

					try {
						HibernateUtil.getSessionFactory().close();
						dispose();
						System.gc();

						Files.copy(source.toPath(), destination.resolve("khazina.db"), StandardCopyOption.REPLACE_EXISTING);

					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this, "По техническим причинам не удалось импортировать" +
								" базу. Ошибка " + e1.getMessage(), "Импорт Данных", JOptionPane.OK_OPTION);
					}
				}
			}
		});

		exitMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WindowListener[] listeners = getWindowListeners();
				
				for (WindowListener listener : listeners) {
					listener.windowClosing(new WindowEvent(MainFrame.this, 0));
				}
			}
		});
		
		fileMenu.add(backupMenuItem);
		fileMenu.add(restoreMenuItem);
		fileMenu.add(exitMenuItem);
		
		menu.add(fileMenu);
		
		// Product Menu
		JMenu productMenu = new JMenu("Товары");

		JMenuItem producConfigurationMenuItem = new JMenuItem("Параметры конфигурирования товара");
		producConfigurationMenuItem.setBackground(Color.WHITE);
		JMenuItem productManagementMenuItem = new JMenuItem("Управление товаром");
		productManagementMenuItem.setBackground(Color.WHITE);
		
		producConfigurationMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				productsUI.getContentPanel().setVisible(false);
				incomeStockUI.getContentPanel().setVisible(false);
				outgoStockUI.getContentPanel().setVisible(false);
				analyticsUI.getContentPanel().setVisible(false);
				productConfigurationUI.getContentPanel().setVisible(true);
			}
		});
		
		productManagementMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				productConfigurationUI.getContentPanel().setVisible(false);
				incomeStockUI.getContentPanel().setVisible(false);
				outgoStockUI.getContentPanel().setVisible(false);
				analyticsUI.getContentPanel().setVisible(false);

				updateProductConfigurationModels();
				productsUI.getTotalProductsLbl().setText("" + productsUI.getTableModel().getRowCount());
				productsUI.getContentPanel().setVisible(true);
			}
		});
		
		productMenu.add(producConfigurationMenuItem);
		productMenu.add(productManagementMenuItem);
		
		menu.add(productMenu);
		
		// Stock Menu
		JMenu stockMenu = new JMenu("Склад");

		JMenuItem inComeMenuItem = new JMenuItem("Приход товаров");
		inComeMenuItem.setBackground(Color.WHITE);
		JMenuItem outGoMenuItem = new JMenuItem("Расход товаров");
		outGoMenuItem.setBackground(Color.WHITE);

		inComeMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				productConfigurationUI.getContentPanel().setVisible(false);
				productsUI.getContentPanel().setVisible(false);
				outgoStockUI.getContentPanel().setVisible(false);
				analyticsUI.getContentPanel().setVisible(false);

				inStock = stockController.getProductsFromStock();
				if (inStock != null) {
					incomeStockUI.setStocks(inStock);
					incomeStockUI.getStockTableModel().setStockList(inStock);
					incomeStockUI.getStockTableModel().fireTableDataChanged();

					incomeStockUI.getProductModel().removeAllElements();
					products = productController.getProducts();
					for (Product product : products) {
						incomeStockUI.getProductModel().addElement(product);
					}
				}

				incomeStockUI.getTotal().setText("" + incomeStockUI.getStockTableModel().getRowCount());
				incomeStockUI.getContentPanel().setVisible(true);
			}
		});

		outGoMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				productConfigurationUI.getContentPanel().setVisible(false);
				productsUI.getContentPanel().setVisible(false);
				incomeStockUI.getContentPanel().setVisible(false);
				analyticsUI.getContentPanel().setVisible(false);

				inStock = stockController.getProductsFromStock();
				if (inStock != null) {
					outgoStockUI.setInStock(inStock);
					outgoStockUI.getStockTableModel().setStockList(inStock);
					outgoStockUI.getStockTableModel().fireTableDataChanged();
				}

				outStock = stockController.getSoldProductsFromStock();
				if (outStock != null) {
					outgoStockUI.setOutStock(outStock);
					outgoStockUI.getOutStockTableModel().setStockList(outStock);
					outgoStockUI.getOutStockTableModel().fireTableDataChanged();
				}

				outgoStockUI.getTotalInStock().setText("" + outgoStockUI.getStockTableModel().getRowCount());
				outgoStockUI.getTotalOutStock().setText("" + outgoStockUI.getOutStockTableModel().getRowCount());
				outgoStockUI.getContentPanel().setVisible(true);
			}
		});

		stockMenu.add(inComeMenuItem);
		stockMenu.add(outGoMenuItem);
		
		menu.add(stockMenu);
		
		// Analytics Menu
		JMenu analyticsMenu = new JMenu("Аналитика");

		JMenuItem reportMenuItem = new JMenuItem("Создание отчетов");
		reportMenuItem.setBackground(Color.WHITE);

		reportMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				productConfigurationUI.getContentPanel().setVisible(false);
				incomeStockUI.getContentPanel().setVisible(false);
				outgoStockUI.getContentPanel().setVisible(false);
				productsUI.getContentPanel().setVisible(false);

				updateAnalyticsUIProductConfigurationModels();
				analyticsUI.getContentPanel().setVisible(true);
			}
		});
		
		analyticsMenu.add(reportMenuItem);
		menu.add(analyticsMenu);
		
		return menu;
	}

	private void updateAnalyticsUIProductConfigurationModels() {
		if (analyticsUI.getCategoryModel().getSize() > 0) {
			analyticsUI.getCategoryModel().removeAllElements();
		}

		ProductCategory emptyCategory = new ProductCategory("Выберите категорию");
		categories = productController.getProductCategoryList();
		if (categories != null) {
			analyticsUI.getCategoryModel().addElement(emptyCategory);
			for (ProductCategory category : categories) {
				analyticsUI.getCategoryModel().addElement(category);
			}
		}

		if (analyticsUI.getColorModel().getSize() > 0) {
			analyticsUI.getColorModel().removeAllElements();
		}

		ProductColor emptyColor = new ProductColor("Выберите цвет");
		colors = productController.getProductColorList();
		if (colors != null) {
			analyticsUI.getColorModel().addElement(emptyColor);
			for (ProductColor color : colors) {
				analyticsUI.getColorModel().addElement(color);
			}
		}

		if (analyticsUI.getSizeModel().getSize() > 0) {
			analyticsUI.getSizeModel().removeAllElements();
		}

		ProductSize emptySize = new ProductSize();
		sizes = productController.getProductSizeList();
		if (sizes != null) {
			analyticsUI.getSizeModel().addElement(emptySize);
			for (ProductSize size : sizes) {
				analyticsUI.getSizeModel().addElement(size);
			}
		}

		if (analyticsUI.getProductModel().getSize() > 0) {
			analyticsUI.getProductModel().removeAllElements();
		}

		Product emptyProduct = new Product("Выберите товар", "");
		products = productController.getProducts();
		if (products != null) {
			analyticsUI.getProductModel().addElement(emptyProduct);
			for (Product product : products) {
				analyticsUI.getProductModel().addElement(product);
			}
		}

		analyticsUI.getTableModel().setStockList(null);
		analyticsUI.getTableModel().fireTableDataChanged();
	}

	private void updateProductConfigurationModels() {
		if (productsUI.getCategoryModel().getSize() > 0) {
			productsUI.getCategoryModel().removeAllElements();
		}

		categories = productController.getProductCategoryList();
		if (categories != null) {
			for (ProductCategory category : categories) {
				productsUI.getCategoryModel().addElement(category);
			}
		}

		if (productsUI.getColorModel().getSize() > 0) {
			productsUI.getColorModel().removeAllElements();
		}

		colors = productController.getProductColorList();
		if (colors != null) {
			for (ProductColor color : colors) {
				productsUI.getColorModel().addElement(color);
			}
		}

		if (productsUI.getSizeModel().getSize() > 0) {
			productsUI.getSizeModel().removeAllElements();
		}

		sizes = productController.getProductSizeList();
		if (sizes != null) {
			for (ProductSize size : sizes) {
				productsUI.getSizeModel().addElement(size);
			}
		}

		products = productController.getProducts();
		if (products != null) {
			productsUI.getTableModel().getProducts().clear();
			for (Product product : products) {
				productsUI.getTableModel().getProducts().add(product);
			}
		}
	}
}
