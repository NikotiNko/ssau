package thread;

import pupil.Pupil;

/**
 * Created by Shpulka on 20.09.2016.
 */
public class PupilSynchronizer {

    private Pupil v;
    private volatile int current = 0;
    private Object lock = new Object();
    private boolean set = false;

    public PupilSynchronizer(Pupil v) {
        this.v = v;
    }

    public boolean printMark() throws InterruptedException {
        int val;
        synchronized(lock) {
            if (!canRead()) return false;
            while (!set)
                lock.wait();
            val = v.getMark(current++);
            System.out.println("Print mark: " + val);
            set = false;
            lock.notifyAll();
        }
        return true;
    }

    public boolean printSubject() throws InterruptedException {
        synchronized(lock) {
            if (!canWrite()) return false;
            while (set)
                lock.wait();
            System.out.println("Print subject: " + v.getSubject(current));
            set = true;
            lock.notifyAll();
            return true;
        }
    }

    public boolean canRead() {
        return current < v.getLength();
    }

    public boolean canWrite() {
        return (!set && current < v.getLength()) || (set && current < v.getLength() - 1);
    }
}