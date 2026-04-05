package in.ecom.server.controller;

import in.ecom.server.model.User;
import in.ecom.server.payload.AddressDTO;
import in.ecom.server.service.AddressService;
import in.ecom.server.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping
    public ResponseEntity<?> createAddress(@RequestBody AddressDTO addressDTO) {
        User user = authUtil.loggedInUser();

        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO, user);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAddresses() {
        List<AddressDTO> addressList = addressService.getAddresses();
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

}
