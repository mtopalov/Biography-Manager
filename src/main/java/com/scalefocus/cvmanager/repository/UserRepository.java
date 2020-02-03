package com.scalefocus.cvmanager.repository;

import com.scalefocus.cvmanager.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mariyan Topalov
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);
}
