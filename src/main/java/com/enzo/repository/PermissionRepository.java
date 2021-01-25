package com.enzo.repository;

import com.enzo.entities.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {




    public Permission findByCode(String code);

    /** list of permissions of user **/
    @Query(value = "SELECT u.permissions FROM User u  WHERE id = :user")
    public Page<Permission> permissionsOfUser(@Param("user") String username, Pageable page);

    /** search permissions in user **/
    @Query(value = "SELECT u.permissions FROM User u LEFT JOIN u.permissions p WHERE id = :user AND UPPER(p.description) LIKE CONCAT('%',:param,'%')")
    public Page<Permission> searchPermissionsOfUser(@Param("user") String username, @Param("param") String param, Pageable page);


    /** list of permissions of role **/
    @Query(value = "SELECT r.permissions FROM Role r WHERE id = :role")
    public Page<Permission> permissionsOfRole(@Param("role") String username, Pageable page);


    /** search permisons in role **/
    @Query(value = "SELECT p FROM Role r LEFT JOIN r.permissions p WHERE id = :role AND UPPER(p.description) LIKE CONCAT('%',:param,'%')")
    public Page<Permission> searchPermissionsOfRole(@Param("role") String username, @Param("param") String param, Pageable page);




}
