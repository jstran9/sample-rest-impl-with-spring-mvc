package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;

public interface VendorService {
    VendorListDTO getAllVendors();

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    void deleteVendorByid(Long id);

    VendorDTO getVendorById(Long id);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

    VendorDTO putVendorByDTO(Long id, VendorDTO vendorDTO);
}
