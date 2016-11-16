package thread;

import pupil.Pupil;

/**
 * Created by Shpulka on 20.09.2016.
 */
public class MarksWriter extends Thread {

    private Pupil pupil;

    public MarksWriter(Pupil pupil) {
        this.pupil = pupil;
    }

    @Override
    public void run() {
        for (int i = 0; i < pupil.getLength(); i++) {
            System.out.print(pupil.getMark(i) + " ");
        }
    }
}
