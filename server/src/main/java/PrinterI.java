import Demo.Response;
import Demo.Printer;
import com.zeroc.Ice.Current;

public class PrinterI implements Printer {

    public Response printString(String s, Current current)
    {
        long startTime = System.currentTimeMillis();
        System.out.println(s);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        return new Response(elapsedTime, "Server response: " + s);
    }

}