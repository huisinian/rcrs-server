import java.time.LocalDateTime;

public class ThreadDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 当前时间：" + LocalDateTime.now());
        }, "线程1");

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 当前时间：" + LocalDateTime.now());
        }, "线程2");

        Thread t3 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 当前时间：" + LocalDateTime.now());
        }, "线程3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有子线程执行完毕，主线程结束");
    }
}