package com.surshank.lil.learningspring.web;

import com.surshank.lil.learningspring.business.ReservationService;
import com.surshank.lil.learningspring.data.Guest;
import com.surshank.lil.learningspring.data.GuestRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/guests")
public class GuestsController {
  // This is one way to do it - but it is generally recommended to write a service:
//  private final GuestRepository guestRepository;
//
//  public GuestsController(GuestRepository guestRepository){
//    this.guestRepository = guestRepository;
//  }
//
//  @RequestMapping(method= RequestMethod.GET)
//  public String getGuests(Model model) {
//    Iterable<Guest> guests = this.guestRepository.findAll();
//    model.addAttribute("guests", guests);
//    return "guests";
//  }
  private final ReservationService reservationService;

  public GuestsController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @RequestMapping(method= RequestMethod.GET)
  public String getGuests(Model model) {
    model.addAttribute("guests", this.reservationService.getGuestsService());
    // If you see a cyclic dependency - rename the below return statement to: return "hotel-guests";
    return "guests";
  }

}
