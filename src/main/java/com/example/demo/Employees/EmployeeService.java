package com.example.demo.Employees;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

//@service=@component but for readability
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EntityManager entityManager;
//    private final Session session;
//    private final EntityPersister persister;

    public EmployeeService( EmployeeRepository employeeRepository, EntityManager entityManager) {
        this.employeeRepository = employeeRepository;
        this.entityManager = entityManager;
//        session = (Session) entityManager.getDelegate();
//        persister= ((SessionFactoryImpl) session.getSessionFactory()).getMetamodel().entityPersister("theNameOfYourEntity");

    }


    public void EvictCache()
    {

        entityManager.getEntityManagerFactory().getCache().evict(Employee.class);

    }

    public List<Employee> getEmployees()
    {
        return employeeRepository.findAll();

    }


    public void createNewEmployee(Employee employee) {

        employeeRepository.save(employee);

    }

    public void removeEmployee(Long id) {

        boolean studentByIdExists= employeeRepository.existsById(id);

        if(!studentByIdExists)
        {
            throw new IllegalStateException("student with ID "+id+" was not found");
        }
        employeeRepository.deleteById(id);
    }

    @Transactional
    public void updateStudentInfo(Long id, int age, String jopRole) {
        Employee employeeById = employeeRepository
                .findById(id)
                .orElseThrow(()->new IllegalStateException("employee with ID "+id+" was not found"));

        if(jopRole!=null && jopRole.length()>0)
        {
            employeeById.setJobRole(jopRole);
            System.out.println("updated jop!");
        }


            employeeById.setAge(age);
            System.out.println("updated age");

    }

    public Employee getEmployee(Long id) {
        Employee employeeById = employeeRepository
                .findById(id)
                .orElseThrow(()->new IllegalStateException("student with ID "+id+" was not found"));
        return  employeeById;
    }
}
