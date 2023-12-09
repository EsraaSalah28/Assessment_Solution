package com.example.batch.repoistory;

import com.example.batch.model.Item;
import com.example.batch.model.Permission;
import com.example.batch.model.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
     Permission findByUserEmailAndGroup(String userEmail, PermissionGroup group);
     Permission findByUserEmail(String userEmail);
}
