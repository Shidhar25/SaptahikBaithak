package com.baithak.assignment.repository;

import com.baithak.assignment.model.PersonAssignment;
import com.baithak.assignment.model.Place;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonAssignmentRepository extends JpaRepository<PersonAssignment, Long> {

    // last 10 meetings for a person (descending by date)
    @Query("SELECT pa FROM PersonAssignment pa " +
            "WHERE pa.person.personId = :personId " +
            "ORDER BY pa.meetingDate DESC")
    List<PersonAssignment> findRecentMeetings(Long personId, Pageable pageable);

    // full history for export
    List<PersonAssignment> findByPerson_PersonIdOrderByMeetingDateAsc(Long personId);

    // find the latest assignment for a given place
    Optional<PersonAssignment> findTopByPlaceAndMeetingDateBetweenOrderByMeetingDateDesc(
            Place place,
            LocalDate startDate,
            LocalDate endDate
    );
    // 🔹 All assignments within a date range
    List<PersonAssignment> findByMeetingDateBetween(LocalDate startDate, LocalDate endDate);

    // 🔹 Filter by person + date range
    List<PersonAssignment> findByPerson_PersonIdAndMeetingDateBetween(
            Long personId,
            LocalDate startDate,
            LocalDate endDate
    );

    // 🔹 Filter by place + date range
    List<PersonAssignment> findByPlaceAndMeetingDateBetween(
            Place place,
            LocalDate startDate,
            LocalDate endDate
    );

    // 🔹 Find by createdAt range (audit/logs)
    List<PersonAssignment> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}
