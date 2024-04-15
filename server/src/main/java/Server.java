// import java.io.*;
import java.util.List;
import java.util.ArrayList;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Object;
import static com.zeroc.Ice.Util.initialize;
import static com.zeroc.Ice.Util.stringToIdentity;

public class Server {

    public static void main(String[] args)
    {
        List<String> extraArgs = new ArrayList<>();
        try(Communicator communicator = initialize(args,"config.server", extraArgs)) {
            if (!extraArgs.isEmpty()) {
                System.err.println("too many arguments");
                for (String v : extraArgs)
                    System.out.println(v);
            }

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

    /*
    public static void f(String m)
    {
        String str = null, output = "";

        InputStream s;
        BufferedReader r;

        try {
            Process p = Runtime.getRuntime().exec(m);

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
            while ((str = br.readLine()) != null) 
            output += str + System.getProperty("line.separator"); 
            br.close(); 
        }
        catch(Exception ex) {
        }
    }
    */
}