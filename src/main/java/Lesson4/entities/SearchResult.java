package Lesson4.entities;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.List;

@Component
public class SearchResult {
    int[] pages;
    int currentPage;
    List<Product> productList;
    long minCost;
    long maxCost;
    String sortType;

    public SearchResult() {
    }

    public SearchResult(int[] pages, int currentPage, List<Product> productList, long minCost, long maxCost, String sortType) {
        this.pages = pages;
        this.currentPage = currentPage;
        this.productList = productList;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.sortType = sortType;
    }

    public int[] getPages() {
        return pages;
    }

    public void setPages(int[] pages) {
        this.pages = pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public long getMinCost() {
        return minCost;
    }

    public void setMinCost(long minCost) {
        this.minCost = minCost;
    }

    public long getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(long maxCost) {
        this.maxCost = maxCost;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
