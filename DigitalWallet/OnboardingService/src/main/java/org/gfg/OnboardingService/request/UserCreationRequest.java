package org.gfg.OnboardingService.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gfg.OnboardingService.model.UserIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreationRequest {

    String name;
    String email;
    String phoneNo;
    String password;
    UserIdentifier userIdentifier;
    String userIdentifierValue;
    String dob;
    String address;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserIdentifier getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(UserIdentifier userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public String getUserIdentifierValue() {
        return userIdentifierValue;
    }

    public void setUserIdentifierValue(String userIdentifierValue) {
        this.userIdentifierValue = userIdentifierValue;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
