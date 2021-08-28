package Lesson3.controllers;

import Lesson3.Launcher;
import Lesson3.entites.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Random;

@Controller
@RequestMapping("/products")
public class ProductController {

    @RequestMapping("/menuAction")
    public String mainMenu(){
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
        product.setId(new Random().nextInt(1000));
        Launcher.base.addToBase(product);
        return "showResult";
    }

    @RequestMapping("/productList")
    public String showProductList(Model model) {
        model.addAttribute("products", Launcher.base.getProductList());
        return "product-list";
    }

    @RequestMapping("/searchProductForm")
    public String searchProductForm(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "searchProductForm";
    }

    @RequestMapping("/searchResult")
    public String showSearchResult(@ModelAttribute("product") Product product, Model model){
        Product result = Launcher.base.getProductFromId(product.getId());
        if (result!=null) {
            model.addAttribute("product", Launcher.base.getProductFromId(product.getId()));
        } else {
            model.addAttribute("product", product);
            return "error";
        }
        return "searchResult";
    }
}
