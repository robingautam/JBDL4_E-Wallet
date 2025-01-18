package org.gfg.OnboardingService.service;

import org.gfg.CommonConstants;
import org.gfg.OnboardingService.model.User;
import org.gfg.OnboardingService.model.UserRole;
import org.gfg.OnboardingService.model.UserStatus;
import org.gfg.OnboardingService.repository.UserRepository;
import org.gfg.OnboardingService.request.UserCreationRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public User onboardNewUser(UserCreationRequest userCreationRequest){
        User user = new User();
        user.setName(userCreationRequest.getName());
        user.setEmail(userCreationRequest.getEmail());
        user.setPhone(userCreationRequest.getPhoneNo());
        user.setUserIdentifier(userCreationRequest.getUserIdentifier());
        user.setUserIdentifierValue(userCreationRequest.getUserIdentifierValue());
        user.setUserStatus(UserStatus.ACTIVE);
        user.setRole(UserRole.NORMAL);
        user.setDob(userCreationRequest.getDob());
        user.setAddress(userCreationRequest.getAddress());


        user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));

        // going to save the user in db
        User savedUser = userRepository.save(user);

        JSONObject userDetails = new JSONObject();
        userDetails.put(CommonConstants.USER_NAME,user.getName());
        userDetails.put(CommonConstants.USER_EMAIL,user.getEmail());
        userDetails.put(CommonConstants.USER_MOBILE,user.getPhone());
        userDetails.put(CommonConstants.USER_IDENTIFIER,user.getUserIdentifier());
        userDetails.put(CommonConstants.USER_IDENTIFIER_VALUE,user.getUserIdentifierValue());
        userDetails.put(CommonConstants.USER_ID,savedUser.getId());
        kafkaTemplate.send(CommonConstants.USER_DETAILS_TOPIC,userDetails.toString());

        return savedUser;


    }


    public String fetchUserFromUserService(String userMobile){
        User user = userRepository.findByPhone(userMobile);
        if (user==null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CommonConstants.STATUS,"06");
            return jsonObject.toString();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CommonConstants.USER_MOBILE,user.getPhone());
        jsonObject.put(CommonConstants.USER_PASSWORD,user.getPassword());

        return jsonObject.toString();
    }
}
