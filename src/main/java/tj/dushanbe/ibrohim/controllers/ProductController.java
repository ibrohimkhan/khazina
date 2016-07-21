package tj.dushanbe.ibrohim.controllers;

import tj.dushanbe.ibrohim.events.ProductConfigurationEvent;
import tj.dushanbe.ibrohim.events.ProductEvent;
import tj.dushanbe.ibrohim.models.Product;
import tj.dushanbe.ibrohim.models.ProductCategory;
import tj.dushanbe.ibrohim.models.ProductColor;
import tj.dushanbe.ibrohim.models.ProductSize;
import tj.dushanbe.ibrohim.services.ProductServices;

import java.util.List;

/**
 * Created by ibrohim on 1/21/2015.
 */
public class ProductController {
    private List<ProductCategory> productCategoryList;
    private List<ProductColor> productColorList;
    private List<ProductSize> productSizeList;
    private List<Product> products;

    // List of Products and Configs
    public List<Product> getProducts() {
        products = ProductServices.getAllProducts();
        return products;
    }

    public List<ProductSize> getProductSizeList() {
        productSizeList = ProductServices.getAllProductSize();
        return productSizeList;
    }

    public List<ProductColor> getProductColorList() {
        productColorList = ProductServices.getAllProductColors();
        return productColorList;
    }

    public List<ProductCategory> getProductCategoryList() {
        productCategoryList = ProductServices.getAllProductCategories();
        return productCategoryList;
    }

    // Products
    public Long addProduct(ProductEvent productEvent) {
        Long id = ProductServices.addProduct(productEvent.getProduct());
        return id;
    }

    public void updateProduct(ProductEvent productEvent) {
        ProductServices.updateProduct(productEvent.getProduct());
    }

    public void deleteProduct(ProductEvent productEvent) {
        ProductServices.deleteProduct(productEvent.getProduct());
    }

    // Product Category Configuration
    public Long addProductCategory(ProductConfigurationEvent categoryEvent) {
        Long id = ProductServices.addProductCategory(categoryEvent.getProductCategory());
        return id;
    }

    public void updateProductCategory(ProductConfigurationEvent categoryEvent) {
        ProductServices.updateProductCategory(categoryEvent.getProductCategory());
    }

    public void deleteProductCategory(ProductConfigurationEvent categoryEvent) {
        ProductServices.deleteProductCategory(categoryEvent.getProductCategory());
    }

    // Product Color Configuration
    public Long addProductColor(ProductConfigurationEvent colorEvent) {
        Long id = ProductServices.addProductColor(colorEvent.getProductColor());
        return id;
    }

    public void updateProductColor(ProductConfigurationEvent colorEvent) {
        ProductServices.updateProductColor(colorEvent.getProductColor());
    }

    public void deleteProductColor(ProductConfigurationEvent colorEvent) {
        ProductServices.deleteProductColor(colorEvent.getProductColor());
    }

    // Product Size Configuration
    public Long addProductSize(ProductConfigurationEvent sizeEvent) {
        Long id = ProductServices.addProductSize(sizeEvent.getProductSize());
        return id;
    }

    public void updateProductSize(ProductConfigurationEvent sizeEvent) {
        ProductServices.updateProductSize(sizeEvent.getProductSize());
    }

    public void deleteProductSize(ProductConfigurationEvent sizeEvent) {
        ProductServices.deleteProductSize(sizeEvent.getProductSize());
    }
}
