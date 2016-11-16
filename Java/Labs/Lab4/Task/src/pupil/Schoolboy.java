package pupil;

import error.DuplicateSubjectException;
import error.MarkOutOfBoundsException;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author 1
 * @since 04.09.2016.
 */
public class Schoolboy implements Pupil{

    private Register[] registers;
    private String studentName;

    public Schoolboy(String studentName, int size) {
        registers = new Register[size];
        for (int i = 0; i < size; i++) {
            registers[i] = new Register();
        }
        this.studentName = studentName;
    }

    @Override
    public String getSubject(int index) {
        return registers[index].subject;
    }

    @Override
    public void setSubject(int index, String value) throws DuplicateSubjectException {
        for (int i = 0; i < registers.length; i++) {
            if (registers[i].subject.equals(value) && i!=index) throw new DuplicateSubjectException();
        }
        registers[index].subject = value;
    }

    @Override
    public int getMark(int index) {
        return registers[index].mark;
    }

    @Override
    public void setMark(int index, int value) {
        if (value > 5 || value < 2) throw new MarkOutOfBoundsException();
        registers[index].mark = value;
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
        for (Register register : registers) {
            System.out.print(register.mark + "; ");
        }
        System.out.println();
    }

    @Override
    public void printSubjects() {
        for (Register register : registers) {
            System.out.print(register.subject + "; ");
        }
        System.out.println();
    }

    @Override
    public int getLength() {
        return registers.length;
    }

    @Override
    public void addNewSubject(String subject, int mark) throws DuplicateSubjectException {
        if (mark > 5 || mark < 2) throw new MarkOutOfBoundsException();
        for (Register register : registers) {
            if (register.subject.equals(subject)) throw new DuplicateSubjectException();
        }
        registers = Arrays.copyOf(registers, registers.length + 1);
        registers[registers.length - 1] = new Register(subject, mark);
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Student{")
                .append("studentName='")
                .append(studentName)
                .append('\'')
                .append(", subjects=")
                .append(Arrays.toString(registers))
                .append("}");
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Pupil)) return false;

        Pupil student = (Pupil) o;

        for (int i = 0; i < student.getLength(); i++) {
            if (!student.getSubject(i).equals(this.registers[i].subject) || student.getMark(i) != this.registers[i].mark) {
                return false;
            }
        }
        return studentName != null ? studentName.equals(student.getStudentName()) : student.getStudentName() == null;

    }

    @Override
    public int hashCode() {
        int result = studentName != null ? studentName.hashCode() : 0;
        result = 31 * result + (registers != null ? Arrays.hashCode(registers) : 0);
        return result;
    }

    @Override
    public Schoolboy clone() throws CloneNotSupportedException {
        Schoolboy clone = (Schoolboy) super.clone();
        clone.registers = new Register[registers.length];
        for (int i = 0; i < registers.length; i++) {
            clone.registers[i] = registers[i].clone();
        }
        clone.studentName = studentName;
        return clone;
    }

    private class Register implements Serializable, Cloneable {
        private int mark;
        private String subject;

        public Register(String subject, int mark) {
            this.subject = subject;
            this.mark = mark;
        }

        public Register() {
            subject = "";
        }

        @Override
        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("{")
                    .append("mark='")
                    .append(mark)
                    .append("\', subject='")
                    .append(subject)
                    .append("\'}");
            return stringBuffer.toString();
        }

        @Override
        public int hashCode() {
            int result = mark;
            result = 31 * result + (subject != null ? subject.hashCode() : 0);
            return result;
        }

        @Override
        protected Register clone() throws CloneNotSupportedException {
            Register clone = (Register) super.clone();
            clone.subject = this.subject;
            clone.mark = this.mark;
            return clone;
        }

    }
}
