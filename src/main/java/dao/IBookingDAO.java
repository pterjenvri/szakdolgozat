package dao;

import model.Booking;

import java.util.List;

public interface IBookingDAO {
    public void kolcsonoz(Booking booking);
    public void visszahoz(Booking booking);
    public List<Booking> getBookings();
}
