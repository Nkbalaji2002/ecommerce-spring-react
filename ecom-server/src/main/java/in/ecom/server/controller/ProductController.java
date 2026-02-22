package in.ecom.server.controller;

import in.ecom.server.payload.ProductDTO;
import in.ecom.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO, @PathVariable Long categoryId) {
        ProductDTO savedProductDTO = productService.addProduct(categoryId, productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
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
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        ProductDTO response = productService.updateProduct(productId, productDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*delete product*/
    @DeleteMapping("admin/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        var productDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    /* update product image */
    @PutMapping("/products/{productId}/image")
    public ResponseEntity<?> updateProductImage(@PathVariable Long productId, @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO updatedProduct = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

}
