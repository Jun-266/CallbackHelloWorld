import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import static com.zeroc.Ice.Util.initialize;
import static com.zeroc.Ice.Util.stringToIdentity;

import Demo.CallbackPrx;
import Demo.Response;
import Demo.PrinterPrx;

public class Client
{
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        try(Communicator communicator = initialize(args,"config.client",extraArgs))
        {
            //com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimplePrinter:default -p 10000");
            Response response = null;
            PrinterPrx service = PrinterPrx
                    .checkedCast(communicator.propertyToProxy("Printer.Proxy"));
            
            if(service == null)
            {
                throw new Error("Invalid proxy");
            }

            ObjectAdapter adapter = communicator.createObjectAdapter("Callback");
            CallbackImp callbackImp = new CallbackImp();
            ObjectPrx obprx = adapter.add(callbackImp, stringToIdentity("callbackClient"));
            adapter.activate();

            CallbackPrx prx = CallbackPrx.uncheckedCast(obprx);
            service.printString("Hello World from a remote client!", prx);
            System.out.println("invoked callback");
            
            communicator.waitForShutdown();
        }
    }
}