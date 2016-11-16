import error.DuplicateSubjectException;
import error.PupilParseException;
import pupil.Collegeboy;
import pupil.Pupil;
import pupil.impl.Schoolboy;
import pupil.impl.Student;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @author 1
 * @since 08.09.2016.
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.toString());

    public static void main(String[] args) throws DuplicateSubjectException, IOException, PupilParseException, ClassNotFoundException, CloneNotSupportedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        args = input.split(" ");
        //Task 1
        Object instance = Pupils.testReflectionTaskOne(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        System.out.println(instance.toString());
        System.out.println();

        //Task 2
        Pupil schoolBoyPupil = Pupils.createPupil("Test1", 5, new Schoolboy("",0));
        System.out.println(schoolBoyPupil);
        schoolBoyPupil.setMark(1,3);

        Pupil studentPupil = Pupils.createPupil("Test2", 5, new Student("",0));
        System.out.println(studentPupil);
        studentPupil.setMark(1,5);
        System.out.println();


        Collegeboy collegeboy = new Collegeboy("dsd");
        collegeboy.addSubject("First", 4);
        collegeboy.printSubjects();
        System.out.println();
        //Task 4
        Pupil testPupil1 = new Schoolboy("", 0);
        testPupil1.addNewSubject("da",5);
        testPupil1.addNewSubject("fa",3);
        testPupil1.addNewSubject("olo",3);
        Pupil testPupil2 = new Schoolboy("", 0);
        testPupil2.addNewSubject("nope",4);
        testPupil2.addNewSubject("sasxa",5);
        testPupil2.addNewSubject("olo",5);
        double average = Pupils.getAverageOfMarks(testPupil1,testPupil2);
        System.out.println(average);

        //Task5
        Pupils.printRegister(testPupil1);

    }
}
