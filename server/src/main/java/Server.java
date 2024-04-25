import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Object;
import static com.zeroc.Ice.Util.initialize;
import static com.zeroc.Ice.Util.stringToIdentity;

public class Server {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static void main(String[] args)
    {
        List<String> extraArgs = new ArrayList<>();
        try(Communicator communicator = initialize(args,"config.server", extraArgs)) {

            if (!extraArgs.isEmpty())
            {
                System.err.println("too many arguments");
                for (String v : extraArgs)
                    System.out.println(v);
            }

            ObjectAdapter adapter1 = communicator.createObjectAdapter("Printer");
            ObjectAdapter adapter2 = communicator.createObjectAdapter("ExecuteCommand");
            ObjectAdapter adapter3 = communicator.createObjectAdapter("CRUDClient");
            Object object1 = new PrinterI();
            Object object2 = new ExecuteCommandI();
            Object object3 = new CRUDClientI();
            adapter1.add(object1, stringToIdentity("SimplePrinter"));
            adapter2.add(object2, stringToIdentity("SimpleCommandExecute"));
            adapter3.add(object3, stringToIdentity("SimpleCRUDClient"));
            adapter1.activate();
            adapter2.activate();
            adapter3.activate();

            communicator.waitForShutdown();
        }
    }
}