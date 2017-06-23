package kiddom.repository;

import kiddom.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kiddom.model.ParentEntity;

@Repository("parentRepository")
public interface ParentRepository extends JpaRepository<ParentEntity, String> {
  // ParentEntity findByUser(UserEntity user);
   // void save(String user, ParentEntity parent)
}
