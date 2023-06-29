package com.surshank.lil.learningspring.web;

import com.surshank.lil.learningspring.business.ReservationService;
import com.surshank.lil.learningspring.business.RoomReservation;
import com.surshank.lil.learningspring.util.DateUtils;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/reservations")
public class RoomReservationController {
  private final DateUtils dateUtils;
  private final ReservationService reservationService;

  public RoomReservationController(DateUtils dateUtils, ReservationService reservationService) {
    this.reservationService = reservationService;
    this.dateUtils = dateUtils;
  }

  @RequestMapping(method=RequestMethod.GET)
  public String getReservations(@RequestParam(value="date", required=false) String dateString, Model model) {
    Date date = this.dateUtils.createDateFromDateString(dateString);
    List<RoomReservation> roomReservations = this.reservationService.getRoomReservationForDate(date);
    model.addAttribute("roomReservations", roomReservations);
    return "roomres";
  }
}
