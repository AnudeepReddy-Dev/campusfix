package com.campusfix.campusfix.controller;

import com.campusfix.campusfix.entity.Issue;
import com.campusfix.campusfix.service.IssueService;
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
    public Issue createIssue(@RequestBody Issue issue) {
        return issueService.createIssue(issue);
    }

    @GetMapping
    public List<Issue> getAllIssues() {
        return issueService.getAllIssues();
    }

    @GetMapping("/{id}")
    public Issue getIssueById(@PathVariable Long id) {
        return issueService.getIssueById(id);
    }

    @PutMapping("/{id}")
    public Issue updateIssue(
            @PathVariable Long id,
            @RequestBody Issue issue) {

        return issueService.updateIssue(id, issue);
    }

    @DeleteMapping("/{id}")
    public void deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
    }
}