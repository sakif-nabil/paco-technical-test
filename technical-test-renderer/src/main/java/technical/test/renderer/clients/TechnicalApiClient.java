package technical.test.renderer.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.properties.TechnicalApiProperties;
import technical.test.renderer.viewmodels.*;

import java.util.UUID;

@Component
@Slf4j
public class TechnicalApiClient {

    private final TechnicalApiProperties technicalApiProperties;
    private final WebClient webClient;

    public TechnicalApiClient(TechnicalApiProperties technicalApiProperties, final WebClient.Builder webClientBuilder) {
        this.technicalApiProperties = technicalApiProperties;
        this.webClient = webClientBuilder.build();
    }

    public Mono<PageResultView<FlightViewModel>> getFlights(SearchForm searchForm ) {

        return webClient
                .get()
                .uri(uriBuilder -> {
                    //uriBuilder = uriBuilder.path(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath());
                    UriBuilder builder = UriComponentsBuilder.fromHttpUrl(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath());

                    if (searchForm.getMinPrice() != null) {
                        builder.queryParam("minPrice", searchForm.getMinPrice());
                    }
                    if (searchForm.getMaxPrice() != null) {
                        builder.queryParam("maxPrice", searchForm.getMaxPrice());
                    }
                    if (searchForm.getDepartureDate() != null) {
                        builder.queryParam("departure", searchForm.getDepartureDate().toLocalDate());
                    }
                    if (searchForm.getOrigin() != null) {
                        builder.queryParam("origin", searchForm.getOrigin());
                    }
                    if (searchForm.getDestination() != null) {
                        builder.queryParam("destination", searchForm.getDestination());
                    }

                    builder.queryParam("page", searchForm.getPage());

                    return builder.build();
                })
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<PageResultView<FlightViewModel>>() {});
    }

    public Mono<FlightViewModel> createFlight(FlightViewModel flightViewModel) {
       return webClient
                .post()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath())
                .bodyValue(flightViewModel)
                .retrieve()
                .bodyToMono(FlightViewModel.class);
    }

    public Mono<FlightViewModel> getFlightDetails(UUID id) {

       return webClient
               .get()
               .uri(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath()+"/"+id)
               .retrieve().bodyToMono(FlightViewModel.class);
    }
}
