package Lesson4.controllers;

import Lesson4.entities.Product;
import Lesson4.repositories.ProductDao;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    Logger logger = Logger.getLogger(ProductController.class);

    @Resource(name = "ProductDao")
    private ProductDao productBase;

    @RequestMapping("/menuAction")
    public String mainMenu() {
        logger.info("Запрошено меню");
        return "menuAction";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    public String addProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "addProduct";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProductResult(@ModelAttribute("product") Product product, Model model) {
        productBase.addProduct(product);
        model.addAttribute("msg", String.format("Product with title %s added to base.", product.getTitle()));
        return "message";
    }

    @RequestMapping("/productList")
    public String showProductList(Model model) {
        model.addAttribute("products", productBase.getAll(0, 5, Sort.Direction.ASC));
        model.addAttribute("page", 0);
        model.addAttribute("sortType", "ASC");
        return "product-list";
    }

    @RequestMapping("/typedProductList")
    public String typeShowProductList(Model model, @PathParam("minCost") Long minCost, @PathParam("maxCost") Long maxCost, @PathParam("sortType") String sortType, @PathParam("page") Integer page) {
        long minCostDefault = 0l;
        long maxCostDefault = 999999999;
        Page<Product> pages;

        if (minCost != null) {
            minCostDefault = minCost;
        }
        if (maxCost != null) {
            maxCostDefault = maxCost;
        }
        if (page < 0) {
            page = 0;
        }
        if ("DESC".equals(sortType)) {
            pages = productBase.findCostBetween(minCostDefault, maxCostDefault, Sort.Direction.DESC, page);
            System.out.println(pages.getTotalPages());
            model.addAttribute("products", pages.stream().collect(Collectors.toList()));
        } else {
            pages = productBase.findCostBetween(minCostDefault, maxCostDefault, Sort.Direction.ASC, page);
            System.out.println(pages.getTotalPages());
            model.addAttribute("products", pages.stream().collect(Collectors.toList()));
        }
        model.addAttribute("minCost", minCost);
        model.addAttribute("maxCost", maxCost);
        model.addAttribute("page", page);
        model.addAttribute("pages", new int[pages.getTotalPages()]);
        return "product-list";
    }

    @RequestMapping("/searchProductForm")
    public String searchProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "searchProductForm";
    }

    @RequestMapping("/search/{id}")
    public String getFindMethod(Model model, @PathVariable(value = "id") Integer id) {
        Product result = productBase.getProductById(id);
        if (result != null) {
            model.addAttribute("mess", String.format("Product with id=[%s] found:<br><hr> %s, cost = %s<hr>",
                    result.getId(),
                    result.getTitle(),
                    result.getCost()));
        } else {
            model.addAttribute("msg", "Product with id=" + id + " not found.");
        }
        return "message";

    }

    @RequestMapping("/searchResult")
    public String showSearchResult(@ModelAttribute("product") Product product, Model model) {
        return "redirect:search/" + product.getId();
    }

    @RequestMapping("/maxResult")
    public String searchFilterMaxCost(Model model) {
        model.addAttribute("products", productBase.findMax());
        model.addAttribute("action", "Product with Max cost:");
        return "extResult";
    }

    @RequestMapping("/minResult")
    public String searchFilterMinCost(Model model) {
        model.addAttribute("products", productBase.findMin());
        model.addAttribute("action", "Product with Min cost:");
        return "extResult";
    }

    @RequestMapping("/maxAndMinCost")
    public String searchFilterMaxAndMinCost(Model model) {
        List<Product> maxList = productBase.findMax();
        List<Product> minList = productBase.findMin();
        minList.addAll(maxList);
        model.addAttribute("products", minList);
        model.addAttribute("action", "Product with Max and Min costs:");
        return "extResult";
    }

    @RequestMapping("/deleteProductForm")
    public String deleteProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "deleteProductForm";
    }

    @RequestMapping("/delete/{id}")
    public String getDeleteMethod(Model model, @PathVariable(value = "id") Integer id) {
        logger.info(String.format("Удаляется объект с id [%s]", id));
        int result = productBase.deleteProductById(id);
        if (result <= 0) {
            model.addAttribute("msg", "Delete error.");
        } else {
            model.addAttribute("msg", "Delete success.");
        }
        return "message";
    }

    @RequestMapping("/deleteResult")
    public String showDeleteResult(@ModelAttribute("product") Product product, Model model) {
        logger.info(String.format("Удаляется объект с именем [%s]", product.getTitle()));
        return "redirect:/products/delete/" + product.getId();
    }

    @RequestMapping("/updateProduct")
    public String updateProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "updateProduct";
    }

    @RequestMapping("/updateResult")
    public String updateResult(@ModelAttribute("product") Product product) {
        productBase.addOrUpdate(product);
        return "updateResult";
    }

    @RequestMapping("/loadFromFile")
    public String loadDataFromFile(Model model) {
        int result = productBase.loadDataFromFile();
        if (result > 0) {
            model.addAttribute("msg", "Success. Operation complete. Lines read: " + result);
        } else {
            model.addAttribute("msg", "Critical error. See server log");
        }
        return "message";
    }

}
