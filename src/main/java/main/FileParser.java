package main;

import java.util.concurrent.CountDownLatch;

public interface FileParser extends Runnable {

    void addParsedFile(String fileName);

    void setCountDownLatch(CountDownLatch countDownLatch);
}
