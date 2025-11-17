package technical.test.api.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import technical.test.api.record.FlightRecord;

import java.util.UUID;

@Repository
public interface FlightRepository extends ReactiveMongoRepository<FlightRecord, UUID>, FlightRepositoryCustom  {

    Flux<FlightRecord> findByOriginIgnoreCase(String origin);

    Flux<FlightRecord> findByDestinationIgnoreCase(String destination);

    Flux<FlightRecord> findByPriceLessThanEqual(double price);

    Flux<FlightRecord> findByOriginAndDestinationAllIgnoreCase(String origin, String destination);
}
