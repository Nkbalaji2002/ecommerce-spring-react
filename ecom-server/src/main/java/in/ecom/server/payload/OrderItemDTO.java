package in.ecom.server.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long OrderItemId;

    private ProductDTO product;

    private Integer quantity;

    private Double discount;

    private Double orderedProductPrice;

}
