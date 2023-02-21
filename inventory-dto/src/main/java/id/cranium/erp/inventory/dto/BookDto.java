package id.cranium.erp.inventory.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class BookDto {
  private Long id;
  private Long bookshelfId;
  private String bookName;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;
  private Long createdBy;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;
  private Long updatedBy;
  private boolean deleted;
}
