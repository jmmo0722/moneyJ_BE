package com.project.moneyj.user.repository;

import com.project.moneyj.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    List<User> findAllByEmailIn(List<String> emails);
}
