package SpringLevel1.controllers;

import SpringLevel1.entities.Product;
import SpringLevel1.entities.SearchProperties;
import SpringLevel1.errors.Errors;
import SpringLevel1.errors.ResourceNotFoundException;
import SpringLevel1.service.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/rest")
public class RestProductController {
    @Resource(name = "ProductDao")
    private ProductDao productBase;

    @GetMapping
    public SearchProperties typeShowProductList(@PathParam("minCost") Long minCost,
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
        return new SearchProperties(
                new int[pages.getTotalPages()],
                page,
                pages.stream().collect(Collectors.toList()),
                minCostDefault,
                maxCostDefault,
                sortType);
    }

    @GetMapping(value = "/{id}")
    public Product findById(@PathVariable("id") Integer id) {
        return productBase.getProductById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id=[%s] not found", id)));
    }

    @PutMapping
    public ResponseEntity<?> addOrUpdateProduct(@ModelAttribute("product") Product product) {
        List<String> errorsList = new ArrayList<>();
        product.setId(0); //исключаем возможность передачи ID на сервер
        if (product.getTitle().length() < 3) {
            errorsList.add("Error: Title length < 3");
        }
        if (product.getCost() < 0) {
            errorsList.add("Error: Cost<0");
        }
        if (errorsList.size() > 0) {
            return new ResponseEntity<>(new Errors(HttpStatus.BAD_REQUEST.value(), errorsList), HttpStatus.BAD_REQUEST);
        } else {
            productBase.addOrUpdate(product);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@PathParam("id") Integer id) {
        if (productBase.deleteById(id) > 0) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new ResourceNotFoundException(String.format("Product with id=[%s] not found", id));
        }
    }


}
