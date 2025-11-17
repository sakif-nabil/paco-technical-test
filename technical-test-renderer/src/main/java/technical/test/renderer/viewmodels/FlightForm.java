package technical.test.renderer.viewmodels;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightForm {
    private String origin;
    private String destination;
    LocalDateTime departureDate;
    LocalDateTime arrivalDate;
    private Double price;
    private String image;

}

