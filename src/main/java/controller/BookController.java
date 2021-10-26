package controller;

import dao.BookDAOImpl;
import dao.IBookDAO;
import model.Book;

import java.util.List;

public class BookController {
    private IBookDAO dao = new BookDAOImpl();

    private static BookController instance;

    public static BookController getInstance()
    {
        if(instance == null)
        {
            instance = new BookController();
        }
        return instance;
    }

    private BookController() {}

    public void add(Book book)
    {
        dao.add(book);
    }

    public List<Book> getBooks()
    {
        return dao.getBooks();
    }
}
