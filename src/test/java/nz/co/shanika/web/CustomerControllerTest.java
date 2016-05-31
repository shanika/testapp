package nz.co.shanika.web;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.shanika.domain.Customer;
import nz.co.shanika.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by SWijerathna on 5/20/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CustomerControllerTest.TestConfiguration.class)
@WebAppConfiguration
public class CustomerControllerTest {


    public static final String TEST_NAME_1 = "Test Name";
    public static final Integer TEST_ID_1 = 1;
    public static final String TEST_ADDRESS_1 = "Test Address";
    public static final String TEST_PHONE_1 = "1234567";

    public static final String TEST_NAME_2 = "TestName 2";
    public static final Integer TEST_ID_2 = 2;
    public static final String TEST_ADDRESS_2 = "Test Address 2";
    public static final String TEST_PHONE_2 = "7654321";

    public static final String APPLICATION_JSON_UTF_8 = "application/json;charset=UTF-8";

    ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private CustomerRepository customerRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        Mockito.reset(customerRepository);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void createCustomer_ShouldCreateANewCustomer() throws Exception {

        when(customerRepository.save(Mockito.any(Customer.class)))
            .thenReturn(new Customer(TEST_ID_1.longValue(), TEST_NAME_1, TEST_ADDRESS_1, TEST_PHONE_1));

        mockMvc.perform(post("/api/customers")
            .contentType(APPLICATION_JSON_UTF_8)
            .content(convertObjectToJsonBytes(new Customer(null,TEST_NAME_1,TEST_ADDRESS_1,TEST_PHONE_1))))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF_8))

            .andExpect(jsonPath("$.id", is(TEST_ID_1)))
            .andExpect(jsonPath("$.name", is(TEST_NAME_1)))
            .andExpect(jsonPath("$.address", is(TEST_ADDRESS_1)))
            .andExpect(jsonPath("$.phone", is(TEST_PHONE_1)));

        verify(customerRepository, times(1)).save(argumentCaptor.capture());

        assertNull(argumentCaptor.getValue().getId());
        assertThat(argumentCaptor.getValue().getName(), is(TEST_NAME_1));
        assertThat(argumentCaptor.getValue().getAddress(), is(TEST_ADDRESS_1));
        assertThat(argumentCaptor.getValue().getPhone(), is(TEST_PHONE_1));

        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    public void updateCustomer_ShouldUpdateTheCustomer() throws Exception {

        when(customerRepository.save(Mockito.any(Customer.class)))
            .thenReturn(new Customer(TEST_ID_1.longValue(), TEST_NAME_1, TEST_ADDRESS_1, TEST_PHONE_1));

        mockMvc.perform(put("/api/customers/" + TEST_ID_1)
            .contentType(APPLICATION_JSON_UTF_8)
            .content(convertObjectToJsonBytes(new Customer(null,TEST_NAME_1,TEST_ADDRESS_1,TEST_PHONE_1))))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF_8))

            .andExpect(jsonPath("$.id", is(TEST_ID_1)))
            .andExpect(jsonPath("$.name", is(TEST_NAME_1)))
            .andExpect(jsonPath("$.address", is(TEST_ADDRESS_1)))
            .andExpect(jsonPath("$.phone", is(TEST_PHONE_1)));

        verify(customerRepository, times(1)).save(argumentCaptor.capture());

        assertNull(argumentCaptor.getValue().getId());
        assertThat(argumentCaptor.getValue().getName(), is(TEST_NAME_1));
        assertThat(argumentCaptor.getValue().getAddress(), is(TEST_ADDRESS_1));
        assertThat(argumentCaptor.getValue().getPhone(), is(TEST_PHONE_1));

        verifyNoMoreInteractions(customerRepository);

    }

    @Test
    public void getAllCustomers_ShouldReturnAllTheCustomers() throws Exception {

        List<Customer> customers = new ArrayList<Customer>();
        customers.add(new Customer(TEST_ID_1.longValue(), TEST_NAME_1, TEST_ADDRESS_1, TEST_PHONE_1));
        customers.add(new Customer(TEST_ID_2.longValue(), TEST_NAME_2, TEST_ADDRESS_2, TEST_PHONE_2));

        Mockito.when(customerRepository.findAll()).thenReturn(customers);

        mockMvc.perform(get("/api/customers").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF_8))
            .andExpect(jsonPath("$", hasSize(2)))

            .andExpect(jsonPath("$[0].id", is(TEST_ID_1)))
            .andExpect(jsonPath("$[0].name", is(TEST_NAME_1)))
            .andExpect(jsonPath("$[0].address", is(TEST_ADDRESS_1)))
            .andExpect(jsonPath("$[0].phone", is(TEST_PHONE_1)))

            .andExpect(jsonPath("$[1].id", is(TEST_ID_2)))
            .andExpect(jsonPath("$[1].name", is(TEST_NAME_2)))
            .andExpect(jsonPath("$[1].address", is(TEST_ADDRESS_2)))
            .andExpect(jsonPath("$[1].phone", is(TEST_PHONE_2)));

        verify(customerRepository, times(1)).findAll();
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    public void getCustomer_ShouldReturnCorrectCustomer() throws Exception {

        Mockito.when(customerRepository.findOne(TEST_ID_1.longValue()))
            .thenReturn(new Customer(TEST_ID_1.longValue(), TEST_NAME_1, TEST_ADDRESS_1, TEST_PHONE_1));

        mockMvc.perform(get("/api/customers/" + TEST_ID_1).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF_8))

            .andExpect(jsonPath("$.id", is(TEST_ID_1)))
            .andExpect(jsonPath("$.name", is(TEST_NAME_1)))
            .andExpect(jsonPath("$.address", is(TEST_ADDRESS_1)))
            .andExpect(jsonPath("$.phone", is(TEST_PHONE_1)));

        verify(customerRepository, times(1)).findOne(TEST_ID_1.longValue());
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    public void deleteCustomer_ShouldDeleteCustomer() throws Exception {

        mockMvc.perform(delete("/api/customers/" + TEST_ID_1).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(customerRepository, times(1)).delete(TEST_ID_1.longValue());
        verifyNoMoreInteractions(customerRepository);
    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    @Configuration
    @EnableWebMvc
    public static class TestConfiguration {

        @Bean
        public CustomerRepository customerRepository() {

            return Mockito.mock(CustomerRepository.class);
        }

        @Bean
        public CustomerController customerController() {

            return new CustomerController();
        }
    }

}