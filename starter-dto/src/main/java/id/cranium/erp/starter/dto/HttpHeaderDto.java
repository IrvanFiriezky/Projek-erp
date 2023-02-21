package id.cranium.erp.starter.dto;

import lombok.Data;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class HttpHeaderDto {
    
    private String XUserId;
    private String XAuthorization;
    private String XAcceptLanguage;
    private String XApiVersion;

    public Long getXUserId() {
        if (this.XUserId == null) {
            return 0L;
        }
        return Long.parseLong(this.XUserId);
    }
}
