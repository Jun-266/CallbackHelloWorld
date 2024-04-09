module Demo
{
    class Response{
        long responseTime;
        string value;
    }

    interface Callback{
        void callbackClient(Response res);
    }

    interface Printer
    {
        void printString(string s, Callback* callback);
    }

}