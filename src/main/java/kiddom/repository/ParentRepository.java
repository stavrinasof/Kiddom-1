package kiddom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kiddom.model.ParentEntity;

@Repository("parentRepository")
public interface ParentRepository extends JpaRepository<ParentEntity, Long> {
    //ParentEntity findByUsername(String username);

//	void saveUser(UserEntity user, ParentEntity parent);
}