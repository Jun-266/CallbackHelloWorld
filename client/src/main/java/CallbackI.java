import com.zeroc.Ice.Current;

import Demo.Response;
import Demo.Callback;

import java.util.concurrent.CompletableFuture;

public class CallbackI implements Callback {

    @Override
    public synchronized void callbackClient(Response response, Current current) {
        CompletableFuture.runAsync(() -> System.out.println(response.value));
    }
    
}
