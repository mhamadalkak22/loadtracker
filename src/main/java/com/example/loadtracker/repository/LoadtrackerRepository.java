package com.example.loadtracker.repository;
import com.example.loadtracker.entity.Loadtracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface LoadtrackerRepository extends JpaRepository<Loadtracker, Long> {
    List<Loadtracker> findByShipperId(String shipperId);

}
