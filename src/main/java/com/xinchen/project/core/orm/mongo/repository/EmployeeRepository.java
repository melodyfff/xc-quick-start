package com.xinchen.project.core.orm.mongo.repository;

import com.xinchen.project.core.orm.mongo.model.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Profile("mongo")
@Repository
public interface EmployeeRepository extends MongoRepository<Employee,String> {
}
