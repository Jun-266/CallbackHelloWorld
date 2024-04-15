// import java.util.concurrent.Semaphore;

// import com.zeroc.IceInternal.ThreadPool;
import com.zeroc.Ice.Current;

import Demo.Response;
import Demo.Printer;
import Demo.CallbackPrx;

public class PrinterI implements Printer
{
    // ThreadPool ca;
    // Semaphore s = new Semaphore(2);

    public synchronized void printString(String s, CallbackPrx callback, Current current)
    {
        /*
        synchronized(objs){

        }
        */

        // ca.execute(null);
        new Thread(()->{
            try{
                System.out.println(s);
                Thread.sleep(10000);
                callback.callbackClient(new Response(0, "The server received your message."));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        ).start();
    }
}