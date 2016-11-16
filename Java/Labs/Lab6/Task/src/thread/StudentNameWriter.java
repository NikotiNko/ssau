package thread;

import pupil.Pupil;

/**
 * @author 1
 * @since 21.09.2016.
 */
public class StudentNameWriter implements Runnable {

    private Pupil pupil;

    public StudentNameWriter(Pupil pupil) {
        this.pupil = pupil;
    }

    @Override
    public void run() {
        System.out.println(pupil.getStudentName());
    }
}
