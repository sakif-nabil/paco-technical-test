package technical.test.api.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.PageResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepositoryCustomImpl implements FlightRepositoryCustom {

    private final ReactiveMongoTemplate mongoTemplate;

    public FlightRepositoryCustomImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mono<PageResult<FlightRecord>> search(String origin,
                                     String destination,
                                     Double minPrice,
                                     Double maxPrice,
                                     LocalDate departureDate,
                                     String sort,
                                     int page) {

        List<Criteria> criteriaList = new ArrayList<>();

        if (origin != null && !origin.isEmpty()) {
            criteriaList.add(Criteria.where("origin").regex("^" + origin + "$", "i"));
        }

        if (destination != null && !destination.isEmpty()) {
            criteriaList.add(Criteria.where("destination").regex("^" + destination + "$", "i"));
        }

        if (minPrice != null) {
            criteriaList.add(Criteria.where("price").gte(minPrice));
        }

        if (maxPrice != null) {
            criteriaList.add(Criteria.where("price").lte(maxPrice));
        }

        if (departureDate != null) {
            LocalDateTime start = departureDate.atStartOfDay();
            LocalDateTime end = departureDate.atTime(LocalTime.MAX);
            criteriaList.add(Criteria.where("departure").gte(start).lte(end));
        }

        Criteria criteria = new Criteria();
        if (!criteriaList.isEmpty()) {
            criteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
        }

        Query query = new Query(criteria);

        Sort sortCriteria = "desc".equalsIgnoreCase(sort)
                ? Sort.by("price").descending()
                : Sort.by("price").ascending();

        query.with(sortCriteria);

        int pageSize = 6;
        query.skip((long) page * pageSize).limit(pageSize);



        Flux<FlightRecord> flights = mongoTemplate.find(query, FlightRecord.class);

        Mono<Long> totalCount = mongoTemplate.count(new Query(criteria), FlightRecord.class);

        return totalCount.flatMap(count ->
                flights.collectList()
                        .map(list -> {
                            int totalPages = (int) Math.ceil((double) count / pageSize);
                            return new PageResult<>(list, page, totalPages, count);
                        })
        );
    }
}

