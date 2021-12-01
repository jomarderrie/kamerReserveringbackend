package com.example.taskworklife.repo;

import com.example.taskworklife.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    @Query("select u from User u where u.achternaam =:achterNaam and u.naam = :naam")
    User findUserByAchternaamAndAndNaam(@Param("achterNaam") String achterNaam, @Param("naam") String naam);
}
