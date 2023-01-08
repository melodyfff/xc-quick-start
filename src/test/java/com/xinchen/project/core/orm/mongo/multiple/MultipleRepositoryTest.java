package com.xinchen.project.core.orm.mongo.multiple;

import com.xinchen.project.core.orm.mongo.model.Department;
import com.xinchen.project.core.orm.mongo.multiple.primary.PrimaryDepartmentRepository;
import com.xinchen.project.core.orm.mongo.multiple.secondary.SecondaryDepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

@Profile("mongo-multiple")
@SpringBootTest
class MultipleRepositoryTest {
    @Autowired
    PrimaryDepartmentRepository primaryDepartmentRepository;
    @Autowired
    SecondaryDepartmentRepository secondaryDepartmentRepository;

    @Test
    void insert() {
        Optional<Department> optional = primaryDepartmentRepository.findById("63ba4279697faa6a71ec0507");

        if (optional.isPresent()){
            primaryDepartmentRepository.save(optional.get());
        } else {
            Department department = new Department();
            // 设置ID,如果存在后续的操作即为更新
            department.setId("63ba4279697faa6a71ec0507");
            department.setName("测试多数据源");

            primaryDepartmentRepository.save(department);
        }
    }

    @Test
    void insert2() {
        Optional<Department> optional = secondaryDepartmentRepository.findById("63ba4279697faa6a71ec0507");

        if (optional.isPresent()){
            secondaryDepartmentRepository.save(optional.get());
        } else {
            Department department = new Department();
            // 设置ID,如果存在后续的操作即为更新
            department.setId("63ba4279697faa6a71ec0507");
            department.setName("测试多数据源");

            secondaryDepartmentRepository.save(department);
        }
    }
}