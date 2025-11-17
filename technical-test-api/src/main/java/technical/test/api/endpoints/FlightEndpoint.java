package technical.test.api.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import technical.test.api.facade.FlightFacade;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.representation.PageResult;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightEndpoint {
    private final FlightFacade flightFacade;

    @GetMapping
    public Mono<PageResult<FlightRepresentation>> getAllFlights(@RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
                                                                @RequestParam(required = false) String origin,
                                                                @RequestParam(required = false) String destination,
                                                                @RequestParam(required = false) Double minPrice,
                                                                @RequestParam(required = false) Double maxPrice,
                                                                @RequestParam(required = false) LocalDate departureDate,
                                                                @RequestParam(required = false, defaultValue = "0") int page) {

        return flightFacade.getAllFlights( origin, destination,minPrice, maxPrice, departureDate, sort, page);
    }

    @PostMapping
    public Mono<FlightRepresentation> createFlight(@RequestBody FlightRepresentation flightRepresentation) {
        return flightFacade.createFlight(flightRepresentation);
    }

    @GetMapping("/{id}")
    public Mono<FlightRepresentation> getFlightById(@PathVariable UUID id ){
       return flightFacade.getFlightById(id);
    }



}
