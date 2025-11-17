package technical.test.renderer.facades;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import technical.test.renderer.services.FlightService;
import technical.test.renderer.viewmodels.AirportViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;
import technical.test.renderer.viewmodels.PageResultView;
import technical.test.renderer.viewmodels.SearchForm;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class FlightFacade {

    private final FlightService flightService;

    public FlightFacade(FlightService flightService) {
        this.flightService = flightService;
    }

    public Mono<PageResultView<FlightViewModel>> getFlights(SearchForm searchForm) {
        return this.flightService.getFlights(  searchForm);
    }

    public Mono<FlightViewModel> createFlight(String origin, String destination, Double price, LocalDateTime departureDate, LocalDateTime arrivalDate, String image) {

        AirportViewModel airportOrigin= AirportViewModel.builder().iata(origin).build();
        AirportViewModel airportDestination= AirportViewModel.builder().iata(destination).build();

        FlightViewModel flightViewModel =FlightViewModel.builder().origin(airportOrigin)
                                                                .destination(airportDestination)
                                                                .departure(departureDate)
                                                                .price(price)
                                                                .arrival(arrivalDate)
                                                                .image(image)
                                                                .build();

        return this.flightService.createFlight(flightViewModel);
    }

    public Mono<FlightViewModel> getFlightDetails(UUID id) {
        return flightService.getFlightDetails(id);
    }
}
