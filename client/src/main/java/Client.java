import Demo.*;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import static com.zeroc.Ice.Util.initialize;
import static com.zeroc.Ice.Util.stringToIdentity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Client
{
    public static void main(String[] args) { startClient(args); }

    public static void startClient(String[] args) {
        boolean finish = false;
        List<String> extraArgs = new ArrayList<>();
        try(Communicator communicator = initialize(args,"config.client", extraArgs))
        {
            Scanner sc = new Scanner(System.in);
            String clientData = getClientData();
            Response response;

            PrinterPrx service1 = PrinterPrx
                    .checkedCast(communicator.propertyToProxy("Printer.Proxy"));
            ExecuteCommandPrx service2 = ExecuteCommandPrx
                    .checkedCast(communicator.propertyToProxy("ExecuteCommand.Proxy"));
            CRUDClientPrx service3 = CRUDClientPrx
                    .checkedCast(communicator.propertyToProxy("CRUDClient.Proxy"));

            ObjectAdapter adapter = communicator.createObjectAdapter("Callback");
            CallbackI callbackI = new CallbackI();
            ObjectPrx objectPrx = adapter.add(callbackI, stringToIdentity("callbackClient"));
            adapter.activate();

            CallbackPrx callbackPrx = CallbackPrx.uncheckedCast(objectPrx);

            service3.registerClient(clientData);

            response = service1.printString(clientData + " is online!");
            System.out.println(response.value + ", " + response.responseTime);

            while (!finish) {
                System.out.print(clientData + ": ");
                String message = sc.nextLine();
                if (message.equalsIgnoreCase("Exit"))
                    finish = true;
                else if (message.equals("list clients"))
                    printClients(service3, callbackPrx);
                else if (message.equals("BC"))
                    System.out.println();
                else
                    executeCommand(service2, message, clientData);
            }
            sc.close();
        }
    }

    public static String getClientData() {
        StringBuilder data = new StringBuilder();
        try {
            String command1 = "whoami", command2 = "hostname";
            Runtime runtime = Runtime.getRuntime();
            Process process1 = runtime.exec(command1);
            BufferedReader input1 = new BufferedReader(
                    new InputStreamReader(process1.getInputStream()));
            String line;
            while ((line = input1.readLine()) != null)
                data.append(line);

            data.append("@");

            Process process2 = runtime.exec(command2);
            BufferedReader input2 = new BufferedReader(
                    new InputStreamReader(process2.getInputStream()));
            while ((line = input2.readLine()) != null)
                data.append(line);
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return data.toString();
    }

    public static void executeCommand(ExecuteCommandPrx executeCommandPrx, String command,
                                      String clientData)
    {
        if (executeCommandPrx == null)
            throw new Error("Invalid Proxy");

        Response response;

        response = executeCommandPrx.printCommandResult(clientData, command);
        System.out.println(clientData + ":$ " + command);
        System.out.println(response.value);
    }

    public static void printClients(CRUDClientPrx crudClients, CallbackPrx callbackPrx)
    {
        if (crudClients == null)
            throw new Error("Invalid Proxy");

        crudClients.showClients(callbackPrx);
    }

}