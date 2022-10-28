package com.example.demo.Employees;

import com.example.demo.KeyGenerator.CustomCacheKeyImplementation;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path="/employees")
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    private final RedissonClient redissonClient;
    private final RMapCache mycache;
    public EmployeeController(EmployeeService employeeService, RedissonClient redissonClient) {
        this.employeeService = employeeService;
        this.redissonClient = redissonClient;
        mycache=redissonClient.getMapCache("empCache");
    }

    @GetMapping(path="getallfromcached")
    public void getAllFromCached()
    {
        Collection<Employee> map=mycache.readAllValues();

        System.out.println("map----------------"+map);
        Set keys=mycache.readAllKeySet();
        System.out.println("keys----------------"+keys);

        for (Object key:keys
             ) {
            System.out.println("keysType-------------"+key.getClass());


        }


    }
    @GetMapping(path = "evict/{employeeID}")
    public void evictEmployee(@PathVariable("employeeID") Long id)
    {
        RMapCache my=redissonClient.getMapCache("employeesCache");
       CustomCacheKeyImplementation c= new CustomCacheKeyImplementation(id,"employee");
       System.out.println(my.containsKey(c));
       my.remove(c);
       mycache.remove(c);
    }
    @GetMapping(path = "evictall")
    public void evictAll()
    {
        employeeService.EvictCache();
    }
    @GetMapping
    public List<Employee> getEmployees()
    {
        return employeeService.getEmployees();
    }

    @PostMapping
    public void  createEmployee(@RequestBody Employee employee)
    {
        employeeService.createNewEmployee(employee);
    }

    @DeleteMapping(path = "del/{employeeID}")
    public void deleteEmployee(@PathVariable("employeeID") Long id)
    {
        employeeService.removeEmployee(id);
    }

    @GetMapping(path = "find/{employeeID}")
    public Employee getEmployee(@PathVariable("employeeID") Long id)
    {
        return employeeService.getEmployee(id);
    }

    @PutMapping(path= "update/{employeeID}")
    public void updateEmployee(
            @PathVariable("employeeID") Long id,
            @RequestParam(required = false) int age,
            @RequestParam(required = false) String jopRole)
    {
        employeeService.updateStudentInfo(id,age,jopRole);
    }
}
