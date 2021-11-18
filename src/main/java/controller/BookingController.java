package controller;

import dao.BorrowingDAOImpl;
import dao.IBorrowingDAO;
import model.Borrowing;

import java.util.List;

public class BookingController {
    private IBorrowingDAO dao = new BorrowingDAOImpl();

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

    public void kolcsonoz(Borrowing borrowing)
    {
        dao.kolcsonoz(borrowing);
    }

    public void visszahoz(Borrowing borrowing)
    {
        dao.visszahoz(borrowing);
    }

    public List<Borrowing> getBorrowings()
    {
        return dao.getBorrowings();
    }
}
