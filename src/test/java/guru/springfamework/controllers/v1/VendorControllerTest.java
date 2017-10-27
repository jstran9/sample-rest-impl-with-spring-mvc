package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
import guru.springfamework.services.VendorServiceImplTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {VendorController.class})
public class VendorControllerTest {

    @MockBean
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc;

    VendorDTO vendorDTO_1;
    VendorDTO vendorDTO_2;

    public static final String NON_EXISTING_ID = String.valueOf(Long.MAX_VALUE);

    @Before
    public void setUp() throws Exception {
        vendorDTO_1 = createVendorOne();
        vendorDTO_2 = createVendorTwo();
    }

    @Test
    public void getAllVendorsTest() throws Exception {
        VendorListDTO vendorListDTO = new VendorListDTO(Arrays.asList(createVendorOne(), createVendorTwo()));

        given(vendorService.getAllVendors()).willReturn(vendorListDTO);

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(VendorServiceImplTest.EXPECTED_SIZE)));
    }

    @Test
    public void createNewVendorTest() throws Exception {
        given(vendorService.createNewVendor(vendorDTO_1)).willReturn(vendorDTO_1);

        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO_1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void deleteVendorById() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + VendorServiceImplTest.EXPECTED_ID_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorByid(anyLong());
    }

    @Test
    public void getVendorByIdTest() throws Exception {
        given(vendorService.getVendorById(anyLong())).willReturn(vendorDTO_1);

        mockMvc.perform(get(VendorController.BASE_URL + VendorServiceImplTest.EXPECTED_ID_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void patchVendorTest() throws Exception {
        VendorDTO modifiedVendorDTO = vendorDTO_2;
        modifiedVendorDTO.setName(vendorDTO_1.getName());
        given(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).willReturn(modifiedVendorDTO);

        mockMvc.perform(patch(VendorController.BASE_URL + VendorServiceImplTest.EXPECTED_ID_1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(modifiedVendorDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void putVendorByDTOTest() throws Exception {
        VendorDTO modifiedVendorDTO = vendorDTO_2;
        modifiedVendorDTO.setName(vendorDTO_1.getName());
        given(vendorService.putVendorByDTO(anyLong(), any(VendorDTO.class))).willReturn(modifiedVendorDTO);

        mockMvc.perform(put(VendorController.BASE_URL + VendorServiceImplTest.EXPECTED_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(modifiedVendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + NON_EXISTING_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private VendorDTO createVendorOne() {
        return new VendorDTO(VendorServiceImplTest.FRANKS_FRESH_FRUITS_FROM_FRANCE_LTD_TEST,
                VendorController.BASE_URL + VendorServiceImplTest.EXPECTED_ID_1);
    }

    private VendorDTO createVendorTwo() {
        return new VendorDTO(VendorServiceImplTest.HORMEL_FOODS_CORPORATION_TEST,
                VendorController.BASE_URL + VendorServiceImplTest.EXPECTED_ID_2);
    }
}
