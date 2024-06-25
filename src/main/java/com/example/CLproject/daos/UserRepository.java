package com.example.CLproject.daos;

import com.example.CLproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("from com.example.CLproject.models.User a where a.id=:uid and a.password=:pass")
    User loginUser(@Param("uid") String id, @Param("pass") String password);

    boolean existsByUsername(String username);

    User findByUsername(String username);

    Optional<User> findOptionalByUsername(String username);
}
