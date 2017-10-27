package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMapperTest {

    public static final String VENDOR_NAME = "Hormel Foods Corporation";
    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTOTest() throws Exception {
        // given
        Vendor vendor = new Vendor();
        vendor.setName(VENDOR_NAME);

        // when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        // then
        assertEquals(VENDOR_NAME, vendorDTO.getName());
    }

    @Test
    public void vendorDTOToVendorTest() throws Exception {
        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);

        // when
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);

        // then
        assertEquals(VENDOR_NAME, vendor.getName());
    }
}
