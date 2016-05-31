package nz.co.shanika.web;

import nz.co.shanika.domain.Customer;
import nz.co.shanika.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by SWijerathna on 5/20/2016.
 */
@RestController
@RequestMapping(value = "api/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Customer createCustomer(@RequestBody Customer customer) {

        return customerRepository.save(customer);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Customer updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {

        return customerRepository.save(customer);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Customer> getAllCustomers(){

        return customerRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable Long id) {

        return customerRepository.findOne(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable Long id) {

        customerRepository.delete(id);
    }


}
