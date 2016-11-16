import error.DuplicateSubjectException;
import pupil.Pupil;
import pupil.impl.Schoolboy;
import pupil.impl.Student;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * @author 1
 * @since 08.09.2016.
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.toString());

    public static void main(String[] args) throws DuplicateSubjectException, IOException, ClassNotFoundException, CloneNotSupportedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        Pupil schoolboy = new Schoolboy("Test", 0);
        schoolboy.addNewSubject("Physic", 4);
        schoolboy.addNewSubject("Chemistry", 5);
        schoolboy.addNewSubject("Math", 2);

        Pupil student1 = new Student("Test", 0);
        student1.addNewSubject("Physic", 2);
        student1.addNewSubject("Chemistry", 2);
        student1.addNewSubject("Math", 3);

        Pupil student2 = new Student("Test", 0);
        student2.addNewSubject("Physic", 5);
        student2.addNewSubject("Chemistry", 5);
        student2.addNewSubject("Math", 2);

        Pupil[] pupils = new Pupil[3];

        pupils[0] = schoolboy;
        pupils[1] = student1;
        pupils[2] = student2;

        Socket socket;
        while (true) {
            try {
                System.out.println("Try to connect...");
                socket = new Socket("localhost", 8585);
                System.out.println("Connected!");
                break;
            } catch (ConnectException e) {
                System.out.println("Connection failed. Retrying in 5 seconds");
                Thread.sleep(5000);
            }
        }

        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(pupils);
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        double average = inputStream.readDouble();
        System.out.println("Среднее значение:");
        System.out.println(average);
    }
}
