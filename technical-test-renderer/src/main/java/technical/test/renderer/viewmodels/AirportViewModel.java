package technical.test.renderer.viewmodels;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirportViewModel {
    private String iata;
    private String name;
    private String country;
}
