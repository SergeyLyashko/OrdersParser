package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

@Service("commandLine")
class CommandLineArgs implements Runnable {

    @CommandLine.Parameters(index = "0..*")
    private String[] files;

    private FileParse fileParse;

    @Autowired
    public void setExecutorParse(FileParse fileParse){
        this.fileParse = fileParse;
    }

    @Override
    public void run() {
        fileParse.execute(files);
    }
}
