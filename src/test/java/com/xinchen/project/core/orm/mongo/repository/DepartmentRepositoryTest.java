package com.xinchen.project.core.orm.mongo.repository;

import com.xinchen.project.core.orm.mongo.model.Department;
import com.xinchen.project.core.orm.mongo.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Profile("mongo")
@SpringBootTest
class DepartmentRepositoryTest {
    @Autowired
    DepartmentRepository repository;
    @Autowired
    EmployeeRepository employeeRepository;


    @BeforeEach
    void initData(){
        if (repository.count()!=2){
            Department department = new Department();
            // 设置ID,如果存在后续的操作即为更新
            department.setId("63ba4279697faa6a71ec0502");
            department.setName("系统开发部");
            department.setDescription("二级部门");

            Employee employee = new Employee();
            employee.setEmpId("1");
            employee.setName("Mr.Robot");
            employee.setAge(20);
            employee.setSalary(3000);
            employeeRepository.save(employee);

            Employee employee2 = new Employee();
            employee2.setEmpId("2");
            employee2.setName("Mr.Tom");
            employee2.setAge(25);
            employee2.setSalary(3600);
            employeeRepository.save(employee2);

            department.setEmployees(List.of(employee,employee2));
            department.setEmployeesNoRef(List.of(employee,employee2));
            repository.save(department);

            Department department2 = new Department();
            // 设置ID,如果存在后续的操作即为更新
            department2.setId("63ba4279697faa6a71ec0503");
            department2.setName("系统测试部");
            department2.setDescription("二级部门");

            department2.setEmployeesNoRef(List.of(employee2));
            department2.setEmployees(List.of(employee2));
            repository.save(department2);
        } else {
            // 这里主要是为了观察version变动
            repository.saveAll(repository.findAll());
        }
    }

    @Test
    void findDepartment() {
        assertEquals(2, repository.findByName("系统开发部").getEmployees().size());
        assertEquals(1, repository.findDepartmentByName("系统测试部").get(0).getEmployees().size());
        // 根据雇员姓名查询部门，并排除字段
        assertEquals(2, repository.findDepartmentByEmployeeName("Mr.Tom").size());
        Assertions.assertNull(repository.findDepartmentByEmployeeName("Mr.Tom").get(0).getEmployeesNoRef());
    }

    @Test
    void findPage(){
        // 分页查询条件 1页，size=1，排序根据name属性desc
        // 第一页
        PageRequest pageRequest1 = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("name")));
        Page<Department> re1 = repository.findAll(pageRequest1);
        assertEquals(2,re1.getTotalPages());
        assertEquals(1,re1.getContent().size());
        assertEquals("系统测试部",re1.getContent().get(0).getName());
        // 第二页
        PageRequest pageRequest2 = PageRequest.of(1, 1, Sort.by(Sort.Order.desc("name")));
        Page<Department> re2 = repository.findAll(pageRequest2);
        assertEquals(2,re2.getTotalPages());
        assertEquals(1,re2.getContent().size());
        assertEquals("系统开发部",re2.getContent().get(0).getName());
    }

    @Test
    void findExample(){
        // 每天一个小问题之：jpa Example.of 的模糊查询 使用 https://blog.csdn.net/weixin_41326813/article/details/108708712
        // 查询所有，直接将实体进行全匹配查询
        Example<Department> example1 = Example.of(new Department());
        Optional<Department> re1 = repository.findOne(example1);
        assertTrue(re1.isPresent());


        // 模糊查询
        // Example主要是查询参数，ExampleMatcher对比规则，设置对比语句，根据实体类内容与matcher组合最终拼装形成了查询语句
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains())
                // 忽略属性
                .withIgnorePaths("description");

        Department query = new Department();
        query.setName("测试");
        Example<Department> example2 = Example.of(query, matcher);
        List<Department> re2 = repository.findAll(example2);
        assertEquals(1,re2.size());
    }


    @Test
    void likeSearch(){
        List<Department> departments = repository.findByNameLike("系统");
        assertEquals(2,departments.size());
    }

    @Test
    void likeSearchPage(){
        Page<Department> byNameLike = repository.findByNameLike("部", PageRequest.of(0, 1, Sort.by(Sort.Order.desc("name"))));
        assertEquals(2,byNameLike.getTotalPages());
        assertEquals(1,byNameLike.getContent().size());
        assertEquals("系统测试部",byNameLike.getContent().get(0).getName());
    }
    @Test
    void findAll(){
        List<Department> all = repository.findAll();
        Assertions.assertNotNull(all);
    }
}