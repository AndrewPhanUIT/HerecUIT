package uit.herec.controller.auth;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import uit.herec.common.APIConstants;
import uit.herec.common.Constant;
import uit.herec.common.dto.ApiResponseDto;
import uit.herec.common.dto.JwtAuthenticationResponseDto;
import uit.herec.common.form.Form;
import uit.herec.common.form.LoginForm;
import uit.herec.common.form.RegisterForm;
import uit.herec.common.message.Success;
import uit.herec.config.security.JwtTokenProvider;
import uit.herec.hyperledger.Service;
import uit.herec.service.IUserService;

@RestController
@RequestMapping(APIConstants.AUTH_API)
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private Service hyperlegderService;
    
    @Value("${hyperledger.port.client}")
    private String clientPort;
    
    @Value("${hyperledger.client}")
    private String clientOrg;
    
    @Value("${hyperledger.msp.client}")
    private String clientMsp;
    
    @PostMapping(APIConstants.LOGIN)
    public ResponseEntity<ApiResponseDto> login(@Valid @RequestBody LoginForm form) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(form.getPhoneNumber(), form.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = tokenProvider.generateToken(auth);
        String hyperledgerName = this.userService.getHyperledgerName(form.getPhoneNumber());
        ApiResponseDto dto = new ApiResponseDto(true,  Arrays.asList(new JwtAuthenticationResponseDto(jwt, hyperledgerName, form.getPhoneNumber())), Success.LOGIN);
        return ResponseEntity.ok(dto);
    }
    
    @PostMapping(APIConstants.REGISTER)
    public ResponseEntity<ApiResponseDto> register(@Valid @RequestBody RegisterForm form) {
        ApiResponseDto dto = this.userService.registerPatient(form);
        if(dto.isSuccess()) {
            hyperlegderService.registerUser("user" + form.getPhoneNumber(), "ClientMSP", "Client", "7054", Constant.HYPERLEDGER_CLIENT_DEPARTMENT_INDEX);
            Constant.HYPERLEDGER_CLIENT_DEPARTMENT_INDEX++;
            return ResponseEntity.ok(dto);
        }
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/test")
    public ResponseEntity<ApiResponseDto> test(@RequestBody String json) {
        String temp = json.substring(1, json.length() - 1);
        Gson gson = new Gson();
        Form form = gson.fromJson(temp, Form.class);
        System.out.println(form.getCode());
        return ResponseEntity.ok(null);
    }
    
    @GetMapping("/validate-user")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<ApiResponseDto> validate() {
        return ResponseEntity.ok(null); 
    }
}
