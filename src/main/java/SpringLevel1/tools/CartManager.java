package SpringLevel1.tools;

import SpringLevel1.entities.CartElement;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class CartManager {
    List<CartElement> cartList;
    long totalCost;

    @PostConstruct
    public void init() {
        cartList = new ArrayList<>();
        totalCost = 0;
    }

    public void addToCart(CartElement ce) {
        int indexOfElement = findInCart(ce.getId());
        if (cartList != null && indexOfElement >= 0) {
            cartList.get(indexOfElement).addElement();
        } else {
            cartList.add(ce);
        }
        totalCost = 0;
        totalCostCalc();
    }

    public void addToCart(int id) {
        int indexOfElement = findInCart(id);
        if (cartList != null && indexOfElement >= 0) {
            cartList.get(indexOfElement).addElement();
            totalCost = 0;
            totalCostCalc();
        }
    }

    private int findInCart(int cartElementId) {
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getId() == cartElementId) {
                return i;
            }
        }
        return -1;
    }

    private void totalCostCalc() {
        for (CartElement ce : cartList) {
            totalCost = totalCost + ce.getTotalElementCost();
        }
    }

    public void deleteFromCart(int id) {
        int indexOfElement = findInCart(id);
        if (cartList != null && indexOfElement >= 0 && cartList.get(indexOfElement).getCount() > 1) {
            cartList.get(indexOfElement).removeElement();
        } else {
            cartList.remove(indexOfElement);
        }
        totalCost = 0;
        totalCostCalc();
    }
}
