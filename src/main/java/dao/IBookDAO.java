package dao;

import model.Book;

import java.util.List;

public interface IBookDAO {
    public void add(Book book);
    public List<Book> getBooks();
}
