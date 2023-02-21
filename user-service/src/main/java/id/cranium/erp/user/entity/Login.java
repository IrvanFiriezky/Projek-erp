package id.cranium.erp.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    
    @Id
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "user_id")
    private Long userId;
    private String username;
    private boolean status;
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(name = "created_by", updatable = false)
    @CreatedBy
    private Long createdBy;
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Column(name = "updated_by")
    @LastModifiedBy
    private Long updatedBy;
    private boolean deleted;
}
