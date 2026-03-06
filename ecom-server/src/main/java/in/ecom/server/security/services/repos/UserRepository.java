package in.ecom.server.security.services.repos;

import in.ecom.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);

    Boolean existByUserName(String username);

    Boolean existsByEmail(String email);

}
