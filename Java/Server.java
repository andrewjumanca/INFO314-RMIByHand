import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(10314);
            // Read the bytes
            // Deserialize using ObjectInputStream
            while (true) {
                Socket socket = serverSocket.accept();
                
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                RemoteMethod method = (RemoteMethod) ois.readObject();
                
                Object result = invokeMethod(method);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                oos.writeObject(result);
                oos.flush();
                oos.close();
                ois.close();
                socket.close();
                

                System.out.println("Remote Method Completed: " + method.getMethodName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object invokeMethod(RemoteMethod method) {
        String methodName = method.getMethodName();
        Object[] args = method.getArgs();
        
        switch (methodName) {
            case "add":
                int sum = (int) args[0] + (int) args[1];
                return sum;
            case "divide":
                int dividend = (int) args[0];
                int divisor = (int) args[1];
                if (divisor == 0) {
                    throw new ArithmeticException("Illegal Operation: Division by Zero");
                }
                int quotient = dividend / divisor;
                return quotient;
            case "echo":
                String message = (String) args[0];
                String response = "You said " + message + "!";
                return response;
            default:
                throw new IllegalArgumentException("Invalid method name: " + methodName);
        }
    }

    // Do not modify any code below tihs line
    // --------------------------------------
    public static String echo(String message) { 
        return "You said " + message + "!";
    }
    public static int add(int lhs, int rhs) {
        return lhs + rhs;
    }
    public static int divide(int num, int denom) {
        if (denom == 0)
            throw new ArithmeticException();

        return num / denom;
    }
}