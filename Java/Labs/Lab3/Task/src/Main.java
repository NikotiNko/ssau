import error.DuplicateSubjectException;
import error.PupilParseException;
import pupil.Pupil;
import pupil.Schoolboy;

import java.io.*;
import java.util.logging.Logger;

/**
 * @author 1
 * @since 08.09.2016.
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.toString());

    public static void main(String[] args) throws DuplicateSubjectException, IOException, PupilParseException, ClassNotFoundException {
        Pupil pupil = new Schoolboy("Nikitin", 0);
        pupil.addNewSubject("physic", 4);
        pupil.addNewSubject("chemistry", 2);
        pupil.addNewSubject("matan", 4);
        pupil.addNewSubject("oop", 5);

        //File
        {
            //Byte
            File streamPupilFile = new File("BytePupil.txt");

            FileOutputStream fos = new FileOutputStream(streamPupilFile);
            FileInputStream fis = new FileInputStream(streamPupilFile);

            Pupils.outputPupil(pupil, fos);
            fos.close();
            Pupil streamPupil = Pupils.inputPupil(fis);
            System.out.println(streamPupil.getJsonView());

            //Character
            File charPupilFile = new File("CharacterPupil.txt");

            FileWriter fw = new FileWriter(charPupilFile);
            FileReader fr = new FileReader(charPupilFile);

            Pupils.writePupil(pupil, fw);
            fw.close();
            Pupil charPupil = Pupils.readPupil(fr);
            System.out.println(charPupil.getJsonView());
        }

        //Console
        {
            Pupils.writePupil(pupil, new PrintWriter(System.out));
            Pupil charPupil = Pupils.readPupil(new InputStreamReader(System.in));

        }

        //Serialize
        {
            File serializeFile = new File("serialized.szd");

            FileOutputStream fos = new FileOutputStream(serializeFile);
            FileInputStream fis = new FileInputStream(serializeFile);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            ObjectInputStream ois = new ObjectInputStream(fis);

            oos.writeObject(pupil);
            Pupil readedPupil = (Pupil) ois.readObject();


            assert readedPupil.getJsonView().equals(pupil.getJsonView());

        }

    }
}
