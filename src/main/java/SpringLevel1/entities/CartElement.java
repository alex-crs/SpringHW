package SpringLevel1.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartElement {
    int id;
    String title;
    long cost;
    long totalElementCost;
    int count;

    public void addElement() {
        count++;
        totalElementCost = totalElementCost + cost;
    }

    public void removeElement(){
        count--;
        totalElementCost = totalElementCost - cost;
    }

    public CartElement getFromProduct(Product product) {
        setId(product.getId());
        setTitle(product.getTitle());
        setCost(product.getCost());
        count = 1;
        totalElementCost = totalElementCost + cost;
        return this;
    }
}
