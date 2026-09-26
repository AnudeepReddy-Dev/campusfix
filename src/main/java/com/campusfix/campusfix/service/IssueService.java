package com.campusfix.campusfix.service;

import com.campusfix.campusfix.entity.Issue;
import com.campusfix.campusfix.repository.IssueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Issue createIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    public Issue getIssueById(Long id) {
        return issueRepository.findById(id).orElse(null);
    }

    public Issue updateIssue(Long id, Issue issue) {

        Issue existingIssue =
                issueRepository.findById(id).orElse(null);

        if (existingIssue == null) {
            return null;
        }

        existingIssue.setTitle(issue.getTitle());
        existingIssue.setDescription(issue.getDescription());
        existingIssue.setUserPriority(issue.getUserPriority());
        existingIssue.setSystemPriority(issue.getSystemPriority());
        existingIssue.setSeverity(issue.getSeverity());
        existingIssue.setSafetyRisk(issue.getSafetyRisk());
        existingIssue.setPeopleAffected(issue.getPeopleAffected());
        existingIssue.setStatus(issue.getStatus());
        existingIssue.setDeadline(issue.getDeadline());
        existingIssue.setReportedBy(issue.getReportedBy());
        existingIssue.setCategory(issue.getCategory());
        existingIssue.setLocation(issue.getLocation());
        existingIssue.setAssignedTechnician(issue.getAssignedTechnician());

        return issueRepository.save(existingIssue);
    }

    public void deleteIssue(Long id) {
        issueRepository.deleteById(id);
    }
}