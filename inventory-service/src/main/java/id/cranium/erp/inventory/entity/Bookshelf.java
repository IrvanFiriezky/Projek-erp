package id.cranium.erp.inventory.entity;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bookshelf {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "bookshelf_name")
  private String bookshelfName;

  private int status;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToOne(targetEntity = Book.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bookshelf")
  private Book book;

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
