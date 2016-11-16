import error.DuplicateSubjectException;
import error.PupilParseException;
import pupil.Pupil;
import pupil.impl.Schoolboy;
import pupil.impl.Student;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 1
 * @since 06.09.2016.
 */
public class Pupils {

    public static void outputPupil(Pupil v, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        dos.writeUTF(v.getStudentName());
        dos.writeInt(v.getLength());
        for (int i = 0; i < v.getLength(); i++) {
            dos.writeUTF(v.getSubject(i));
            dos.writeInt(v.getMark(i));
        }
        dos.flush();
    }

    public static Pupil inputPupil(InputStream is) throws IOException, PupilParseException, DuplicateSubjectException {
        DataInputStream dis = new DataInputStream(is);
        String name = dis.readUTF();
        int count = dis.readInt();

        Pupil p = new Student(name, count);
        for (int i = 0; i < count; i++) {
            p.setSubject(i, dis.readUTF());
            p.setMark(i, dis.readInt());
        }
        return p;
    }

    public static void writePupil(Pupil v, Writer writer) throws IOException {
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println(v.getStudentName());
        printWriter.println(v.getLength());
        for (int i = 0; i < v.getLength(); i++) {
            printWriter.println(v.getSubject(i));
            printWriter.println(v.getMark(i));
        }
        printWriter.flush();
    }

    public static Pupil readPupil(Reader in) throws PupilParseException, IOException, DuplicateSubjectException {
        BufferedReader reader = new BufferedReader (in);

        String name = reader.readLine();
        int count = Integer.parseInt(reader.readLine());
        Pupil p = new Student(name, count);

        for (int i = 0; i < count; i++) {
            p.setSubject(i,reader.readLine());
            p.setMark(i,Integer.parseInt(reader.readLine()));
        }
        return p;
    }

    public static double getAverageOfMarks(Pupil... pupils) {
        double sum = 0;
        double length = 0;
        for (Pupil pupil: pupils) {
            for (int i = 0; i < pupil.getLength(); i++) {
                sum += pupil.getMark(i);
            }
            length += pupil.getLength();
        }
        return sum / length;
    }

    public static void printRegister(Pupil pupil) {
        System.out.println("Subjects:");
        pupil.printSubjects();
        System.out.println("Marks:");
        pupil.printMarks();
    }

    public static Object testReflectionTaskOne(String type, String method, int index, int value) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class targetClass = Class.forName(type);
        Object instance = targetClass.getConstructor(String.class, int.class).newInstance("ReflectionAPI", index+1);
        targetClass.getMethod(method, int.class, int.class).invoke(instance, index, value);

        return instance;
    }

    public static Pupil createPupil(String studentName, int size, Pupil exampleInstance) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        try {
            return exampleInstance.getClass().getConstructor(String.class, int.class).newInstance(studentName, size);
        }catch (NoSuchMethodException e){
            return null;
        }
    }

}
