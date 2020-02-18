package uit.herec;

import java.io.File;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import uit.herec.common.Utils;
import uit.herec.common.form.RegisterForm;
import uit.herec.controller.auth.AuthController;
import uit.herec.dao.repository.AllergyRepository;
import uit.herec.dao.repository.AppUserRepository;
import uit.herec.dao.repository.AppointmentRepository;
import uit.herec.dao.repository.DiagnosisRepository;
import uit.herec.dao.repository.MedicationRepository;
import uit.herec.hyperledger.Cmd;
import uit.herec.hyperledger.Service;

@Component
public class Initializer{

    @Autowired
    private Cmd hyperledgerCmd;
    
    @Autowired
    private Service hyperledgerService;
    
    @Autowired
    private AppUserRepository userRepository;
    
    @Autowired
    private DiagnosisRepository diagnosisRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private AllergyRepository allergyRepository;
    
    @Autowired
    private MedicationRepository medicationRepository;
    
    @Value("${hyperledger.port.client}")
    private String clientPort;
    
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTF-8"));
//        this.hyperledgerCmd.startServer();
        this.removePrevData();
        this.initNewData();
    }
    
    private void removePrevData() {
        String rootPath = System.getProperty("user.dir");
        File clientWallet = new File(rootPath + "/walletClient");
        Utils.deleteFolder(clientWallet);
//        File quan12Wallet = new File(rootPath + "/walletQuan12");
//        Utils.deleteFolder(quan12Wallet);
        
        this.userRepository.deleteAll();
        this.appointmentRepository.deleteAll();
        this.diagnosisRepository.deleteAll();
        this.allergyRepository.deleteAll();
        this.medicationRepository.deleteAll();
    }
    
    private void initNewData() {
        this.hyperledgerService.enrollAdmin("ClientMSP", "Client", this.clientPort);
//        RegisterForm form = new RegisterForm("Phan Thế Anh", "AnhPmcl2015", "0783550324");
//        new AuthController().register(form);
    }
    
}
