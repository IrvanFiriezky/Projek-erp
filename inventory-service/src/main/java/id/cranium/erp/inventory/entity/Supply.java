package id.cranium.erp.inventory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Version;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
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
public class Supply {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "supply_name")
    private String supplyName;
    private int status;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(targetEntity = Stock.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "supply")
    private Stock stock;

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
    @Version
    private Long version;
}
