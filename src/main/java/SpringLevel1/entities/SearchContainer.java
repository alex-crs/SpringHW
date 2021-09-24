package SpringLevel1.entities;

import SpringLevel1.service.ProductDao;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor

public class SearchContainer {

    int[] pages;

    int currentPage;

    Page<Product> products;

    long minCost;

    long maxCost;


    String sortType;

    //виды сортировки
    boolean costSort;

    public SearchContainer getProducts(ProductDao productBase) {
        if (minCost <= 0) {
            minCost = 0;
        }
        if (maxCost <= 0) {
            maxCost = 999999999;
        }
        currentPage = 1;
        if ("DESC".equals(sortType)) {
            products = productBase.findCostBetween(minCost,
                    maxCost, Sort.Direction.DESC, --currentPage);
        } else {
            products = productBase.findCostBetween(minCost,
                    maxCost, Sort.Direction.ASC, --currentPage);
        }
        pages = new int[products.getTotalPages()];
        return this;
    }
}
