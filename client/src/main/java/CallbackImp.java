import com.zeroc.Ice.Current;

import Demo.Response;
import Demo.Callback;

public class CallbackImp implements Callback {

    @Override
    public void callbackClient(Response response, Current current) {
       System.out.println("Server response: " + response.value + ", " + response.responseTime);
       System.out.println("Callback invoked");
    }
    
}
