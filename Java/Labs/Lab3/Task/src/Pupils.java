import error.DuplicateSubjectException;
import error.PupilParseException;
import pupil.Pupil;
import pupil.Schoolboy;
import pupil.Student;

import java.io.*;
import java.lang.reflect.Array;
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

    private static <T> T[] getArrayFromString(String array, Class<T> type) throws PupilParseException {
        StringBuilder inputBuilder = new StringBuilder(array);
        if (inputBuilder.charAt(0)!= '[') throw new PupilParseException(0);
        inputBuilder.deleteCharAt(0);
        if (inputBuilder.charAt(0)== ']') return (T[]) Array.newInstance(type, 0);
        List<T> list = new LinkedList<T>();

        while (inputBuilder.length() > 0) {
            int start = 0;
            int end = inputBuilder.indexOf(",");
            if (end==-1) end = inputBuilder.length() - 1;
            String element = inputBuilder.substring(start, end);
            inputBuilder.delete(0, end + 1);
            list.add(type == Integer.class ? type.cast(Integer.valueOf(element)) : type.cast(element));
        }

        return list.toArray((T[]) Array.newInstance(type, list.size()));
    }

    public static double getAverangeOfMarks(Pupil pupil) {
        int sum = 0;
        for (int i = 0; i < pupil.getLength(); i++) {
            sum += pupil.getMark(i);
        }
        return sum / pupil.getLength();
    }

    public static void printRegister(Pupil pupil) {
        System.out.println("Subjects:");
        pupil.printSubjects();
        System.out.println("Marks:");
        pupil.printMarks();
    }


    //Bad version :(
    private static Pupil convertToPupil(String input) throws PupilParseException, DuplicateSubjectException {
        Pupil pupil = new Schoolboy(null, 0);

        StringBuilder inputBuilder = new StringBuilder(input.replaceAll(" ", ""));
        if (inputBuilder.charAt(0)!= '{') throw new PupilParseException(0);
        inputBuilder.deleteCharAt(0);

        while (inputBuilder.length() > 0) {
            int start = 0;
            int end = inputBuilder.indexOf(":");
            String key = inputBuilder.substring(start, end);
            start = end + 1;
            String value;
            if (inputBuilder.indexOf(",") > inputBuilder.indexOf("[") && inputBuilder.indexOf(",") < inputBuilder.indexOf("]")) {
                end = inputBuilder.indexOf("]");
                value = inputBuilder.substring(start, end + 1);
                end += 1;
            } else {
                end = inputBuilder.indexOf(",");
                value = inputBuilder.substring(start, end);
            }
            inputBuilder.delete(0, end + 1);
            processValue(key, value, pupil);
        }

        return pupil;
    }

    private static void processValue(String key, String value, Pupil pupil) throws PupilParseException, DuplicateSubjectException {
        switch (key){
            case "name":
                pupil.setStudentName(value);
                break;
            case "subjects":
                String[] subjects = getArrayFromString(value, String.class);
                if (pupil.getLength()>0) {
                    for (int i = 0; i < pupil.getLength(); i++) {
                        pupil.setSubject(i, subjects[i]);
                    }
                }else {
                    for (String subject : subjects) {
                        pupil.addNewSubject(subject, 5);
                    }
                }
                break;
            case "marks":
                Integer[] marks = getArrayFromString(value, Integer.class);
                if (pupil.getLength()>0) {
                    for (int i = 0; i < pupil.getLength(); i++) {
                        pupil.setMark(i, marks[i]);
                    }
                }else {
                    for (int i = 0; i < marks.length; i++) {
                        pupil.addNewSubject(String.valueOf(i), marks[i]);
                    }
                }
                break;
            default:
                throw new PupilParseException("Обнаружено неверное поле:" + key);

        }
    }

}
