package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

@Service("commandLine")
class CommandLineArgs implements Runnable {

    @CommandLine.Parameters(index = "0..*")
    private String[] files;
    private ParsersExecutor parsersExecutor;

    @Autowired
    public void setExecutorsParsers(ParsersExecutor parsersExecutor){
        this.parsersExecutor = parsersExecutor;
    }

    @Override
    public void run() {
        if(files.length != 0) {
            parsersExecutor.execute(files);
        }
    }
}
