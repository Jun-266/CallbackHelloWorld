module Demo
{
    class Response
    {
        long responseTime;
        string value;
    };

    //-------- Servicios del cliente ---------

    // Esta interfaz representa el servicio del cliente
    // por el cual, el servidor puede contactar al cliente para
    // informarle que el servicio solicitado, ya está listo.
    interface Callback
    {
        void callbackClient(Response res);
    };

    //-------- Servicios del servidor ---------
    interface Printer
    {
        Response printString(string s);
    };

    interface ExecuteCommand
    {
        Response printCommandResult(string dataClient, string command);
    };

    //------- Implementación con Callback ---------

    // Esta interfaz representa el servicio del servidor
    // el cual, es usado por el cliente.
    // interface Printer
    // {
        // El '*' es para indicarle a ICE que habrán
        // más clientes para un mismo servicio.
    //    void printString(string s, Callback* callback);
    // }

};