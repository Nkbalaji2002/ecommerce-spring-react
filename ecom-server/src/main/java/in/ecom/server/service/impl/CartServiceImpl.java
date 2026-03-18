package in.ecom.server.service.impl;

import in.ecom.server.exceptions.APIException;
import in.ecom.server.exceptions.ResourceNotFoundException;
import in.ecom.server.model.Cart;
import in.ecom.server.model.CartItem;
import in.ecom.server.model.Product;
import in.ecom.server.payload.CartDTO;
import in.ecom.server.payload.ProductDTO;
import in.ecom.server.repository.CartItemRepository;
import in.ecom.server.repository.CartRepository;
import in.ecom.server.repository.ProductRepository;
import in.ecom.server.service.CartService;
import in.ecom.server.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthUtil authUtil;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        /* find existing cart or create one */
        Cart cart = createCart();

        /* Retrieve Product Details */
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        /* Perform Validations */
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(
                cart.getCartId(),
                productId
        );

        if (cartItem != null) {
            throw new APIException("Product " + product.getProductName() + " already exists in the cart.");
        }

        if (product.getQuantity() == 0) {
            throw new APIException(product.getProductName() + "is not available.");
        }

        if (product.getQuantity() < quantity) {
            throw new APIException("Please make an order of the " + product.getProductName() + " less than or equal to the quantity " + product.getQuantity() + ".");
        }

        /* Create Cart Item */
        CartItem newCartItem = new CartItem();

        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        /* Save Cart Item */
        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity());

        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));

        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();

        List<ProductDTO> products = cartItems.stream().map(item -> {
            ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        }).toList();

        cartDTO.setProducts(products);

        /* Return updated Cart */
        return cartDTO;
    }

    private Cart createCart() {
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if (userCart != null) {
            return userCart;
        }

        Cart cart = new Cart();
        cart.setTotalPrice(0.00);
        cart.setUser(authUtil.loggedInUser());

        return cartRepository.save(cart);
    }

}
