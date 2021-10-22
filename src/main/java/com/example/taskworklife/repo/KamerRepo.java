package com.example.taskworklife.repo;


import com.example.taskworklife.models.Kamer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KamerRepo extends JpaRepository<Kamer, Long> {
        Kamer findByNaam(String kamerNaam);
}
