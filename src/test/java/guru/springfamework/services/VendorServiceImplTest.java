package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendorServiceImplTest {

    public static final String FRANKS_FRESH_FRUITS_FROM_FRANCE_LTD_TEST = "Franks Fresh Fruits from France Ltd (Test).";
    public static final long ID_1 = 1L;
    public static final String HORMEL_FOODS_CORPORATION_TEST = "Hormel Foods Corporation (Test).";
    public static final long ID_2 = 2L;
    public static final int EXPECTED_SIZE = 2;
    public static final String EXPECTED_ID_1 = "/1";
    public static final String EXPECTED_ID_2 = "/2";

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    Vendor vendor1;
    Vendor vendor2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);

        vendor1 = new Vendor(ID_1, FRANKS_FRESH_FRUITS_FROM_FRANCE_LTD_TEST);
        vendor2 = new Vendor(ID_2, HORMEL_FOODS_CORPORATION_TEST);
    }

    @Test
    public void getAllVendorsTest() throws Exception {
        //given
        List<Vendor> vendors = Arrays.asList(vendor1, vendor2);
        given(vendorRepository.findAll()).willReturn(vendors);

        //when
        VendorListDTO vendorListDTO = vendorService.getAllVendors();

        //then
        then(vendorRepository).should(times(1)).findAll();
        assertThat(vendorListDTO.getVendors().size(), is(equalTo(EXPECTED_SIZE)));
    }

    @Test
    public void createNewVendorTest() throws Exception {
        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(vendor1.getName());

        // when
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor1);
        VendorDTO savedDTO = vendorService.createNewVendor(vendorDTO);

        // thenF
        assertEquals(vendorDTO.getName(), savedDTO.getName());
        assertEquals(VendorController.BASE_URL + EXPECTED_ID_1, savedDTO.getVendorUrl());
    }

    @Test
    public void deleteByIdTest() throws Exception {
        vendorService.deleteVendorByid(ID_1);

        verify(vendorRepository, times(1)).deleteById(ID_1);
    }

    @Test
    public void getVendorByIdTest() throws Exception {
        //given from the setUp() method.

        //when
        when(vendorRepository.findById(ID_1)).thenReturn(java.util.Optional.ofNullable(vendor1));
        VendorDTO retrievedVendorDTO = vendorService.getVendorById(ID_1);

        // then.
        assertEquals(FRANKS_FRESH_FRUITS_FROM_FRANCE_LTD_TEST, retrievedVendorDTO.getName());
        assertEquals(VendorController.BASE_URL + EXPECTED_ID_1, retrievedVendorDTO.getVendorUrl());
    }

    @Test
    public void patchVendorTest() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(FRANKS_FRESH_FRUITS_FROM_FRANCE_LTD_TEST + " (NEW)");

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor1));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor1);

        VendorDTO patchedVendorDTO = vendorService.patchVendor(vendor1.getId(), vendorDTO);

        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(patchedVendorDTO.getName(), containsString(vendorDTO.getName()));
        assertThat(patchedVendorDTO.getVendorUrl(), containsString(VendorController.BASE_URL + EXPECTED_ID_1));
    }

    @Test
    public void putVendorByDTOTest() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        // modifying the name to test if put changes the name.
        vendorDTO.setName(FRANKS_FRESH_FRUITS_FROM_FRANCE_LTD_TEST + " (NEW)");

        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor1);

        // when
        VendorDTO putVendorDTO = vendorService.putVendorByDTO(ID_1, vendorDTO);

        // then.
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(putVendorDTO.getVendorUrl(), containsString(VendorController.BASE_URL + EXPECTED_ID_1));
    }
}
