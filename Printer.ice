module Demo
{
    class Response
    {
        long responseTime;
        string value;
    };

    interface Printer
    {
        Response printString(string s);
    };

    interface ExecuteCommand
    {
        Response printCommandResult(string dataClient, string command);
    };

    interface Callback
    {
        void callbackClient(Response res);
    };

/*
    interface Printer
    {
        void printString(string s, Callback* callback);
    };
*/
};