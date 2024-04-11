import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Object;
import static com.zeroc.Ice.Util.initialize;
import static com.zeroc.Ice.Util.stringToIdentity;

public class Server {

    public static void main(String[] args)
    {
        try(Communicator communicator = initialize(args,"config.server"))
        {
            ObjectAdapter adapter1 = communicator.createObjectAdapter("Printer");
            ObjectAdapter adapter2 = communicator.createObjectAdapter("ExecuteCommand");
            Object object1 = new PrinterI();
            Object object2 = new ExecuteCommandI();
            adapter1.add(object1, stringToIdentity("SimplePrinter"));
            adapter2.add(object2, stringToIdentity("SimpleCommandExecute"));
            adapter1.activate();
            adapter2.activate();
            communicator.waitForShutdown();
        }
    }

}