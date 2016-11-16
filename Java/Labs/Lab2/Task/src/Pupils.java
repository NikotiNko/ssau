/**
 * @author 1
 * @since 06.09.2016.
 */
public class Pupils {

    public static void main(String[] args) {
        Pupil first = new Student("Никитин", 5);
     //   Pupil second = new Schoolboy("Никитин", 0);

        doTask(first);
      //  doTask(second);
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

    public static void doTask(Pupil pupil) {
        try {
            pupil.setSubject(2, "физика");
         //   pupil.addNewSubject("Матан", (int)(Math.random()*4 + 2));
        //    pupil.addNewSubject("ООП",  (int)(Math.random()*4 + 2));
  //          pupil.addNewSubject("Физика", (int)(Math.random()*4 + 2));
//            pupil.addNewSubject("Физика", (int)(Math.random()*4 + 2));
            pupil.setSubject(3, "физика");
//            pupil.setMark(1, 10);

            printRegister(pupil);
            System.out.println("Среднее арифметическое:" + getAverangeOfMarks(pupil));

        }
        catch (DuplicateSubjectException | MarkOutOfBoundsException ex){
            System.err.println("Обнаружена ошибка: " + ex.getMessage());
        }
        finally {
            System.out.println("-----------------------------------------------");
        }

    }

}
