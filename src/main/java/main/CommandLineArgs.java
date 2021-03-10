package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

@Service("commandLine")
class CommandLineArgs implements Runnable {

    @CommandLine.Parameters(index = "0..*")
    private String[] files;
    private ExecutorParsers executorParsers;

    @Autowired
    public void setExecutorsParsers(ExecutorParsers executorParsers){
        this.executorParsers = executorParsers;
    }

    @Override
    public void run() {
        if(files.length != 0) {
            executorParsers.execute(files);
        }
    }
}
