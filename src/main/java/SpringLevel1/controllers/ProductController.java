package SpringLevel1.controllers;

import SpringLevel1.entities.CartElement;
import SpringLevel1.entities.Product;
import SpringLevel1.entities.SearchContainer;
import SpringLevel1.service.ProductDao;
import SpringLevel1.tools.CartManager;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
public class ProductController {

    Logger logger = Logger.getLogger(ProductController.class);

    @Resource(name = "ProductDao")
    private ProductDao productBase;

    @Autowired
    CartManager cartMgr;

    @RequestMapping("/menuAction")
    public String mainMenu() {
        logger.info("Запрошено меню");
        return "menuAction";
    }

    @RequestMapping("/")
    public String mainMenuAfterAuth(Model model) {
        logger.info("Запрошено меню");

        return "menuAction";
    }

    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    public String addProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "addProduct";
    }

    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    @RequestMapping(value = "/addProduct", method = POST)
    public String addProductResult(@ModelAttribute("product") Product product, Model model) {
        productBase.addProduct(product);
        model.addAttribute("msg", String.format("Product with title %s added to base.", product.getTitle()));
        return "message";
    }

    @RequestMapping("/typedProductList")
    public String typeShowProductList(Model model, @PathParam("minCost") Long minCost,
                                      @PathParam("maxCost") Long maxCost,
                                      @PathParam("sortType") String sortType,
                                      @RequestParam(value = "page", defaultValue = "1") Integer page) {
        long minCostDefault = 0l;
        long maxCostDefault = 999999999;
        Page<Product> pages;

        if (minCost != null) {
            minCostDefault = minCost;
        }
        if (maxCost != null) {
            maxCostDefault = maxCost;
        }
        if ("DESC".equals(sortType)) {
            pages = productBase.findCostBetween(minCostDefault, maxCostDefault, Sort.Direction.DESC, --page);
        } else {
            pages = productBase.findCostBetween(minCostDefault, maxCostDefault, Sort.Direction.ASC, --page);
        }
        model.addAttribute("products", pages.stream().collect(Collectors.toList()));
        model.addAttribute("minCost", minCost);
        model.addAttribute("maxCost", maxCost);
        model.addAttribute("page", page);
        model.addAttribute("ASC", "ASC".equals(sortType));
        model.addAttribute("DESC", "DESC".equals(sortType));
        model.addAttribute("sortType", sortType);
        model.addAttribute("pages", new int[pages.getTotalPages()]);
        return "product-list";
    }

    @RequestMapping("/searchProductForm")
    public String searchProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "searchProductForm";
    }

    @RequestMapping(value = "/searchProductForm", method = POST)
    public String getFindMethod(Model model,
                                @RequestParam("searchElement") String searchElement,
                                @RequestParam("searchType") String searchType) {
        List<Product> result = null;
        if ("id".equals(searchType)) {
            result = new ArrayList<>();
            result.add(productBase.getProductById(Integer.parseInt(searchElement)).get());
        } else {
            result = productBase.findProductByTitle(searchElement);
        }
        if (result!=null) {
            model.addAttribute("products", result);
        } else {
            model.addAttribute("msg", "Product: " + searchElement + " not found.");
        }
        return "products-found";
    }

    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    @RequestMapping("/deleteProductForm")
    public String deleteProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "deleteProductForm";
    }

    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    @RequestMapping("/delete")
    public String getDeleteMethod(Model model, @PathParam("id") Integer id) {
        logger.info(String.format("Удаляется объект с id [%s]", id));
        int result = productBase.deleteProductById(id);
        if (result <= 0) {
            model.addAttribute("msg", "Delete error.");
        } else {
            model.addAttribute("msg", "Delete success.");
        }
        return "message";
    }

    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    @RequestMapping("/deleteResult")
    public String showDeleteResult(@ModelAttribute("product") Product product, Model model) {
        logger.info(String.format("Удаляется объект с именем [%s]", product.getTitle()));
        return "redirect:/delete/" + product.getId();
    }

    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    @RequestMapping("/updateProduct")
    public String updateProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "updateProduct";
    }

    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    @RequestMapping(value = "/updateProduct", method = POST)
    public String updateResult(@ModelAttribute("product") Product product, Model model) {
        int result = productBase.addOrUpdate(product);
        if (result == 0) {
            model.addAttribute("msg", String.format("Product with: %s, cost = %s updated",
                    product.getTitle(),
                    product.getCost()));
        } else if (result == 1) {
            model.addAttribute("msg", String.format("Product with: %s, cost = %s updated",
                    product.getTitle(),
                    product.getCost()));
        } else {
            model.addAttribute("msg", "Error.");
        }
        return "message";
    }

    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
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

    @RequestMapping("/cartList")
    public String showCart(Model model) {
        model.addAttribute("products", cartMgr.getCartList());
        model.addAttribute("totalCost", cartMgr.getTotalCost());
        return "cart";
    }

    @RequestMapping("/addToCart")
    public String addToCart(@PathParam("id") int id, @PathParam("minCost") Long minCost,
                            @PathParam("maxCost") Long maxCost,
                            @PathParam("sortType") String sortType,
                            @RequestParam(value = "page", defaultValue = "1") Integer page, Model model) {
        Optional<Product> product = productBase.getProductById(id);
        product.ifPresent(value -> cartMgr.addToCart(new CartElement().getFromProduct(value)));
        model.addAttribute("page", page + 1);
        model.addAttribute("minCost", minCost);
        model.addAttribute("maxCost", maxCost);
        model.addAttribute("ASC", "ASC".equals(sortType));
        model.addAttribute("DESC", "DESC".equals(sortType));
        model.addAttribute("sortType", sortType);
        return "redirect:/typedProductList";
    }

    @RequestMapping("/incCartElement")
    public String incCartElement(@PathParam("id") int id) {
        cartMgr.addToCart(id);
        return "redirect:/cartList";
    }


    @RequestMapping("/removeFromCart")
    public String removeFromCart(@PathParam("id") int id) {
        cartMgr.deleteFromCart(id);
        return "redirect:/cartList";
    }

}
