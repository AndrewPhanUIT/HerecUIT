package uit.herec.hyperledger.Impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallet.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import uit.herec.common.dto.AllergyDto;
import uit.herec.common.dto.AppointmentDetailDto;
import uit.herec.common.dto.AppointmentDto;
import uit.herec.common.dto.DiagnosisDetailDto;
import uit.herec.common.dto.DiagnosisDto;
import uit.herec.common.message.Error;
import uit.herec.common.message.Success;
import uit.herec.hyperledger.Service;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{
    private final static Logger logger = LoggerFactory.getLogger(ServiceImpl.class);
    private final String rootPath = System.getProperty("user.dir");
    private final Gson gson = new Gson();
    
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }
    
    @Override
    public boolean enrollAdmin(String msp, String orgName, String caHost) {
        Properties props = new Properties();
        String pemFile = String.format("%s/fabric-network/crypto-config/peerOrganizations/%s.herec.uit/ca/ca.%s.herec.uit-cert.pem", this.rootPath, orgName.toLowerCase(), orgName.toLowerCase());
        logger.info("pemFile: " + pemFile);
        props.put("pemFile", pemFile);
        props.put("allowAllHostNames", "true");
        HFCAClient caClient;
        try {
            caClient = HFCAClient.createNewInstance(caHost, props);
            CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
            caClient.setCryptoSuite(cryptoSuite);
            Wallet wallet = Wallet.createFileSystemWallet(Paths.get(String.format("wallet%s", orgName)));
            
            boolean adminExists = wallet.exists("admin");
            if (adminExists) {
                logger.error("An identity for the admin user \"admin\" already exists in the wallet");
                return false;
            }

            // Enroll the admin user, and import the new identity into the wallet.
            final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
            enrollmentRequestTLS.addHost("localhost");
            enrollmentRequestTLS.setProfile("tls");
            Enrollment enrollment = caClient.enroll("admin", "adminpw", enrollmentRequestTLS);
            Identity user = Identity.createIdentity(msp, enrollment.getCert(), enrollment.getKey());
            wallet.put("admin", user);
            logger.info("Successfully enrolled user \"admin\" and imported it into the wallet");
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("Error while enrolling admin to " + orgName);
            return false;
        }
        return true;
    }
    @Override
    public boolean registerUser(String username, String msp, String orgName, String caHost, int departmentNumber) {
        try {
         // Create a CA client for interacting with the CA.
            Properties props = new Properties();
            String pemFile = String.format("%s/fabric-network/crypto-config/peerOrganizations/%s.herec.uit/ca/ca.%s.herec.uit-cert.pem", this.rootPath, orgName.toLowerCase(), orgName.toLowerCase());
            props.put("pemFile", pemFile);
            props.put("allowAllHostNames", "true");
            HFCAClient caClient = HFCAClient.createNewInstance(caHost, props);
            CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
            caClient.setCryptoSuite(cryptoSuite);
            Wallet wallet = Wallet.createFileSystemWallet(Paths.get(String.format("wallet%s", orgName)));
            boolean userExists = wallet.exists(username);
            if (userExists) {
                logger.error(String.format(Error.HYPERLEDGER_USER_IS_EXISTS, username));
                return false;
            }
            userExists = wallet.exists("admin");
            if (!userExists) {
                logger.error(Error.HYPERLEDGER_ADMIN_IS_NOT_EXISTS);
                return false;
            }
            Identity adminIdentity = wallet.get("admin");
            User admin = new User() {

                @Override
                public String getName() {
                    return "admin";
                }

                @Override
                public Set<String> getRoles() {
                    return null;
                }

                @Override
                public String getAccount() {
                    return null;
                }

                @Override
                public String getAffiliation() {
                    return "org1";
                }

                @Override
                public Enrollment getEnrollment() {
                    return new Enrollment() {

                        @Override
                        public PrivateKey getKey() {
                            return adminIdentity.getPrivateKey();
                        }

                        @Override
                        public String getCert() {
                            return adminIdentity.getCertificate();
                        }
                    };
                }

                @Override
                public String getMspId() {
                    return msp;
                }

            };

            // Register the user, enroll the user, and import the new identity into the wallet.
            RegistrationRequest registrationRequest = new RegistrationRequest(username);
            registrationRequest.setAffiliation("org1");
            registrationRequest.setEnrollmentID(username);
            String enrollmentSecret = caClient.register(registrationRequest, admin);
            Enrollment enrollment = caClient.enroll(username, enrollmentSecret);
            Identity user = Identity.createIdentity(msp, enrollment.getCert(), enrollment.getKey());
            wallet.put(username, user);
            logger.info(Success.HYPERLEDGER_ENROLL_NEW_USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
   
    
    public static void main(String[] args) {
//        new ServiceImpl().enrollAdmin("ClientMSP", "Client", "https://localhost:7054");
//        new ServiceImpl().registerUser("andrew", "ClientMSP", "Client", "https://localhost:7054", 1);
//        new ServiceImpl().queryAllDiagnosisByPhoneNumber("andrew", "ClientMSP", "Client", "herecchannel", "diagnosis", "0783550324");

        List<AllergyDto> allergies = new ArrayList<AllergyDto>();
        allergies.add(new AllergyDto("Paracetamol", "Nhẹ", "Ho"));
        DiagnosisDetailDto dto = new DiagnosisDetailDto("PT001", "Andrew Phan", "19971226", "0783550324", "Phường Tân Thới Nhất, Quận 12, Thành phố Hồ Chí Minh", null, "");
        DiagnosisDto diagnosisDto = new DiagnosisDto("D002", "ORG001", "Bệnh viện quận 12", "Phan Thế Anh", "20201201", allergies, new ArrayList<>(), new ArrayList<>());
        new ServiceImpl().addNewDiagnosis("ClientMSP", "Client", "https://localhost:7054", "herecchannel", "diagnosis", dto, diagnosisDto);
    }

    @Override
    public DiagnosisDetailDto queryAllDiagnosisByPhoneNumber(String username, String msp, String orgName,
            String channel, String chaincode, String phoneNumber) {
        DiagnosisDetailDto dto = new DiagnosisDetailDto();
        Path walletPath = Paths.get("wallet" + orgName);
        Wallet wallet = null;
        try {
            wallet = Wallet.createFileSystemWallet(walletPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path networkConfigPath = Paths.get(this.rootPath, "fabric-network", "connection-herec-client.json");
        Gateway.Builder builder = Gateway.createBuilder();
        try {
            builder.identity(wallet, username).networkConfig(networkConfigPath).discovery(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Gateway gateway = builder.connect()) {

            Network network = gateway.getNetwork(channel);
            Contract contract = network.getContract(chaincode);

            byte[] result;

            result = contract.evaluateTransaction("queryAllDiagnosisByPhoneNumber", phoneNumber);
            String strResult = new String(result);
            dto = gson.fromJson(strResult, DiagnosisDetailDto.class);
            java.lang.reflect.Type type = new TypeToken<DiagnosisDto[]>() {}.getType();
            DiagnosisDto[] lstDiagnosis = gson.fromJson(dto.getDiagnosis(), type);
            dto.setDiagnosisDtos(lstDiagnosis);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        return dto;
    }
    @Override
    public AppointmentDetailDto queryAllAppointmentsByPhoneNumber(String username, String msp, String orgName,
            String channel, String chaincode, String phoneNumber) {
        AppointmentDetailDto dto = new AppointmentDetailDto();
        Path walletPath = Paths.get("wallet" + orgName);
        Wallet wallet = null;
        try {
            wallet = Wallet.createFileSystemWallet(walletPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path networkConfigPath = Paths.get(this.rootPath, "fabric-network", "connection-herec-client.json");
        Gateway.Builder builder = Gateway.createBuilder();
        try {
            builder.identity(wallet, username).networkConfig(networkConfigPath).discovery(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Gateway gateway = builder.connect()) {
            Network network = gateway.getNetwork(channel);
            Contract contract = network.getContract(chaincode);
            byte[] result;
            result = contract.evaluateTransaction("queryAllAppointmentsByPhoneNumber", phoneNumber);
            String strResult = new String(result);
            dto = gson.fromJson(strResult, AppointmentDetailDto.class);
            java.lang.reflect.Type type = new TypeToken<AppointmentDto[]>() {}.getType();
            AppointmentDto[] lstAppoiments = gson.fromJson(dto.getAppointments(), type);
            dto.setAppointmentDtos(lstAppoiments);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        return dto;
    }
    @Override
    public boolean addNewDiagnosis(String msp, String orgName, String caHost, String channel, String chaincode,
            DiagnosisDetailDto dto, DiagnosisDto diagnosisDto) {
        Path walletPath = Paths.get("wallet" + orgName);
        Wallet wallet = null;
        try {
            wallet = Wallet.createFileSystemWallet(walletPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path networkConfigPath = Paths.get(this.rootPath, "fabric-network", String.format("connection-herec-client.json", orgName.toLowerCase()));
        Gateway.Builder builder = Gateway.createBuilder();
        try {
            builder.identity(wallet, "andrew").networkConfig(networkConfigPath).discovery(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Gateway gateway = builder.connect()) {
            Network network = gateway.getNetwork("herecchanel");
            Contract contract = network.getContract("diagnosis");
            String patientInfo = gson.toJson(dto);
            String diagnosis = gson.toJson(diagnosisDto);
//            contract.submitTransaction("addNewDiagnosisRecord", patientInfo, diagnosis);
            contract.submitTransaction("initLedger", "");

            
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean addNewAppoiment(String msp, String orgName, String caHost, String channel, String chaincode,
            AppointmentDetailDto dto, AppointmentDto appointmentDto) {
        Path walletPath = Paths.get("wallet" + orgName);
        Wallet wallet = null;
        try {
            wallet = Wallet.createFileSystemWallet(walletPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path networkConfigPath = Paths.get(this.rootPath, "fabric-network", String.format("connection-herec-%s.json", orgName.toLowerCase()));
        Gateway.Builder builder = Gateway.createBuilder();
        try {
            builder.identity(wallet, orgName.toLowerCase()).networkConfig(networkConfigPath).discovery(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Gateway gateway = builder.connect()) {
            Network network = gateway.getNetwork(channel);
            Contract contract = network.getContract(chaincode);
            String patientInfo = gson.toJson(dto);
            String appointment = gson.toJson(appointmentDto);
            contract.submitTransaction("addNewAppointmentRecord", patientInfo, appointment);
        } catch (ContractException e) {
            e.printStackTrace();
            return false;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    
}
