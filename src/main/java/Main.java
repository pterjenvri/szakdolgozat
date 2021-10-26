import controller.BookController;
import controller.BookingController;
import controller.UserController;
import model.Book;
import model.Booking;
import model.User;
import oracle.kv.KVStoreConfig;
import oracle.nosql.driver.NoSQLHandle;
import oracle.nosql.driver.NoSQLHandleConfig;
import oracle.nosql.driver.NoSQLHandleFactory;
import oracle.nosql.driver.Region;
import oracle.nosql.driver.iam.SignatureProvider;
import oracle.nosql.driver.kv.StoreAccessTokenProvider;
import oracle.nosql.driver.ops.*;
import oracle.nosql.driver.values.JsonOptions;
import oracle.nosql.driver.values.MapValue;
import oracle.pg.nosql.OraclePropertyGraph;
import util.GraphConfig;

import java.io.File;
import java.util.List;

public class Main {


    private static NoSQLHandle getNoSQLConnectionCloud()
    {
        SignatureProvider authProvider = new SignatureProvider(
                "tenantId",
                "userId",
                "fingerprint",
                new File("~/.oci/oci_api_key.pem"),
                "pass-phrase".toCharArray()
        );
        NoSQLHandleConfig config = new NoSQLHandleConfig(Region.EU_FRANKFURT_1, authProvider).setDefaultCompartment("szakdolgozat");
        NoSQLHandle handle = NoSQLHandleFactory.createNoSQLHandle(config);
        return handle;
    }

    private static NoSQLHandle getNoSQLConnectionLocal()
    {
        String endpoint = "http://localhost:8080";
        StoreAccessTokenProvider atProvider = new StoreAccessTokenProvider();
        NoSQLHandleConfig config = new NoSQLHandleConfig(endpoint);
        config.setAuthorizationProvider(atProvider);
        NoSQLHandle handle = NoSQLHandleFactory.createNoSQLHandle(config);
        return handle;
    }

    private static void createTestTable(NoSQLHandle serviceHandle) throws Exception {
        TableRequest req = new TableRequest().setStatement(
                "CREATE TABLE IF NOT EXISTS test_1" +
                        "(id LONG, content JSON, primary key (id))"
        );

        req.setTableLimits(new TableLimits(100,100,1));

        TableResult tr = serviceHandle.tableRequest(req);

        tr.waitForCompletion(serviceHandle,120000,500);

        if(tr.getTableState() != TableResult.State.ACTIVE)
        {
            throw new Exception("Unable to create table.");
        }
    }

    private static void writeOneRecord(NoSQLHandle serviceHandle, long id, String jsonContent)
    {
        MapValue value = new MapValue().put("id",id);

        MapValue contentVal = value.putFromJson("content",jsonContent, new JsonOptions());
        PutRequest putRequest = new PutRequest()
                .setValue(value)
                .setTableName("test_1");
        serviceHandle.put(putRequest);
    }

    private static String readOneRecord(NoSQLHandle serviceHandle, long id)
    {
        GetRequest getRequest = new GetRequest();
        getRequest.setKey(new MapValue().put("id",id));
        getRequest.setTableName("test_1");

        GetResult gr = serviceHandle.get(getRequest);

        if(gr != null)
        {
            return gr.getValue().toJson();
        }
        else
        {
            return null;
        }
    }

    private static void deleteOneRecord(NoSQLHandle serviceHandle, long id)
    {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setKey(new MapValue().put("id",id));
        deleteRequest.setTableName("test_1");

        DeleteResult dr = serviceHandle.delete(deleteRequest);

    }

    private static void clearRepository() {
        try {
            OraclePropertyGraph opg = OraclePropertyGraph.getInstance(GraphConfig.cfg);
            opg.clearRepository();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        clearRepository();

        User User1 = new User("User","One","75250330100","19980131");

        User User2 = new User("User","Two","75250330101","19990801");

        Book RandomBook = new Book("Random Book", "9781387207770");

        Book HarryPotter = new Book("Harry Potter and the Sorcerer's Stone","9780590353403");

        Booking User1RandomBook = new Booking(User1.getOlvasojegySzam(), RandomBook.getISBN());

        Booking User2HarryPotter = new Booking(User2.getOlvasojegySzam(), HarryPotter.getISBN());

        UserController.getInstance().add(User1);

        UserController.getInstance().add(User2);

        List<User> users = UserController.getInstance().getUsers();

        System.out.println("Az adatbazisbol kiolvasott userek:");
        for(User user : users)
        {
            System.out.println(user.toString());
        }

        BookController.getInstance().add(RandomBook);

        BookController.getInstance().add(HarryPotter);

        System.out.println("Az adatbazisbol kiolvasott konyvek:");
        List<Book> books = BookController.getInstance().getBooks();
        for(Book book : books)
        {
            System.out.println(book.toString());
        }

        BookingController.getInstance().kolcsonoz(User1RandomBook);

        BookingController.getInstance().kolcsonoz(User2HarryPotter);

        System.out.println("Az adatbazisbol kiolvasott kolcsonzesek:");
        List<Booking> bookings = BookingController.getInstance().getBookings();
        for(Booking booking : bookings)
        {
            System.out.println(booking.toString());
        }

        BookingController.getInstance().visszahoz(User1RandomBook);

        System.out.println("Az adatbazisbol kiolvasott kolcsonzesek:");
        bookings = BookingController.getInstance().getBookings();
        for(Booking booking : bookings)
        {
            System.out.println(booking.toString());
        }

        try
        {
            NoSQLHandle handle = getNoSQLConnectionLocal();
            createTestTable(handle);
            writeOneRecord(handle, 1, "{\"hello\":\"world\"}");
            writeOneRecord(handle,2,"{\"test2\":\"test2\"}");
//            System.out.println(readOneRecord(handle,1));
//            System.out.println(readOneRecord(handle,2));
            deleteOneRecord(handle,1);
            System.exit(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
