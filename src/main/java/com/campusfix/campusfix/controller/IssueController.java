package com.campusfix.campusfix.controller;

import com.campusfix.campusfix.dto.IssueRequestDto;
import com.campusfix.campusfix.dto.IssueResponseDto;
import com.campusfix.campusfix.service.IssueService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping
    public IssueResponseDto createIssue(@Valid @RequestBody IssueRequestDto dto) {
        return issueService.createIssue(dto);
    }

    @GetMapping
    public List<IssueResponseDto> getAllIssues() {
        return issueService.getAllIssues();
    }

    @GetMapping("/{id}")
    public IssueResponseDto getIssueById(@PathVariable Long id) {
        return issueService.getIssueById(id);
    }

    @PutMapping("/{id}")
    public IssueResponseDto updateIssue(
            @PathVariable Long id,
            @Valid @RequestBody IssueRequestDto dto) {

        return issueService.updateIssue(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
    }
}