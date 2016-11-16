package thread;

import pupil.Pupil;

/**
 * Created by Shpulka on 20.09.2016.
 */
public class RunnableMarksWriter implements Runnable {

    private PupilSynchronizer synchronizer;

    public RunnableMarksWriter(PupilSynchronizer synchronizer) {
        this.synchronizer = synchronizer;
    }


    @Override
    public void run() {
        try {
            while (synchronizer.printMark());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
