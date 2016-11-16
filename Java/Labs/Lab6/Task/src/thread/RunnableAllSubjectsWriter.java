package thread;

import pupil.Pupil;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 1
 * @since 20.09.2016.
 */
public class RunnableAllSubjectsWriter implements Runnable {

    private Pupil pupil;
    private ReentrantLock lock;
    private Condition waiter;

    public RunnableAllSubjectsWriter(Pupil pupil, ReentrantLock lock, Condition waiter) {
        this.pupil = pupil;
        this.lock = lock;
        this.waiter = waiter;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            pupil.printSubjects();
            waiter.signalAll();
            waiter.awaitUninterruptibly();
        }finally {
            lock.unlock();
        }
    }
}
