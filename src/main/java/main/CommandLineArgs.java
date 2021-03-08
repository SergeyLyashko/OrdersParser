package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

@Service("commandLine")
class CommandLineArgs implements Runnable {

    @CommandLine.Parameters(index = "0..*")
    private String[] files;
    private ExecutorsParsers executorsParsers;

    @Autowired
    public void setExecutorsParsers(ExecutorsParsers executorsParsers){
        this.executorsParsers = executorsParsers;
    }

    @Override
    public void run() {
        executorsParsers.execute(files);
    }
}
