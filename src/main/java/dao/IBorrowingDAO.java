package dao;

import model.Borrowing;

import java.util.List;

public interface IBorrowingDAO {
    public void kolcsonoz(Borrowing booking);
    public void visszahoz(Borrowing booking);
    public List<Borrowing> getBorrowings();
}
