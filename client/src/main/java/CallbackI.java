import com.zeroc.Ice.Current;

import Demo.Response;
import Demo.Callback;

public class CallbackI implements Callback {

    @Override
    public void callbackClient(Response response, Current current) {
       System.out.println("Server response: " + response.value);
    }
    
}
