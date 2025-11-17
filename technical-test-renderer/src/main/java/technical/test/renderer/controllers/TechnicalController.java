package technical.test.renderer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.FlightForm;
import technical.test.renderer.viewmodels.SearchForm;

import java.util.UUID;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TechnicalController {

    @Autowired
    private FlightFacade flightFacade;

    @GetMapping
    public Mono<String> getMarketPlaceReturnCouponPage(final Model model, @ModelAttribute SearchForm searchForm) {
        return flightFacade.getFlights(searchForm)
                .doOnNext(page -> {
                    model.addAttribute("flights", page.getContent());
                    model.addAttribute("currentPage", page.getCurrentPage());
                    model.addAttribute("totalPages", page.getTotalPages());
                    model.addAttribute("totalItems", page.getTotalItems());
                })
                .thenReturn("pages/index");
    }

    @GetMapping("/addView")
    public String showAddFlightPage() {
        return "pages/add-flight";
    }


    @PostMapping("/add")
    public Mono<String> addFlight(@ModelAttribute FlightForm flightForm) {

        System.out.println("Origin = " + flightForm.getOrigin());
        return flightFacade.createFlight(flightForm.getOrigin(), flightForm.getDestination(), flightForm.getPrice()
                 ,flightForm.getDepartureDate(), flightForm.getArrivalDate(),flightForm.getImage()).
                then(Mono.just("redirect:/"));

    }

    @GetMapping("/details")
    public Mono<String> getFlightDetails(final Model model,@RequestParam UUID id){
        return flightFacade.getFlightDetails(id)
                .doOnNext(flightViewModel ->
                        model.addAttribute("flight",flightViewModel )
                )
                .thenReturn("pages/flight-details");

    }
}
