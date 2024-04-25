import Demo.CRUDClient;
import Demo.CallbackPrx;
import Demo.Response;

import com.zeroc.Ice.Current;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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
        /*
        Future<?> f = getFuture(callbackPrx);
        try {
            f.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        */
        StringBuilder clientData = new StringBuilder();
        if (!clients.isEmpty()) {
            for (int i = 0; i < (clients.size() - 1); i++)
                clientData.append(clients.get(i)).append("\n");
            clientData.append(clients.get(clients.size() - 1));
            callbackPrx.callbackClient(new Response(0, clientData.toString()));
        }
    }

    private Future<?> getFuture(CallbackPrx callbackPrx) {
        StringBuilder clientData = new StringBuilder();

        ExecutorService executorService = Server.getExecutorService();

        return executorService.submit(() -> {
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
    }
}
