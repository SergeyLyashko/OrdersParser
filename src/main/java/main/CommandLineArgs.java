package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

@Service("commandLine")
class CommandLineArgs implements Runnable {

    @CommandLine.Parameters(index = "0..*")
    private String[] files;
    private HandleExecutor handleExecutor;

    @Autowired
    public void setHandleExecutor(HandleExecutor handleExecutor){
        this.handleExecutor = handleExecutor;
    }

    @Override
    public void run() {
        handleExecutor.execute(files);
    }
}
