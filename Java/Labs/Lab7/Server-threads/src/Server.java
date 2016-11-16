import pupil.Pupil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Артем on 27.10.2016.
 */
public class Server {

    boolean isInterrupted = false;

    public void startServer() throws IOException, ClassNotFoundException {
        System.out.println("Server started");
        ServerSocket serverSocket = new ServerSocket(8585);
        System.out.println("Server socket created");
        while (!isInterrupted) {
            System.out.println("Wait connection");
            Socket socket = serverSocket.accept();
            System.out.println("Connected!");
            new Thread(()-> {
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    Pupil[] pupils = (Pupil[]) inputStream.readObject();
                    double average = Pupils.getAverageOfMarks(pupils);

                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                    outputStream.writeDouble(average);
                    System.out.println("END - SUCCESS. Result:" + average);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println("Server stopped");
    }

    public boolean isInterrupted() {
        return isInterrupted;
    }

    public void setInterrupted(boolean interrupted) {
        isInterrupted = interrupted;
    }
}
