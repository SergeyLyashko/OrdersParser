package executors;

import java.util.concurrent.CountDownLatch;

public interface OrdersRunnableIO extends Runnable {

    void setFile(String fileName);

    void setCountDownLatch(CountDownLatch countDownLatch);
}
