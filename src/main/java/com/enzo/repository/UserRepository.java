package com.enzo.repository;

import com.enzo.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,  String> {

    @Query("SELECT u FROM User u WHERE id =:id")
    public UserEntity getById(@Param("id") String id);

    /** rechercher un utilisateur **/
    @Query("SELECT u FROM User u WHERE email =:email")
    public UserEntity getUserByEmail(@Param("email") String email);

    /** get users if have or not params **/
    @Query("SELECT u FROM User u WHERE UPPER(email) like concat('%',UPPER(:param), '%') or UPPER(name) like concat('%',UPPER(:param), '%')")
    public Page<UserEntity> getUsersWithParam(@Param("param") String param, Pageable page);

    /*** add permission to user in table (access_permissions) **/
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO access_permissions (user_id, permission_id) VALUES (:user, :permission)", nativeQuery = true)
    public int addPermissionToUser(@Param("user") String username, @Param("permission") String permission);

    /** remove permissions to user **/
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM access_permissions WHERE user_id = :user AND  permission_id IN ( SELECT id FROM permissions WHERE code in :permission )", nativeQuery = true)
    public int removePermissionsToUser(@Param("user") String username, @Param("permission") List<String> permission);


    /** delete all user who exist in compte **/
    @Modifying
    @Transactional
    @Query(value = "UPDATE FROM users set is_deleted = true WHERE compte_id = :compteId", nativeQuery = true)
    public int deleteUserInCompte(@Param("compte") String compteId);


    /*** add compte to users **/
    @Modifying
    @Transactional
    @Query(value = "UPDATE FROM users set compte_id = :compteID WHERE id IN :userIDs", nativeQuery = true)
    public int addUserOnCompte(@Param("userIDs") List<String> userIDs, @Param("compteID") String compteID);


}
