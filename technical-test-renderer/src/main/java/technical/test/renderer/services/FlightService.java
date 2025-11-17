package technical.test.renderer.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import technical.test.renderer.clients.TechnicalApiClient;
import technical.test.renderer.viewmodels.FlightViewModel;
import technical.test.renderer.viewmodels.PageResultView;
import technical.test.renderer.viewmodels.SearchForm;

import java.util.UUID;

@Service
public class FlightService {
    private final TechnicalApiClient technicalApiClient;

    public FlightService(TechnicalApiClient technicalApiClient) {
        this.technicalApiClient = technicalApiClient;
    }

    public Mono<PageResultView<FlightViewModel>> getFlights(SearchForm searchForm) {
        return this.technicalApiClient.getFlights(searchForm);
    }

    public Mono<FlightViewModel> createFlight(FlightViewModel flightViewModel) {
        return this.technicalApiClient.createFlight(flightViewModel);
    }

    public Mono<FlightViewModel> getFlightDetails(UUID id) {
        return technicalApiClient.getFlightDetails(id);
    }
}
