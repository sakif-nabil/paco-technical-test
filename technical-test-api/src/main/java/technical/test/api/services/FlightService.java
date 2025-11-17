package technical.test.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import technical.test.api.record.FlightRecord;
import technical.test.api.repository.FlightRepository;
import technical.test.api.representation.PageResult;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public Mono<PageResult<FlightRecord>> getAllFlights(String origin, String destination, Double minPrice, Double maxPrice, LocalDate departureDate, String sort, int page) {
        return flightRepository.search(origin, destination,minPrice, maxPrice, departureDate, sort, page);
    }

    public Mono<FlightRecord> createFlight(FlightRecord flightRecord) {
        return flightRepository.save(flightRecord);
    }

    public Mono<FlightRecord> getFlightById(UUID id) {
       return flightRepository.findById(id);
    }
}
