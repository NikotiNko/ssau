package pupil;

import error.DuplicateSubjectException;
import error.MarkOutOfBoundsException;

import java.util.Arrays;

/**
 * @author 1
 * @since 04.09.2016.
 */
public class Student implements Pupil {

    private int[] marks;
    private String[] subjects;
    private String studentName;

    public Student(String studentName, int size) {
        marks = new int[size];
        subjects = new String[size];
        for (int i = 0; i < size; i++) {
            subjects[i] = "";
        }
        this.studentName = studentName;
    }

    @Override
    public String getSubject(int index) {
        return subjects[index];
    }

    @Override
    public void setSubject(int index, String value) throws DuplicateSubjectException {
        for (int i = 0; i < subjects.length; i++) {
            if (subjects[i].equals(value) && i != index) throw new DuplicateSubjectException();
        }
        subjects[index] = value;
    }

    @Override
    public int getMark(int index) {
        return marks[index];
    }

    @Override
    public void setMark(int index, int value) {
        if (value > 5 || value < 2) throw new MarkOutOfBoundsException();
        marks[index] = value;
    }

    @Override
    public String getStudentName() {
        return studentName;
    }

    @Override
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public void printMarks() {
        for (int mark: marks){
            System.out.print(mark + "; ");
        }
        System.out.println();
    }

    @Override
    public void printSubjects() {
        for (String subject: subjects){
            System.out.print(subject + "; ");
        }
        System.out.println();
    }

    @Override
    public int getLength() {
        return marks.length > subjects.length ? marks.length : subjects.length;
    }

    @Override
    public void addNewSubject(String subject, int mark) throws DuplicateSubjectException {
        if (mark > 5 || mark < 2) throw new MarkOutOfBoundsException();
        for (int i = 0; i <subjects.length; i++) {
            if (subjects[i].equals(subject)) throw new DuplicateSubjectException();
        }

        subjects = Arrays.copyOf(subjects, subjects.length + 1);
        marks = Arrays.copyOf(marks, marks.length + 1);

        subjects[subjects.length - 1] = subject;
        marks[marks.length - 1] = mark;
    }

}
