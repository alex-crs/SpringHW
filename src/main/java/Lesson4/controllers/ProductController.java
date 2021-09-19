package Lesson4.controllers;

import Lesson4.Launcher;
import Lesson4.entities.Product;
import Lesson4.repositories.ProductDao;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Resource(name = "ProductDao")
    private ProductDao productBase;

    @RequestMapping("/menuAction")
    public String mainMenu() {
        return "menuAction";
    }

    @RequestMapping("/addProduct")
    public String addProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "addProduct";
    }

    @RequestMapping("/showResult")
    public String addProductResult(@ModelAttribute("product") Product product) {
        productBase.addProduct(product);
        return "showResult";
    }

    @RequestMapping("/productList")
    public String showProductList(Model model) {
        model.addAttribute("products", productBase.getAll(0,5,Sort.Direction.DESC));
        return "product-list";
    }

    @RequestMapping("/searchProductForm")
    public String searchProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "searchProductForm";
    }

    @RequestMapping("/maxResult")
    public String searchFilterMaxCost(Model model){
        model.addAttribute("products", productBase.findMax());
        model.addAttribute("action", "Product with Max cost:");
        return "extResult";
    }

    @RequestMapping("/minResult")
    public String searchFilterMinCost(Model model){
        model.addAttribute("products", productBase.findMin());
        model.addAttribute("action", "Product with Min cost:");
        return "extResult";
    }

    @RequestMapping("/maxAndMinCost")
    public String searchFilterMaxAndMinCost(Model model){
        List<Product> maxList = productBase.findMax();
        List<Product> minList = productBase.findMin();
        minList.addAll(maxList);
        model.addAttribute("products", minList);
        model.addAttribute("action", "Product with Max and Min costs:");
        return "extResult";
    }

    @RequestMapping("/searchResult")
    public String showSearchResult(@ModelAttribute("product") Product product, Model model) {
        Product result = productBase.getProductById(product.getId());
        if (result != null) {
            model.addAttribute("product", result);
        } else {
            model.addAttribute("product", product);
            return "error";
        }
        return "searchResult";
    }

    @RequestMapping("/deleteProductForm")
    public String deleteProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "deleteProductForm";
    }

    @RequestMapping("/deleteResult")
    public String showDeleteResult(@ModelAttribute("product") Product product, Model model) {
        int result = productBase.deleteProductById(product.getId());
        model.addAttribute("product", product);
        if (result <= 0) {
            return "error";
        }
        return "deleteResult";
    }

    @RequestMapping("/updateProduct")
    public String updateProductForm(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "updateProduct";
    }
    @RequestMapping("/updateResult")
    public String updateResult(@ModelAttribute("product") Product product){
        productBase.addOrUpdate(product);
        return "updateResult";
    }
}
