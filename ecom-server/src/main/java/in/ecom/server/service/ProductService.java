package in.ecom.server.service;

import in.ecom.server.model.Product;
import in.ecom.server.payload.ProductDTO;
import in.ecom.server.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();
}
