package com.surshank.lil.learningspring.data;

import java.sql.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  public Iterable<Reservation> findReservationByReservationDate(Date date);
//  public Iterable<Reservation> findByDate(Date date) {
//    String queryString = String.format("SELECT RESERVATION_ID FROM RESERVATION_TABLE WHERE DATE=%s", date);
//    return QueryUtils.getQueryString(queryString);
//  }

}
