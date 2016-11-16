import error.DuplicateSubjectException;
import error.PupilParseException;
import pupil.Pupil;
import pupil.impl.Schoolboy;

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
        out.write(v.getJsonView().getBytes());
    }

    public static Pupil inputPupil(InputStream is) throws IOException, PupilParseException, DuplicateSubjectException {
        int data;
        char content;

        StringBuilder stringBuilder = new StringBuilder();
        do {
            data = is.read();
            content = (char) data;
            stringBuilder.append(content);
        } while (content != '}');

        return convertToPupil(stringBuilder.toString());
    }

    public static void writePupil(Pupil v, Writer writer) throws IOException {
        writer.write(v.getJsonView());
    }

    public static Pupil readPupil(Reader in) throws PupilParseException, IOException, DuplicateSubjectException {
        int data;
        char content;

        StringBuilder stringBuilder = new StringBuilder();
        do {
            data = in.read();
            content = (char) data;
            stringBuilder.append(content);
        } while (content != '}');

        return convertToPupil(stringBuilder.toString());
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

    public static double getAverageOfMarks(Pupil... pupils) {
        double sum = 0;
        int length = 0;
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
}
