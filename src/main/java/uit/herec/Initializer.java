package uit.herec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import uit.herec.hyperledger.Cmd;

public class Initializer implements CommandLineRunner{

    @Autowired
    private Cmd hyperledgerCmd;
    
    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        hyperledgerCmd.startServer();
    }
    
}
