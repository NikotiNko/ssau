import error.DuplicateSubjectException;
import error.PupilParseException;
import pupil.Pupil;
import pupil.impl.Schoolboy;
import pupil.impl.Student;
import thread.StudentNameWriter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * @author 1
 * @since 08.09.2016.
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.toString());

    public static void main(String[] args) throws DuplicateSubjectException, IOException, PupilParseException, ClassNotFoundException, CloneNotSupportedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        Pupil schoolboy = new Schoolboy("Test", 0);
        schoolboy.addNewSubject("Physic", 4);
        schoolboy.addNewSubject("Chemistry", 4);
        schoolboy.addNewSubject("Math", 4);

        Pupil student = new Student("Test", 0);
        student.addNewSubject("Physic", 4);
        student.addNewSubject("Chemistry", 4);
        student.addNewSubject("Math", 4);

        //Task 1
/*        MarksWriter marksWriter = new MarksWriter(schoolboy);
        SubjectsWriter subjectsWriter = new SubjectsWriter(schoolboy);

        marksWriter.start();
        subjectsWriter.start();*/

        //Task 2
/*        PupilSynchronizer synchronizer = new PupilSynchronizer(schoolboy);
        Thread marksWriterThread = new Thread(new RunnableMarksWriter(synchronizer));
        Thread subjectsWriterThread = new Thread(new RunnableSubjectsWriter(synchronizer));

        marksWriterThread.start();
        subjectsWriterThread.start();*/

        //Task 3
/*        ReentrantLock lock = new ReentrantLock();
        Condition waiter = lock.newCondition();
        Thread allMarksWriterThread = new Thread(new RunnableAllMarksWriter(student,lock, waiter));
        Thread allSubjectsWriterThread = new Thread(new RunnableAllSubjectsWriter(student,lock, waiter));


        allMarksWriterThread.start();
        synchronized (schoolboy){
            schoolboy.wait(100);
        }
        allSubjectsWriterThread.start();*/

        //Task 4
        Pupil test1 = new Schoolboy("Test1", 0);
        Pupil test2 = new Schoolboy("Test2", 0);
        Pupil test3 = new Schoolboy("Test3", 0);
        Pupil test4 = new Schoolboy("Test4", 0);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(new StudentNameWriter(test1));
        executor.execute(new StudentNameWriter(test2));
        executor.execute(new StudentNameWriter(test3));
        executor.execute(new StudentNameWriter(test4));
        executor.shutdown();
    }
}
