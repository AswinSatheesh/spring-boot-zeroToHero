package com.aswin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }

    @GetMapping
    public List<Customer> getCustomers()
    {
        return customerRepository.findAll();
    }

    record NewCustomerRequest(
            String name,
            String email,
            Integer age)
    {

    }

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setAge(request.age());
        customer.setEmail(request.email());
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){

        customerRepository.deleteById(id);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody NewCustomerRequest request){

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

        if (Objects.nonNull(request.name())) {
            customer.setName(request.name());
        }

        if (Objects.nonNull(request.age())) {
            customer.setAge(request.age());
        }

        if (Objects.nonNull(request.email())) {
            customer.setEmail(request.email());
        }

        customerRepository.save(customer);
    }




//    @GetMapping("/greet")
//    public GreetResponse greet()
//    {
//        GreetResponse response = new GreetResponse(
//                "HELLO, THIS IS ASWIN",
//                List.of("Java", "Python", "JavaScript"),
//                new Person("Aswin",22,  45_000)
//        );
//        return response;
//    }
//
//    record  Person(String name,int age,double savings){
//
//    }
//    record GreetResponse(
//            String greet,
//            List<String> favProgrammingLanguages,
//            Person person
//    ){}



//    class GreetResponse{
//        private  final String greet;
//
//    GreetResponse(String greet) {
//        this.greet = greet;
//    }
//
//    public String getGreet() {
//        return greet;
//    }
//
//    @Override
//    public String toString() {
//        return "GreetResponse{" +
//                "greet='" + greet + '\'' +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        GreetResponse that = (GreetResponse) o;
//        return Objects.equals(greet, that.greet);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(greet);
//    }
//}

}
