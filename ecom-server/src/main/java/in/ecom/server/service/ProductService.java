package in.ecom.server.service;

import in.ecom.server.model.Product;
import in.ecom.server.payload.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
}
