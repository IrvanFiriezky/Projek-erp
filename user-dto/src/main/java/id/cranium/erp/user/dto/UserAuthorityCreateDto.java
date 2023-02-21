package id.cranium.erp.user.dto;

import lombok.Data;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class UserAuthorityCreateDto {
    
    private Long authorityId;
}
