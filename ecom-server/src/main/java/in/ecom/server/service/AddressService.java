package in.ecom.server.service;

import in.ecom.server.model.User;
import in.ecom.server.payload.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAddresses();

    AddressDTO getAddressById(Long addressId);

    List<AddressDTO> getAddressByUser(User user);

    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);

    String removeAddress(Long addressId);
}
