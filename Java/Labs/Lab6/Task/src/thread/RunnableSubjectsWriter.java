package thread;

/**
 * Created by Shpulka on 20.09.2016.
 */
public class RunnableSubjectsWriter implements Runnable {

    private PupilSynchronizer synchronizer;

    public RunnableSubjectsWriter(PupilSynchronizer synchronizer) {
        this.synchronizer = synchronizer;
    }


    @Override
    public void run() {
        try {
            while (synchronizer.printSubject());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
