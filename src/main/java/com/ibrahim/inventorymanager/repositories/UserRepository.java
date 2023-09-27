package com.ibrahim.inventorymanager.repositories;

import com.ibrahim.inventorymanager.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String username);

}
