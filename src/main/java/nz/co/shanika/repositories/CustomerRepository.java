package nz.co.shanika.repositories;

import nz.co.shanika.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by SWijerathna on 5/20/2016.
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
