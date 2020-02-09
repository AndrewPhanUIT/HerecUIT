package uit.herec.hyperledger.Impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallet.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import uit.herec.common.dto.AppointmentDetailDto;
import uit.herec.common.dto.AppointmentDto;
import uit.herec.common.dto.ChaincodeScript;
import uit.herec.common.dto.DiagnosisDetailDto;
import uit.herec.common.dto.DiagnosisDto;
import uit.herec.common.exception.BadRequestException;
import uit.herec.common.message.Error;
import uit.herec.common.message.Success;
import uit.herec.dao.entity.AppUser;
import uit.herec.dao.entity.Organization;
import uit.herec.dao.repository.AppUserRepository;
import uit.herec.dao.repository.OrganizationRepository;
import uit.herec.hyperledger.Cmd;
import uit.herec.hyperledger.Service;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{
    private final static Logger logger = LoggerFactory.getLogger(ServiceImpl.class);
    private final String rootPath = System.getProperty("user.dir");
    private final Gson gson = new Gson();
    
    @Autowired
    private Cmd cmd;
    
    @Autowired
    private OrganizationRepository orgRepository;
    
    @Autowired
    private AppUserRepository userRepository;
    
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
            caClient = HFCAClient.createNewInstance("https://localhost:" + caHost, props);
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
    
    public static void main(String[] args) {
//        new ServiceImpl().enrollAdmin("ClientMSP", "Client", "7054");
//        new ServiceImpl().registerUser("andrew", "ClientMSP", "Client", "7054", 1);
//        new ServiceImpl().queryAllDiagnosisByPhoneNumber("andrew", "ClientMSP", "Client", "herecchannel", "diagnosis", "0783550324");
        new ServiceImpl().queryAllAppointmentsByPhoneNumber("andrew", "ClientMSP", "Client", "herecchannel", "diagnosis", "0783550324");

    }
    
    @Override
    public boolean registerUser(String username, String msp, String orgName, String caHost, int departmentNumber) {
        try {
         // Create a CA client for interacting with the CA.
            Properties props = new Properties();
            String pemFile = String.format("%s/fabric-network/crypto-config/peerOrganizations/%s.herec.uit/ca/ca.%s.herec.uit-cert.pem", this.rootPath, orgName.toLowerCase(), orgName.toLowerCase());
            props.put("pemFile", pemFile);
            props.put("allowAllHostNames", "true");
            HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:" + caHost, props);
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
            logger.info(String.format(Success.HYPERLEDGER_ENROLL_NEW_USER, username));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
   
    @Override
    public List<DiagnosisDetailDto> queryAllDiagnosisByPhoneNumber(String username, String msp, String orgName,
            String channel, String chaincode, String phoneNumber) {
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
            java.lang.reflect.Type type = new TypeToken<List<DiagnosisDetailDto>>() {}.getType();
            return gson.fromJson(strResult, type);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    @Override
    public List<AppointmentDetailDto> queryAllAppointmentsByPhoneNumber(String username, String msp, String orgName,
            String channel, String chaincode, String phoneNumber) {
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
            result = contract.evaluateTransaction("queryAllAppointmentByPhoneNumber", phoneNumber);
            String strResult = new String(result);
            java.lang.reflect.Type type = new TypeToken<List<AppointmentDetailDto>>() {}.getType();
            return gson.fromJson(strResult, type);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    @Override
    public boolean addNewDiagnosis(String orgName, String channel, String phoneNumber, DiagnosisDto diagnosisDto) {
        Organization org = this.orgRepository.findByHyperledgerNameIgnoreCase(orgName)
                .orElseThrow(() -> new BadRequestException(String.format(Error.ORG_NAME_IS_NOT_FOUND, orgName)));
        String peerName = "peer0";
        String peerPort = org.getPort();
        String chaincode = "diagnosis";
        List<Object> scriptArgs = new ArrayList<>();
        scriptArgs.add(phoneNumber);
        scriptArgs.add(diagnosisDto);
        ChaincodeScript script = new ChaincodeScript("addNewDiagnosisRecord", scriptArgs);
        List<String> orgs = new ArrayList<>();
        List<String> ports = new ArrayList<>();
        List<String> peers = new ArrayList<>();
        AppUser appUser = this.userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new BadRequestException(String.format(Error.PHONE_NUMBER_NOT_FOUND, phoneNumber)));
        orgs.add("Client");
        ports.add(appUser.getPort());
        peers.add(appUser.getPeerName());
        Set<Organization> organizations = appUser.getOrganizations();
        for(Organization organization : organizations) {
            orgs.add(organization.getHyperledgerName());
            ports.add(organization.getPort());
            peers.add("peer0");
        }
        return this.cmd.invokeChaincode(orgName, peerName, peerPort, channel, chaincode, script, orgs, ports, peers);
    }
    
    @Override
    public boolean addNewAppoiment(String orgName, String channel, String phoneNumber, AppointmentDto appointmentDto) {
        Organization org = this.orgRepository.findByHyperledgerNameIgnoreCase(orgName)
                .orElseThrow(() -> new BadRequestException(String.format(Error.ORG_NAME_IS_NOT_FOUND, orgName)));
        String peerName = "peer0";
        String peerPort = org.getPort();
        String chaincode = "diagnosis";
        List<Object> scriptArgs = new ArrayList<>();
        scriptArgs.add(phoneNumber);
        scriptArgs.add(appointmentDto);
        ChaincodeScript script = new ChaincodeScript("addNewAppointmentRecord", scriptArgs);
        List<String> orgs = new ArrayList<>();
        List<String> ports = new ArrayList<>();
        List<String> peers = new ArrayList<>();
        AppUser appUser = this.userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new BadRequestException(String.format(Error.PHONE_NUMBER_NOT_FOUND, phoneNumber)));
        orgs.add("Client");
        ports.add(appUser.getPort());
        peers.add(appUser.getPeerName());
        Set<Organization> organizations = appUser.getOrganizations();
        for(Organization organization : organizations) {
            orgs.add(organization.getHyperledgerName());
            ports.add(organization.getPort());
            peers.add("peer0");
        }
        return this.cmd.invokeChaincode(orgName, peerName, peerPort, channel, chaincode, script, orgs, ports, peers);
    }

    @Override
    public DiagnosisDetailDto queryDiagnosis(String userName, String orgName, String channel, String chaincode,
            String key) {
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
            builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Gateway gateway = builder.connect()) {
            Network network = gateway.getNetwork(channel);
            Contract contract = network.getContract(chaincode);
            byte[] result;
            result = contract.evaluateTransaction("queryDiagnosis", key);
            DiagnosisDetailDto dto = gson.fromJson(new String(result), DiagnosisDetailDto.class);
            return dto;
        } catch (ContractException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AppointmentDetailDto queryAppointment(String userName, String orgName, String channel, String chaincode,
            String key) {
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
            builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Gateway gateway = builder.connect()) {
            Network network = gateway.getNetwork(channel);
            Contract contract = network.getContract(chaincode);
            byte[] result;
            result = contract.evaluateTransaction("queryAppointment", key);
            AppointmentDetailDto dto = gson.fromJson(new String(result), AppointmentDetailDto.class);
            return dto;
        } catch (ContractException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
}
