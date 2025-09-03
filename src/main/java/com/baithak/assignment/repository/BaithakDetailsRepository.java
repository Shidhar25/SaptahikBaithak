package com.baithak.assignment.repository;

import com.baithak.assignment.model.Assignment;
import com.baithak.assignment.model.Person;
import com.baithak.assignment.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BaithakDetailsRepository extends JpaRepository<Assignment,Long> {

    List<Assignment> findByBaithakAtBetween(LocalDateTime start, LocalDateTime end);

    // If you also want filtering by person or place
    List<Assignment> findByPersonAndBaithakAtBetween(Person person, LocalDateTime start, LocalDateTime end);

    List<Assignment> findByPlaceAndBaithakAtBetween(Place place, LocalDateTime start, LocalDateTime end);
}
