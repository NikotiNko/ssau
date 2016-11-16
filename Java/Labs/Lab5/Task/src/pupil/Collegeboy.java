package pupil;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Shpulka on 17.09.2016.
 */
public class Collegeboy {

    private String studentName;
    private Map<String, Integer> subjects;

    public Collegeboy(String studentName) {
        this.studentName = studentName;
        subjects = new HashMap<>();
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Set<String> getSubjects() {
        return subjects.keySet();
    }

    public Collection<Integer> getMarks() {
        return subjects.values();
    }

    public Integer getMark(String subject){
        return subjects.get(subject);
    }

    public void addSubject(String subjectName, int mark){
        subjects.put(subjectName, mark);
    }

    public int getLength(){
        return subjects.size();
    }

    public void printSubjects(){
        for (String subject : subjects.keySet()) {
            System.out.println(subject + "; ");
        }
    }

    public void printMarks(){
        for (Integer mark : subjects.values()) {
            System.out.println(mark + "; ");
        }
    }
}
