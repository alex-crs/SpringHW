package Lesson4.repositories;

import Lesson4.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductBase {
    List<Product> productList;

    public ProductBase() {
        productList = new ArrayList<>();
    }

    public Product getProductFromId(int id) {
        for (Product p:productList){
            if (p.getId() == id){
                return p;
            }
        }
        return null;
    }

    public int getProductId(String productName) {
        return productList.indexOf(productName);
    }

    public void addToBase(Product product) {
        this.productList.add(product);
    }

    public Product[] getProductList() {
        return productList.toArray(new Product[0]);
    }
}
