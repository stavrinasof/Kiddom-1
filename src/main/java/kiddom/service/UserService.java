package kiddom.service;

import kiddom.model.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("userService")
public interface UserService {
	public UserEntity findByUsername(String username);
	public void saveUser(UserEntity user, ParentEntity parent);
	public void saveUserProvider(UserEntity user, ProviderEntity provider);
	public UserEntity findByUsernamePassword(String username, String Password);
	public void saveActivity(UserEntity user, ProviderEntity provider, SingleEventEntity event);
}
