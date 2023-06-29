package com.surshank.lil.learningspring.business;

import com.surshank.lil.learningspring.data.Guest;
import com.surshank.lil.learningspring.data.GuestRepository;
import com.surshank.lil.learningspring.data.Reservation;
import com.surshank.lil.learningspring.data.ReservationRepository;
import com.surshank.lil.learningspring.data.Room;
import com.surshank.lil.learningspring.data.RoomRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReservationService {
  //@Autowired
  // final -> since, these are singletons
  private final RoomRepository roomRepository;
//  @Autowired
//  public RoomRepository getRoomRepository() {
//    return roomRepository;
//  }
//  @Autowired
//  public void setRoomRepository(RoomRepository roomRepository) {
//    this.roomRepository = roomRepository;
//  }

  private final GuestRepository guestRepository;
  private final ReservationRepository reservationRepository;

  @Autowired
  public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository,
      ReservationRepository reservationRepository) {
    this.roomRepository = roomRepository;
    this.guestRepository = guestRepository;
    this.reservationRepository = reservationRepository;
  }

  public List<RoomReservation> getRoomReservationForDate(Date date){
    Iterable<Room> rooms = this.roomRepository.findAll();
    Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
    rooms.forEach(room -> {
      RoomReservation roomReservation = new RoomReservation();
      roomReservation.setRoomId(room.getId());
      roomReservation.setRoomName(room.getName());
      roomReservation.setRoomNumber(room.getRoomNumber());
      roomReservationMap.put(room.getId(), roomReservation);
    });
    Iterable<Reservation> reservations = this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
    System.out.println("The date is: " + date);
    reservations.forEach(reservation -> {
      RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
      roomReservation.setDate(date);
      Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
      roomReservation.setFirstName(guest.getFirstName());
      roomReservation.setLastName(guest.getLastName());
      roomReservation.setGuestId(guest.getId());
    });
    List<RoomReservation> roomReservations = new ArrayList<>();
    for (Long id: roomReservationMap.keySet()) {
      roomReservations.add(roomReservationMap.get(id));
    }
    return roomReservations;
  }

  public List<Guest> getGuestsService() {
    Iterable<Guest> guests = this.guestRepository.findAll();
    List<Guest> guestList = new ArrayList<>();
    guests.forEach(guest -> guestList.add(guest));
    guestList.sort(new Comparator<Guest>() {
      @Override
      public int compare(Guest o1, Guest o2) {
        if(o1.getLastName().equals(o2.getLastName())){
          return o1.getFirstName().compareTo(o2.getFirstName());
        }
        return o1.getLastName().compareTo(o2.getLastName());
      }
    });
    return guestList;
  }

  public void createGuestService(Guest guest) {
    if (guest == null) {
      throw new RuntimeException("Guest cannot be null");
    }
    this.guestRepository.save(guest);
  }

  public List<Room> getRoomsService() {
    Iterable<Room> rooms = this.roomRepository.findAll();
    List<Room> roomsList = new ArrayList<>();
    rooms.forEach(roomsList::add);
    roomsList.sort(new Comparator<Room>() {
      @Override
      public int compare(Room o1, Room o2) {
        return o1.getRoomNumber().compareTo(o2.getRoomNumber());
      }
    });
    return roomsList;
  }
}
