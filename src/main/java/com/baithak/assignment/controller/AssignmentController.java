package com.baithak.assignment.controller;

import com.baithak.assignment.dto.AssignmentDto;
import com.baithak.assignment.dto.PlaceOverviewDto;
import com.baithak.assignment.service.impl.AssignmentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentServiceImpl assignmentServiceImpl;

    // show last 10 locations
    @GetMapping("/v1/person/{id}/history")
    public ResponseEntity<List<AssignmentDto>> getHistory(@PathVariable Long id) {
        log.info("Fetching last 10 history records for personId={}", id);
        return ResponseEntity.ok(assignmentServiceImpl.getLast10Meetings(id));
    }

    // manually assign new location
    @PostMapping("/v1/person/{id}/assign")
    public ResponseEntity<AssignmentDto> assignMeeting(
            @PathVariable Long id,
            @RequestParam Long placeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Assigning new meeting for personId={} placeId={} date={}", id, placeId, date);
        return ResponseEntity.ok(assignmentServiceImpl.assignMeeting(id, placeId, date));
    }

    @GetMapping("/v1/overview")
    public ResponseEntity<List<PlaceOverviewDto>> getOverview() {
        return ResponseEntity.ok(assignmentServiceImpl.getPlacesOverview());
    }

    // Update only person
    @PutMapping("/v1/assignment/{assignmentId}/person/{personId}")
    public ResponseEntity<AssignmentDto> updateMeetingPerson(
            @PathVariable Long assignmentId,
            @PathVariable Long personId) {

        log.info("Updating person for assignmentId={} to personId={}", assignmentId, personId);
        return ResponseEntity.ok(assignmentServiceImpl.updateMeetingPerson(assignmentId, personId));
    }

    // Update only date
    @PutMapping("/v1/assignment/{assignmentId}/date")
    public ResponseEntity<AssignmentDto> updateMeetingDate(
            @PathVariable Long assignmentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        log.info("Updating meeting date for assignmentId={} to {}", assignmentId, date);
        return ResponseEntity.ok(assignmentServiceImpl.updateMeetingDate(assignmentId, date));
    }


//    History by range

    // 🔹 History by meetingDate range
    @GetMapping("/history")
    public ResponseEntity<List<AssignmentDto>> getAssignmentHistory(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        return ResponseEntity.ok(assignmentServiceImpl.getAssignmentHistory(fromDate, toDate));
    }

    // 🔹 History by person + meetingDate range
    @GetMapping("/history/person/{personId}")
    public ResponseEntity<List<AssignmentDto>> getAssignmentHistoryByPerson(
            @PathVariable Long personId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        return ResponseEntity.ok(assignmentServiceImpl.getAssignmentHistoryByPerson(personId, fromDate, toDate));
    }

    // 🔹 History by place + meetingDate range
    @GetMapping("/history/place/{placeId}")
    public ResponseEntity<List<AssignmentDto>> getAssignmentHistoryByPlace(
            @PathVariable Long placeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        return ResponseEntity.ok(assignmentServiceImpl.getAssignmentHistoryByPlace(placeId, fromDate, toDate));
    }

    // 🔹 Optional: History by createdAt range (audit trail / logs)
    @GetMapping("/history/created")
    public ResponseEntity<List<AssignmentDto>> getAssignmentHistoryByCreatedAt(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDateTime
    ) {
        return ResponseEntity.ok(assignmentServiceImpl.getAssignmentHistoryByCreatedAt(fromDateTime, toDateTime));
    }


}
