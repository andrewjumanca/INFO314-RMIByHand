## Adding Remote Object Instances
Instead of having static-only methods on our client interface within "Client.java", we can create a RemoteMethod factory on the server-side ("Server.java") that exposes
the ability to create RemoteMethod objects from our client.

In the current implementation of the RMI by hand, our client implements three static methods (add, divide, echo) all of which are responsible for opening a socket connection
 to the server, sending a RemoteMethod object of the specified method type, and closing that socket. If we'd like to make these methods object-instaces instead, what we would
 do is expose the ability to create RemoteMethod objects on the server itself, as opposed to creating one each time we call add, divide, or echo. 
 
 By having this object-instance method type, we simply create an instance of the RemoteMethod object on the server and send back a specific RemoteMethod ID to the client.
 Once the client has this ID, they can now manipulate the RemoteMethod object using the allowed methods (add, divide, echo). 
 
 In practice, the methods would work like this:
 
 Client: *Connecting to Server*
 Server: *Client Accepted*
 Client: *Wants to create Remote Method for math, calls:*
 Client: RemoteMethod math = (RemoteMethod) Naming.lookup("ObjectID");
 Client: math.add(2, 4)
 
 public class Main {
   public RemoteMethod createMethod(String[] methods) {
    // create server connection and establish output stream
    Socket clientSocket = new Socket(address, port);
    ObjectOutputStream out = new ByteOutputStream();
    
    // specify which methods you'd like to add to your RemoteMethod (this is arbitrary and can be done many different ways)
    out.write(methods);
    out.flush();
    
 }
   public static void main(String[] args) {
     RemoteMethod math = new createMethod("add", "divide");
     // Server: *sends back associated ID to this object*
     System.out.println(math.add(2, 4));
     // Client: 6
     RemoteMethod echo = new createMethod("echo");
     // Server: *sends back different associated ID to this object*
     System.out.println(echo.echo("Hello there"));
     // Client: You said Hello there!
   }
 }
 
 Essentially, what this version of the implementation does--even though the server code isn't shown--is it removes the need for serialization because the objects 
 stay purely on the server side. This way, there is no risk of the object being leaked across the means of connection. Furthermore, the only data being sent from 
 client to server is the object IDs and the corresponding arguments such as integers or strings to be parsed through.
