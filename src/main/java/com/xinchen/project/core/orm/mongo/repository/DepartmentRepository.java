package com.xinchen.project.core.orm.mongo.repository;

import com.xinchen.project.core.orm.mongo.model.Department;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 通过{@link MongoRepository}的方式实现查询
 * 遵循JPA的查询规范
 *
 * {@link Query} value指定查询条件，fields指定返回字段： 0表示不返回，1表示返回
 */
@Profile("mongo")
@Repository
public interface DepartmentRepository extends MongoRepository<Department,String> {
    /**
     * 根据雇员姓名查询其所属部门
     * fields = "{'employeesNoRef': 0}" 排除该字段
     * @param empName 雇员姓名
     * @return List
     */
    @Query(value = "{'employeesNoRef.name':?0}",fields = "{'employeesNoRef': 0}")
    List<Department> findDepartmentByEmployeeName(String empName);

    List<Department> findDepartmentByName(String name);

    Department findByName(String name);

    Page<Department> findByNameLike(String name, Pageable pageable);
    List<Department> findByNameLike(String name);
}
