import com.zeroc.Ice.Current;

import Demo.Response;
import Demo.ExecuteCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecuteCommandI implements ExecuteCommand
{
    public Response printCommandResult(String dataClient, String command,
                                       Current current)
    {
        Response myResponse = null;

        if (command.startsWith("!"))
        {
            myResponse = executeCommand(dataClient, command);
        }
        else if (command.startsWith("listifs"))
        {
            myResponse = listLogicalInterfaces(dataClient);
        }

        return myResponse;
    }

    public Response executeCommand(String dataClient, String command)
    {
        String fixedCommand = command.substring(1);
        return getResponse(dataClient, fixedCommand);
    }

    public Response listLogicalInterfaces(String dataClient)
    {
        String command = "ifconfig";
        return getResponse(dataClient, command);
    }

    private Response getResponse(String dataClient, String command) {
        System.out.println(dataClient + ":$ " + command);
        StringBuilder data = new StringBuilder();

        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = input.readLine()) != null)
                data.append(line).append("\n");

            System.out.println(data);

        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        return new Response(0, data.toString());
    }
}