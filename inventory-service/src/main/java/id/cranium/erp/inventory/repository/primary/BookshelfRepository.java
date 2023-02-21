package id.cranium.erp.inventory.repository.primary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.cranium.erp.inventory.entity.Bookshelf;

@Repository
public interface BookshelfRepository extends JpaRepository<Bookshelf, Long> {
  Optional<Bookshelf> findById(Long id);
}
