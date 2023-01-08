package com.xinchen.project.core.orm.mongo.model;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Document(collection = "department")
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    /**
     * 创建索引，unique=true为唯一索引
     * db.department.getIndexes() 查看索引
     * db.department.dropIndex("deptName") 删除索引
     */
    @Indexed(name = "deptName",unique = true)
    private String name;
    private String description;
    @DBRef
    private List<Employee> employees;

    private List<Employee> employeesNoRef;

    @CreatedDate
    private Date createDate;
    @CreatedBy
    private String createBy;
    @LastModifiedDate
    private Date modifiedDate;
    @LastModifiedBy
    private String modifieBy;
    @Version
    private Long version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> getEmployeesNoRef() {
        return employeesNoRef;
    }

    public void setEmployeesNoRef(List<Employee> employeesNoRef) {
        this.employeesNoRef = employeesNoRef;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getModifieBy() {
        return modifieBy;
    }

    public void setModifieBy(String modifieBy) {
        this.modifieBy = modifieBy;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", employees=" + employees +
                ", employeesNoRef=" + employeesNoRef +
                '}';
    }
}
