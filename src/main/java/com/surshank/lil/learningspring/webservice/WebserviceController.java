package com.surshank.lil.learningspring.webservice;

import com.surshank.lil.learningspring.business.ReservationService;
import com.surshank.lil.learningspring.business.RoomReservation;
import com.surshank.lil.learningspring.data.Guest;
import com.surshank.lil.learningspring.data.Room;
import com.surshank.lil.learningspring.util.DateUtils;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class WebserviceController {
  private final DateUtils dateUtils;
  private final ReservationService reservationService;

  public WebserviceController(DateUtils dateUtils, ReservationService reservationService) {
    this.dateUtils = dateUtils;
    this.reservationService = reservationService;
  }

  @RequestMapping(value = "/reservations", method = RequestMethod.GET)
  public List<RoomReservation> getReservations(@RequestParam(value="date", required=false)String dateString) {
    Date date = this.dateUtils.createDateFromDateString(dateString);
    return this.reservationService.getRoomReservationForDate(date);
  }

  @RequestMapping(value = "/guests", method = RequestMethod.GET)
  public List<Guest> getGuests() {
    return this.reservationService.getGuestsService();
  }

  // curl -X POST http://localhost:8080/api/guests -H "Content-Type: application/json" -d '{"lastName": "shankar",
  // "firstName": "suraj", "emailAddress": "surajshankar@dev.null", "address": "earth", "country": "United States",
  // "state": "CA", "phoneNumber": "222-222-2222"}'

  @PostMapping("/guests")
  @ResponseStatus(HttpStatus.CREATED)
  public void createGuest(@RequestBody(required = true)Guest guest) {
    this.reservationService.createGuestService(guest);
  }

  @GetMapping("/rooms")
  public List<Room> getRooms() {
    return this.reservationService.getRoomsService();
  }
}
