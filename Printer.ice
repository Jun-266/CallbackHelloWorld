module Demo
{
    class Response
    {
        long responseTime;
        string value;
    };

    interface Callback
    {
        void callbackClient(Response res);
    };

    interface Printer
    {
        Response printString(string s);
    };

    interface ExecuteCommand
    {
        Response printCommandResult(string dataClient, string command);
    };

    interface CRUDClient
    {
        void registerClient(string dataClient);

        void showClients(Callback* callback);
    };

};