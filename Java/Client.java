import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ArithmeticException;
import java.net.ConnectException;
import java.net.Socket;

public class Client {
    public static final String serverAddress = "localhost";
    /**
     * This method name and parameters must remain as-is
     */
    public static int add(int lhs, int rhs) {
        int result = (int) Double.NEGATIVE_INFINITY;
        try {
            Socket clientSocket = new Socket(serverAddress, PORT);
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            RemoteMethod addMethod = new RemoteMethod("add", new Object[] {lhs, rhs});
            oos.writeObject(addMethod);
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            result = (int) ois.readObject();
        
            clientSocket.close();
        } catch(ConnectException ce) {
            System.out.println("Client was not able to connect to the port for the specified server.");
            System.out.println("Please restart the client and try again.");
            System.exit(1);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * This method name and parameters must remain as-is
     */
    public static int divide(int num, int denom) {
        if (denom == 0) {
            throw new ArithmeticException("Illegal Operation: Division by Zero");
        }
        int result = (int) Double.NEGATIVE_INFINITY;
        try {
            Socket clientSocket = new Socket(serverAddress, PORT);
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            RemoteMethod divideMethod = new RemoteMethod("divide", new Object[] {num, denom});
            oos.writeObject(divideMethod);
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            result = (int) ois.readObject();
        
            clientSocket.close();
        } catch(ConnectException ce) {
            System.out.println("Client was not able to connect to the port for the specified server.");
            System.out.println("Please restart the client and try again.");
            System.exit(1);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * This method name and parameters must remain as-is
     */
    public static String echo(String message) {
        // connect to server
        // Create an instance of RemoteHand
        // RemoteMethod add = new RemoteMethod("add", new Object[] {lhs, rhs});
        // ObjectOutputStream to serialize the add instance
        // Send serialized data to output stream:
        // OutputStream os = socket.getOutputStream();
        String response = "";
        try {
            Socket clientSocket = new Socket(serverAddress, PORT);
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            RemoteMethod echoMethod = new RemoteMethod("echo", new Object[] {message});
            oos.writeObject(echoMethod);
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            response = (String) ois.readObject();
            // System.out.println(response);

            clientSocket.close();
        } catch(ConnectException ce) {
            System.out.println("Client was not able to connect to the port for the specified server.");
            System.out.println("Please restart the client and try again.");
            System.exit(1);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return (String) response;
    }

    // Do not modify any code below this line
    // --------------------------------------
    String server = "localhost";
    public static final int PORT = 10314;

    public static void main(String... args) {
        // All of the code below this line must be uncommented
        // to be successfully graded.
        System.out.print("Testing... ");

        if (add(2, 4) == 6)
            System.out.print(".");
        else
            System.out.print("X");

        try {
            divide(1, 0);
            System.out.print("X");
        }
        catch (ArithmeticException x) {
            System.out.print(".");
        }

        if (echo("Hello").equals("You said Hello!"))
            System.out.print(".");
        else
            System.out.print("X");    
        
        System.out.println(" Finished");
    }
}