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

    @GetMapping("/{addressId}")
    public ResponseEntity<?> getAddressById(@PathVariable Long addressId) {
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAddressByUser() {
        User user = authUtil.loggedInUser();

        List<AddressDTO> addressDTOList = addressService.getAddressByUser(user);
        return new ResponseEntity<>(addressDTOList, HttpStatus.OK);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<?> updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO) {
        AddressDTO updateAddressDto = addressService.updateAddress(addressId, addressDTO);
        return new ResponseEntity<>(updateAddressDto, HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> removeAddress(@PathVariable Long addressId) {
        String response = addressService.removeAddress(addressId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
