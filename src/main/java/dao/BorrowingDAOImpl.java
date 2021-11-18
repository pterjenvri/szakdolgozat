package dao;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import model.Borrowing;
import oracle.pg.nosql.OraclePropertyGraph;
import oracle.pgql.lang.PgqlException;
import oracle.pgx.api.*;
import util.GraphConfig;
import util.Type;

import java.util.ArrayList;
import java.util.List;

public class BorrowingDAOImpl implements IBorrowingDAO {
    private static PgxGraph graph;
    private static OraclePropertyGraph opg;
    private static PgxSession session;

    public void openGraph()
    {
        try
        {
            opg = OraclePropertyGraph.getInstance(GraphConfig.cfg);
            session = Pgx.createSession("session-id-1");
            graph = session.readGraphWithProperties(opg.getConfig());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeGraph()
    {
        graph.close();
        session.close();
        opg.close();
    }

    @Override
    public void kolcsonoz(Borrowing borrowing) {
        openGraph();
        Vertex userVertex = null;
        Vertex bookVertex = null;
        try {
            PgxPreparedStatement userStatement = graph.preparePgql("SELECT u FROM users_graph MATCH (u:?) WHERE u.olvasojegySzam = ?");
            userStatement.setString(1, Type.USER);
            userStatement.setString(2, borrowing.getOlvasojegySzam());
            PgqlResultSet userRs = userStatement.executeQuery();
            for(PgxResult r : userRs)
            {
                userVertex = opg.getVertex(r.getVertex(1).getId());
            }
            PgxPreparedStatement bookStatement = graph.preparePgql("SELECT u FROM users_graph MATCH (u:?) WHERE u.ISBN = ?");
            bookStatement.setString(1, Type.BOOK);
            bookStatement.setString(2, borrowing.getISBN());
            PgqlResultSet bookRs = bookStatement.executeQuery();
            for(PgxResult r : bookRs)
            {
                bookVertex = opg.getVertex(r.getVertex(1).getId());
            }
        } catch (PgqlException e) {
            e.printStackTrace();
        }
        Edge e1 = opg.addEdge(borrowing.getOlvasojegySzam() + borrowing.getISBN(),userVertex,bookVertex,"borrows");
        e1.setProperty("olvasojegySzam", borrowing.getOlvasojegySzam());
        e1.setProperty("ISBN", borrowing.getISBN());
        System.out.println("A " + borrowing.getOlvasojegySzam() + " szammal rendelkezo user kikolcsonozte a " + borrowing.getISBN() + " ISBN-nel rendelkezo konyvet(letrejott koztuk egy el)");
        closeGraph();
    }

    @Override
    public void visszahoz(Borrowing borrowing) {
        openGraph();
        Edge bookingEdge = null;
        try {
            PgxPreparedStatement bookingStatement = graph.preparePgql("SELECT e FROM users_graph MATCH (u:?) -[e:?]-> (b:?) WHERE u.olvasojegySzam = ? AND b.ISBN = ?");
            bookingStatement.setString(1,Type.USER);
            bookingStatement.setString(2,"borrows");
            bookingStatement.setString(3,Type.BOOK);
            bookingStatement.setString(4, borrowing.getOlvasojegySzam());
            bookingStatement.setString(5, borrowing.getISBN());
            PgqlResultSet bookingRs = bookingStatement.executeQuery();
            for(PgxResult r : bookingRs)
            {
                bookingEdge = opg.getEdge(r.getEdge(1).getId());
            }
        } catch (PgqlException e) {
            e.printStackTrace();
        }
        opg.removeEdge(bookingEdge);
        System.out.println("A " + borrowing.getOlvasojegySzam() + " szammal rendelkezo user visszahozta a " + borrowing.getISBN() + " ISBN-nel rendelkezo konyvet(torlodott koztuk az el)");
        closeGraph();
    }

    @Override
    public List<Borrowing> getBorrowings() {
        openGraph();
        List<Borrowing> ret = new ArrayList<>();
        for (Edge next : opg.getEdges()) {
            Borrowing booking = new Borrowing(next.getProperty("olvasojegySzam").toString(), next.getProperty("ISBN").toString());
            ret.add(booking);
        }
        closeGraph();
        return ret;
    }
}
