package Lesson3.repositories;

import Lesson3.entites.Product;

import java.util.ArrayList;
import java.util.Collections;

public class ProductBase {
    ArrayList<Product> productList;

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
