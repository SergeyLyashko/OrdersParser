package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

@Service("commandLine")
class CommandLineArgs implements Runnable {

    @CommandLine.Parameters(index = "0..*")
    private String[] files;

    private ExecutorParse executorParse;

    @Autowired
    public void setExecutorParse(ExecutorParse executorParse){
        this.executorParse = executorParse;
    }

    @Override
    public void run() {
        executorParse.execute(files);
    }
}
