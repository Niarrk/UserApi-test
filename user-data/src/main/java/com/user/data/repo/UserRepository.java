package com.user.data.repo;

import com.user.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 *
 *  @author : Lilian
 *  @version : 1.0-SNAPSHOT
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
