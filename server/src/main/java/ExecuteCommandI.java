import Demo.Response;
import Demo.ExecuteCommand;
//---------
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecuteCommandI implements ExecuteCommand
{
    public Response printCommandResult(String dataClient, String command,
                                       com.zeroc.Ice.Current current)
    {
        String fixedCommand = command.substring(1);
        System.out.println(dataClient + ":~$ " + fixedCommand);
        StringBuilder data = new StringBuilder();

        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(fixedCommand);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

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
