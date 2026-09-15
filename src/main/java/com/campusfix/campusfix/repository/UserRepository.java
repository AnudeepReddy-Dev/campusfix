package com.campusfix.campusfix.repository;

import com.campusfix.campusfix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
