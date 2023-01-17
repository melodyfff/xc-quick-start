package com.xinchen.project.core.orm.jpa.repositories;

import com.xinchen.project.core.orm.jpa.entity.HelloAuditingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/17 21:37
 */
public interface HelloAuditingRepository extends JpaRepository<HelloAuditingEntity,Long> {

}
