package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);


        System.out.println("Categories loaded = " + categoryRepository.count());
    }

    private void loadCustomers() {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstname("Lebron");
        customer.setLastname("James");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstname("Dwyane");
        customer2.setLastname("Wade");

        customerRepository.save(customer);
        customerRepository.save(customer2);

        System.out.println("Customers loaded = " + customerRepository.count());
    }

    private void loadVendors() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("Franks Fresh Fruits from France Ltd.");

        Vendor vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("Hormel Foods Corporation");

        vendorRepository.save(vendor);
        vendorRepository.save(vendor2);

        System.out.println("Vendors loaded = " + vendorRepository.count());
    }
}