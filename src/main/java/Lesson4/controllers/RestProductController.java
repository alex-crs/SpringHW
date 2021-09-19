package Lesson4.controllers;

import Lesson4.entities.Product;
import Lesson4.entities.SearchResult;
import Lesson4.errors.ResourceNotFoundException;
import Lesson4.repositories.ProductDao;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/rest")
public class RestProductController {
    @Resource(name = "ProductDao")
    private ProductDao productBase;

    @GetMapping
    public SearchResult typeShowProductList(@PathParam("minCost") Long minCost,
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
        return new SearchResult(
                new int[pages.getTotalPages()],
                page,
                pages.stream().collect(Collectors.toList()),
                minCostDefault,
                maxCostDefault,
                sortType);
    }

    @GetMapping(value = "/{id}")
    public Product findById(Model model, @PathVariable("id") Integer id){
        return productBase.getProductById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id=[%s] not found", id)));
    }

    @PutMapping
    public ResponseEntity<?> addOrUpdateProduct(@ModelAttribute("product") Product product) {
        productBase.addOrUpdate(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@PathParam("id") Integer id) {
        return new ResponseEntity<>(productBase.deleteById(id) > 0 ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST);
    }


}
