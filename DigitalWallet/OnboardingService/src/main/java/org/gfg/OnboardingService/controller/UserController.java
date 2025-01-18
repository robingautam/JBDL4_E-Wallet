package org.gfg.OnboardingService.controller;

import org.gfg.OnboardingService.model.User;
import org.gfg.OnboardingService.request.UserCreationRequest;
import org.gfg.OnboardingService.response.UserCreationResponse;
import org.gfg.OnboardingService.service.UserService;
import org.gfg.OnboardingService.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onboarding-service")
public class UserController {


    @Autowired
    UserService userService;


    @PostMapping("/onboard/user")
    public ResponseEntity<UserCreationResponse> createUser(@RequestBody UserCreationRequest userCreationRequest){
        System.out.println("Controller Hit");
        if (userCreationRequest==null){
            UserCreationResponse userCreationResponse = new UserCreationResponse();
            userCreationResponse.setReturnCode("06");
            userCreationResponse.setReturnMessage("Invalid Request");
            return new ResponseEntity<>(userCreationResponse, HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isBlank(userCreationRequest.getEmail()) || StringUtils.isBlank(userCreationRequest.getPhoneNo())){
            UserCreationResponse userCreationResponse = new UserCreationResponse();
            if (StringUtils.isBlank(userCreationRequest.getEmail())){
                userCreationResponse.setReturnCode("07");
                userCreationResponse.setReturnMessage("Invalid Email");
            }else {
                userCreationResponse.setReturnCode("08");
                userCreationResponse.setReturnMessage("Invalid Mobile");
            }

            return new ResponseEntity<>(userCreationResponse,HttpStatus.BAD_REQUEST);
        }


        User user = userService.onboardNewUser(userCreationRequest);
        UserCreationResponse userCreationResponse = new UserCreationResponse();

        if (user==null){
            userCreationResponse.setReturnCode("11");
            userCreationResponse.setReturnMessage("Something went wrong !! User not created");
            return new ResponseEntity<>(userCreationResponse,HttpStatus.OK);
        }else {
            userCreationResponse.setReturnCode("00");
            userCreationResponse.setReturnMessage("User Onboarded successfully");
            userCreationResponse.setName(user.getName());
            userCreationResponse.setEmail(user.getEmail());
            userCreationResponse.setPhoneNo(user.getPhone());
        }
        return new ResponseEntity<>(userCreationResponse,HttpStatus.CREATED);

    }


    @GetMapping("/validate/user")
    public String validateUser(@RequestParam("userId") String user){
      return   userService.fetchUserFromUserService(user);
    }

}
