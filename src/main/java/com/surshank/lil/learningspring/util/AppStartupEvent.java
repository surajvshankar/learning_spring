package com.surshank.lil.learningspring.util;

import com.surshank.lil.learningspring.business.ReservationService;
import com.surshank.lil.learningspring.business.RoomReservation;
import com.surshank.lil.learningspring.data.Reservation;
import com.surshank.lil.learningspring.data.ReservationRepository;
import com.surshank.lil.learningspring.data.Room;
import com.surshank.lil.learningspring.data.RoomRepository;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
  @Autowired
  private final RoomRepository roomRepository;
  @Autowired
  private final ReservationRepository reservationRepository;
  private final ReservationService reservationService;

  public AppStartupEvent(RoomRepository roomRepository, ReservationRepository reservationRepository, ReservationService reservationService) {
    this.roomRepository = roomRepository;
    this.reservationRepository = reservationRepository;
    this.reservationService = reservationService;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    Iterable<Room> rooms = this.roomRepository.findAll();
    rooms.forEach(System.out::println);

    Date date = new Date(2022-1900, 0, 1);
    //Iterable<Reservation> reservations = this.reservationRepository.findReservationByReservationDate(date);
    List<RoomReservation> reservations = this.reservationService.getRoomReservationForDate(date);
    reservations.forEach(System.out::println);
  }
}
