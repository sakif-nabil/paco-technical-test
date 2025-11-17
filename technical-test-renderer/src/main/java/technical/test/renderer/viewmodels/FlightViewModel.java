package technical.test.renderer.viewmodels;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class FlightViewModel {
    private UUID id;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private double price;
    private String image;
    private AirportViewModel origin;
    private AirportViewModel destination;
}
