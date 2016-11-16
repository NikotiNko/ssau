/**
 * @author 1
 * @since 05.09.2016.
 */
public interface Pupil {

    public String getSubject(int index);

    public void setSubject(int index, String value) throws DuplicateSubjectException;

    public int getMark(int index);

    public void setMark(int index, int value);

    public String getStudentName();

    public void setStudentName(String studentName);

    public void printMarks();

    public void printSubjects();

    public int getLength();

    public void addNewSubject(String subject, int mark) throws DuplicateSubjectException;
}
