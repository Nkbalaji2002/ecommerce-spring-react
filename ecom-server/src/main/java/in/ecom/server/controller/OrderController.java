package in.ecom.server.controller;

import in.ecom.server.payload.OrderDTO;
import in.ecom.server.payload.OrderRequestDTO;
import in.ecom.server.service.OrderService;
import in.ecom.server.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/users/payments/{paymentMethod}")
    public ResponseEntity<?> orderProducts(@PathVariable String paymentMethod,
                                           @RequestBody OrderRequestDTO orderRequestDTO) {

        String emailId = authUtil.loggedInEmail();
        OrderDTO order = orderService.placeOrder(
                 emailId,
                 orderRequestDTO.getAddressId(),
                 paymentMethod,
                 orderRequestDTO.getPgName(),
                 orderRequestDTO.getPgPaymentId(),
                 orderRequestDTO.getPgStatus(),
                 orderRequestDTO.getPgResponseMessage()
         );

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

}
