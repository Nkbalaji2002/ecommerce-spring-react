package in.ecom.server.repository;

import in.ecom.server.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT c1 FROM CartItem c1 WHERE c1.cart.id = ?1 AND c1.product.id = ?2")
    CartItem findCartItemByProductIdAndCartId(Long cartId, Long productId);
}
