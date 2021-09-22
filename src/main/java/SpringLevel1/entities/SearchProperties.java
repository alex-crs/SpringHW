package SpringLevel1.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SearchProperties {
    @NonNull
    int[] pages;
    @NonNull
    int currentPage;
    @NonNull
    List<Product> productList;
    @NonNull
    long minCost;
    @NonNull
    long maxCost;
    @NonNull
    String sortType;


}
