package in.ecom.server.service;

import in.ecom.server.payload.CartDTO;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);
}
