import Demo.CRUDClient;
import Demo.CallbackPrx;
import Demo.Response;

import com.zeroc.Ice.Current;

import java.util.ArrayList;

public class CRUDClientI implements CRUDClient
{

    private final ArrayList<String> clients = new ArrayList<>();

    @Override
    public void registerClient(String dataClient, Current current)
    {
        clients.add(dataClient);
    }

    @Override
    public void showClients(CallbackPrx callbackPrx, Current current) {
        StringBuilder clientData = new StringBuilder();
        Thread t = new Thread(() -> {
            try {
                if (!clients.isEmpty()) {
                    for (String client : clients)
                        clientData.append(client).append(" ");
                    callbackPrx.callbackClient(new Response(0, clientData.toString()));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
