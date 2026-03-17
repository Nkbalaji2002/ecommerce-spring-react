package in.ecom.server.repository;

import in.ecom.server.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM cart c WHERE c.user.email = ?1")
    Cart findCartByEmail(String email);

}
