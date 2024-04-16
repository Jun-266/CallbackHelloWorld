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
    public synchronized void showClients(CallbackPrx callbackPrx, Current current)
    {
        StringBuilder clientData = new StringBuilder();
        new Thread(() -> {
            try
            {
                if (!clients.isEmpty())
                {
                    for (String client : clients)
                        clientData.append(client).append("\n");
                    callbackPrx.callbackClient(new Response(0, clientData.toString()));
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }).start();
    }
}
