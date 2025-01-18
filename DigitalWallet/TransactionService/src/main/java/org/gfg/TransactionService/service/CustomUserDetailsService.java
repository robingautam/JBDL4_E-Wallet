package org.gfg.TransactionService.service;

import org.gfg.CommonConstants;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    RestTemplate restTemplate;


    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Inside custom user details service");
        String api = "http://localhost:8082/onboarding-service/validate/user?userId="+username;
        String response = restTemplate.getForObject(api, String.class);

        System.out.println("API Response: "+response);

        JSONObject jsonResponse = new JSONObject(response);
        String status = jsonResponse.optString(CommonConstants.STATUS);
        if ("06".equals(status)){
            System.out.println("User does not exists");
            return null;
        }else {
            String user = jsonResponse.optString(CommonConstants.USER_MOBILE);
            String password = jsonResponse.optString(CommonConstants.USER_PASSWORD);

            UserDetails userDetails = User.builder().username(username).password(password).build();
            return userDetails;
        }
    }
}
