package technical.test.renderer.viewmodels;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchForm {
        private String origin;
        private String destination;
        LocalDateTime departureDate;
        private Double minPrice;
        private Double maxPrice;
        private int page;

}
