package in.ecom.server.controller;

import in.ecom.server.model.Product;
import in.ecom.server.payload.ProductDTO;
import in.ecom.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<?> addProduct(@RequestBody Product product, @PathVariable Long categoryId) {
        ProductDTO productDTO = productService.addProduct(categoryId, product);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<?> getAllProducts() {
        var response = productService.getAllProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("public/categories/{categoryId}/products")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Long categoryId) {
        var response = productService.searchByCategory(categoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<?> getProductByKeyword(@PathVariable String keyword) {
        var response = productService.searchProductByKeyword(keyword);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    /* update product */
    @PutMapping("admin/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        ProductDTO productDTO = productService.updateProduct(productId, product);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    /*delete product*/
    @DeleteMapping("admin/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        var productDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

}
