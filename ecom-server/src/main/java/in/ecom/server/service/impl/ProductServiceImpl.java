package in.ecom.server.service.impl;

import in.ecom.server.exceptions.ResourceNotFoundException;
import in.ecom.server.model.Category;
import in.ecom.server.model.Product;
import in.ecom.server.payload.ProductDTO;
import in.ecom.server.payload.ProductResponse;
import in.ecom.server.repository.CategoryRepository;
import in.ecom.server.repository.ProductRepository;
import in.ecom.server.service.FileService;
import in.ecom.server.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Product product = modelMapper.map(productDTO, Product.class);

        product.setImage("default.png");
        product.setCategory(category);

        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);

        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        var products = productRepository.findAll();
        var productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        var productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        return productResponse;

    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        var products = productRepository.findByCategoryOrderByPriceAsc(category);
        var productsDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        var productResponse = new ProductResponse();
        productResponse.setContent(productsDTOs);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        var products = productRepository.findByProductNameLikeIgnoreCase("%" + keyword + "%");
        List<ProductDTO> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        var productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        /*get the existing product from db*/
        var productFromDb = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        Product product = modelMapper.map(productDTO, Product.class);

        /*update the product info with the one in request body*/
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setSpecialPrice(product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice()));

        /*save to database*/
        Product savedProduct = productRepository.save(productFromDb);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        var product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        /* get the product from db */
        Product productFromDb = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        /* upload image to server */
        /* get the file name of uploaded image */
        String fileName = fileService.uploadImage(path, image);

        /* updating the new file name to the product */
        productFromDb.setImage(fileName);

        /* save updated product */
        Product updatedProduct = productRepository.save(productFromDb);

        /* return DTO after mapping product to DTO */
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }
}
