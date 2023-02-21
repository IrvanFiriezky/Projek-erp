package id.cranium.erp.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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
import org.hibernate.envers.Audited;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_scope")
public class UserScope {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(targetEntity = Scope.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "scope_id", referencedColumnName = "id")
    private Scope scope;

    @Column(name = "scope_value")
    private String scopeValue;
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
