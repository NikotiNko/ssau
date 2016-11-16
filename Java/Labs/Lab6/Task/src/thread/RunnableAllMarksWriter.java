package thread;

import pupil.Pupil;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 1
 * @since 20.09.2016.
 */
public class RunnableAllMarksWriter implements Runnable {

    private Pupil pupil;
    private ReentrantLock lock;
    private Condition waiter;

    public RunnableAllMarksWriter(Pupil pupil, ReentrantLock lock, Condition waiter) {
        this.pupil = pupil;
        this.lock = lock;
        this.waiter = waiter;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            if (!lock.hasWaiters(waiter))
                waiter.awaitUninterruptibly();
            pupil.printMarks();
            waiter.signalAll();
        }finally {
            lock.unlock();
        }

    }
}
