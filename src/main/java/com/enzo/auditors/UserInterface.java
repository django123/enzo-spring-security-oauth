package com.enzo.auditors;

import com.enzo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterface extends JpaRepository<UserEntity, String> {
    public UserEntity findByEmail(String email);
}
