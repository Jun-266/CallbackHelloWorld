import com.zeroc.Ice.Communicator;
import static com.zeroc.Ice.Util.initialize;

import Demo.Response;
import Demo.ExecuteCommandPrx;
import Demo.PrinterPrx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        startClient(args);
    }

    public static void startClient(String[] args) {
        boolean finish = false;
        try(Communicator communicator = initialize(args,"config.client")) {
            Scanner sc = new Scanner(System.in);
            String clientData = getClientData();
            Response response;

            PrinterPrx service1 = PrinterPrx
                    .checkedCast(communicator.propertyToProxy("Printer.Proxy"));
            ExecuteCommandPrx service2 = ExecuteCommandPrx
                    .checkedCast(communicator.propertyToProxy("ExecuteCommand.Proxy"));

            response = service1.printString(clientData + " is online!");
            System.out.println(response.value + ", " + response.responseTime);

            while (!finish) {
                System.out.print(clientData + ": ");
                String message = sc.nextLine();
                if (message.equalsIgnoreCase("Exit"))
                    finish = true;
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
}