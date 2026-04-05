package in.ecom.server.service;

import in.ecom.server.model.User;
import in.ecom.server.payload.AddressDTO;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);
}
