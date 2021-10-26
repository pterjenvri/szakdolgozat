package dao;

import com.tinkerpop.blueprints.Vertex;
import model.User;
import oracle.pg.nosql.OraclePropertyGraph;
import util.GraphConfig;
import util.Type;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements IUserDAO {
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
    public void add(User user) {
        openGraph();
        Vertex v1 = (Vertex) opg.addVertex(user.getOlvasojegySzam());
        v1.setProperty("keresztnev",user.getKeresztnev());
        v1.setProperty("vezeteknev",user.getVezeteknev());
        v1.setProperty("szuletesiIdo",user.getSzuletesiIdo());
        v1.setProperty("olvasojegySzam",user.getOlvasojegySzam());
        v1.setProperty("tipus", Type.USER);
        System.out.println(user.getVezeteknev() + " " + user.getKeresztnev() + " olvasojegyszam: " + user.getOlvasojegySzam() + " elmentodott az adatbazisba(letrejott a node)");
        closeGraph();
    }

    @Override
    public List<User> getUsers() {
        openGraph();
        List<User> ret = new ArrayList<>();
        for (Vertex next : opg.getVertices("olvasojegySzam")) {
            User user = new User(next.getProperty("vezeteknev").toString(), next.getProperty("keresztnev").toString(), next.getProperty("olvasojegySzam").toString(), next.getProperty("szuletesiIdo").toString());
            ret.add(user);
        }
        closeGraph();
        return ret;
    }
}
