package technical.test.api.repository;

import reactor.core.publisher.Mono;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.PageResult;

import java.time.LocalDate;

public interface FlightRepositoryCustom {
    Mono<PageResult<FlightRecord>> search(
            String origin,
            String destination,
            Double minPrice,
            Double maxPrice,
            LocalDate departureDate,
            String sort,
            int page
    );
}

