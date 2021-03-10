package json;

import java.util.concurrent.CountDownLatch;

public interface OrdersPrinter extends Runnable {

    void setCountDownLatch(CountDownLatch countDownLatch);
}
