package id.cranium.erp.user.dto;

import lombok.Data;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import id.cranium.erp.user.validator.constraint.UserMobilePhoneNumber;

@Data
@Builder
@Jacksonized
public class UserCreateDto {

    private String username;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    @UserMobilePhoneNumber(allowed = {"08111111111", "08122222222"})
    private String mobilePhone;
    private String domain;
    private int status;
}
