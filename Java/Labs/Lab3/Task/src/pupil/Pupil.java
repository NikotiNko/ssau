package pupil;

import error.DuplicateSubjectException;

import java.io.Serializable;

/**
 * @author 1
 * @since 05.09.2016.
 */
public interface Pupil extends Serializable{

    String getSubject(int index);

    void setSubject(int index, String value) throws DuplicateSubjectException;

    int getMark(int index);

    void setMark(int index, int value);

    String getStudentName();

    void setStudentName(String studentName);

    void printMarks();

    void printSubjects();

    int getLength();

    void addNewSubject(String subject, int mark) throws DuplicateSubjectException;

    default String getJsonView(){
        StringBuilder outputValue = new StringBuilder();

        outputValue.append("{");
        outputValue.append("name: " + this.getStudentName());

        outputValue.append(", subjects: [");
        if (this.getLength()>0){
            outputValue.append(this.getSubject(0));
        }
        for (int i = 1; i < this.getLength(); i++) {
            outputValue.append("," + this.getSubject(i));
        }
        outputValue.append("]");

        outputValue.append(", marks: [");
        if (this.getLength()>0){
            outputValue.append(this.getMark(0));
        }
        for (int i = 1; i < this.getLength(); i++) {
            outputValue.append(","+ this.getMark(i));
        }
        outputValue.append("]");
        outputValue.append("}");
        return outputValue.toString();
    }
}
