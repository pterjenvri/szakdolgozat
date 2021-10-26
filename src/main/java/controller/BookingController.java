package controller;

import dao.BookingDAOImpl;
import dao.IBookingDAO;
import model.Booking;

import java.util.List;

public class BookingController {
    private IBookingDAO dao = new BookingDAOImpl();

    private static BookingController instance;

    public static BookingController getInstance()
    {
        if(instance == null)
        {
            instance = new BookingController();
        }
        return instance;
    }

    private BookingController() {}

    public void kolcsonoz(Booking booking)
    {
        dao.kolcsonoz(booking);
    }

    public void visszahoz(Booking booking)
    {
        dao.visszahoz(booking);
    }

    public List<Booking> getBookings()
    {
        return dao.getBookings();
    }
}
