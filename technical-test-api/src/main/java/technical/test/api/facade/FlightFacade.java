package technical.test.api.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.mapper.AirportMapper;
import technical.test.api.mapper.FlightMapper;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.representation.PageResult;
import technical.test.api.services.AirportService;
import technical.test.api.services.FlightService;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FlightFacade {
    private final FlightService flightService;
    private final AirportService airportService;
    private final FlightMapper flightMapper;
    private final AirportMapper airportMapper;

    public Mono<PageResult<FlightRepresentation>> getAllFlights( String origin, String destination,Double minPrice, Double maxPrice, LocalDate departureDate,
                                                    String sort, int page) {

        return flightService.getAllFlights(origin, destination, minPrice, maxPrice, departureDate, sort, page)
                .flatMap(pageResult -> {
                    //
                    return Flux.fromIterable(pageResult.getContent())
                            .flatMap(flightRecord -> airportService.findByIataCode(flightRecord.getOrigin())
                                    .zipWith(airportService.findByIataCode(flightRecord.getDestination()))
                                    .map(tuple -> {
                                        FlightRepresentation fr = flightMapper.convert(flightRecord);
                                        fr.setOrigin(airportMapper.convert(tuple.getT1()));
                                        fr.setDestination(airportMapper.convert(tuple.getT2()));
                                        return fr;
                                    }))
                            .collectList()
                            .map(list -> new PageResult<>(list, pageResult.getCurrentPage(), pageResult.getTotalPages(), pageResult.getTotalItems()));
                });

    }

    public Mono<FlightRepresentation> createFlight(FlightRepresentation flightRepresentation) {

        FlightRecord flightRecord = this.flightMapper.convert(flightRepresentation);

        flightRecord.setId(UUID.randomUUID());

        Mono<FlightRecord> flightRecordSaved= flightService.createFlight(flightRecord);

        return flightRecordSaved.map(savedRecord -> this.flightMapper.convert(savedRecord));

    }

    public Mono<FlightRepresentation> getFlightById(UUID id) {
        return flightService.getFlightById(id)
                .flatMap(flightRecord ->
                        airportService.findByIataCode(flightRecord.getOrigin())
                                .zipWith(airportService.findByIataCode(flightRecord.getDestination()))
                                .map(tuple -> {
                                    FlightRepresentation fr = flightMapper.convert(flightRecord);
                                    fr.setOrigin(airportMapper.convert(tuple.getT1()));
                                    fr.setDestination(airportMapper.convert(tuple.getT2()));
                                    return fr;
                                })
                );
    }
}
