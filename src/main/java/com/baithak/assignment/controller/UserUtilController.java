package com.baithak.assignment.controller;

import com.baithak.assignment.dto.UserDetailDto;
import com.baithak.assignment.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserUtilController {

    private final MemberService memberService;

    // all users
    @GetMapping("/v1/all")
    public ResponseEntity<List<UserDetailDto>> getAllMembers() {
        log.info("Received request to fetch all users");
        List<UserDetailDto> users = memberService.findAll();
        log.debug("Returning {} users", users.size());
        return ResponseEntity.ok(users);
    }

    // female users
    @GetMapping("/v1/all/female")
    public ResponseEntity<List<UserDetailDto>> getAllFemaleMembers() {
        log.info("Received request to fetch all female users");
        List<UserDetailDto> females = memberService.findAllFemale();
        log.debug("Returning {} female users", females.size());
        return ResponseEntity.ok(females);
    }

    // male users
    @GetMapping("/v1/all/male")
    public ResponseEntity<List<UserDetailDto>> getAllMaleMembers() {
        log.info("Received request to fetch all male users");
        List<UserDetailDto> males = memberService.findAllMale();
        log.debug("Returning {} male users", males.size());
        return ResponseEntity.ok(males);
    }
}
