import java.time.LocalDateTime;

public class ThreadDemoLambda {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> System.out.println(Thread.currentThread().getName() + " 当前时间：" + LocalDateTime.now());

        Thread t1 = new Thread(task, "线程1");
        Thread t2 = new Thread(task, "线程2");
        Thread t3 = new Thread(task, "线程3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println("所有子线程执行完毕");
    }
}