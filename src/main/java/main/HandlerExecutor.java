package main;

import json.OrdersPrinter;

import java.util.Queue;

public interface HandlerExecutor {

    void execute(Queue<FileParser> parsersPool);

    void execute(OrdersPrinter ordersPrinter);
}
