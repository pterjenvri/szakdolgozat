package dao;

import com.tinkerpop.blueprints.Vertex;
import model.Book;
import oracle.pg.nosql.OraclePropertyGraph;
import util.GraphConfig;
import util.Type;

import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements IBookDAO {
    private static OraclePropertyGraph opg;

    public void openGraph()
    {
        try
        {
            opg = OraclePropertyGraph.getInstance(GraphConfig.cfg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeGraph()
    {
        opg.close();
    }

    @Override
    public void add(Book book) {
        openGraph();
        Vertex v1 = (Vertex) opg.addVertex(book.getISBN());
        v1.setProperty("ISBN",book.getISBN());
        v1.setProperty("title",book.getTitle());
        v1.setProperty("tipus", Type.BOOK);
        System.out.println(book.getTitle() + " ISBN: " + book.getISBN() + " elmentodott az adatbazisba(letrejott a node)");
        closeGraph();
    }

    @Override
    public List<Book> getBooks() {
        openGraph();
        List<Book> ret = new ArrayList<>();
        for (Vertex next : opg.getVertices("ISBN")) {
            Book book = new Book(next.getProperty("title").toString(), next.getProperty("ISBN").toString());
            ret.add(book);
        }
        closeGraph();
        return ret;
    }
}
