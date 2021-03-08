package main;

import java.util.concurrent.CountDownLatch;

public interface OrdersParser extends Runnable {

    void setFile(String fileName);

    void setCountDownLatch(CountDownLatch countDownLatch);
}
