package id.cranium.erp.inventory.entity;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Data
@Builder

public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(targetEntity = Bookshelf.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "bookshelf_id", referencedColumnName = "id")
  private Bookshelf bookshelf;

  @Column(name = "book_name")
  private String bookName;

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
