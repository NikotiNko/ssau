import error.DuplicateSubjectException;
import error.PupilParseException;
import pupil.Pupil;
import pupil.Schoolboy;
import pupil.Student;

import java.io.*;
import java.util.logging.Logger;

/**
 * @author 1
 * @since 08.09.2016.
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.toString());

    public static void main(String[] args) throws DuplicateSubjectException, IOException, PupilParseException, ClassNotFoundException, CloneNotSupportedException {
        //
        Pupil pupil = new Schoolboy("Nikitin", 0);
        pupil.addNewSubject("physic", 4);
        pupil.addNewSubject("chemistry", 2);
        pupil.addNewSubject("matan", 4);
        pupil.addNewSubject("oop", 5);

        System.out.println(pupil.toString());

        //
        Pupil equalsPupil = new Student("Nikitin", 0);
        equalsPupil.addNewSubject("physic", 4);
        equalsPupil.addNewSubject("chemistry", 2);
        equalsPupil.addNewSubject("matan", 4);
        equalsPupil.addNewSubject("oop", 5);

        System.out.println(equalsPupil);

        //equals
        System.out.println(pupil.equals(equalsPupil));


        //clone
        Pupil clone = pupil.clone();
        System.out.println(pupil.equals(clone));
        clone.setMark(1,5);
        System.out.println(pupil.equals(clone));


    }
}
