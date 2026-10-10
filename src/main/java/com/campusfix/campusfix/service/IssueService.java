package com.campusfix.campusfix.service;

import com.campusfix.campusfix.dto.IssueRequestDto;
import com.campusfix.campusfix.dto.IssueResponseDto;
import com.campusfix.campusfix.entity.Category;
import com.campusfix.campusfix.entity.Issue;
import com.campusfix.campusfix.entity.Location;
import com.campusfix.campusfix.entity.User;
import com.campusfix.campusfix.enums.IssueStatus;
import com.campusfix.campusfix.enums.Priority;
import com.campusfix.campusfix.enums.Role;
import com.campusfix.campusfix.exception.ResourceNotFoundException;
import com.campusfix.campusfix.repository.CategoryRepository;
import com.campusfix.campusfix.repository.IssueRepository;
import com.campusfix.campusfix.repository.LocationRepository;
import com.campusfix.campusfix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    public IssueService(
            IssueRepository issueRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            LocationRepository locationRepository) {

        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
    }


    public IssueResponseDto createIssue(IssueRequestDto dto) {

        User reportedBy = userRepository
                .findById(dto.getReportedById())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id " + dto.getReportedById()
                        )
                );

        Category category = categoryRepository
                .findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id " + dto.getCategoryId()
                        )
                );

        Location location = locationRepository
                .findById(dto.getLocationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Location not found with id " + dto.getLocationId()
                        )
                );

        if (reportedBy == null || category == null || location == null) {
            return null;
        }

        Issue issue = new Issue();

        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        issue.setUserPriority(dto.getUserPriority());
        issue.setSeverity(dto.getSeverity());
        issue.setSafetyRisk(dto.getSafetyRisk());
        issue.setPeopleAffected(dto.getPeopleAffected());
        issue.setSystemPriority(calculateSystemPriority(dto));

        issue.setReportedBy(reportedBy);
        issue.setCategory(category);
        issue.setLocation(location);



        Issue savedIssue = issueRepository.save(issue);

        LocalDateTime deadline = calculateDeadline(
                savedIssue.getSystemPriority(),
                savedIssue.getCreatedAt()
        );

        savedIssue.setDeadline(deadline);

        savedIssue = issueRepository.save(savedIssue);

        return convertToResponseDto(savedIssue);
    }

    public List<IssueResponseDto> getAllIssues() {

        List<Issue> issues = issueRepository.findAll();

        List<IssueResponseDto> response = new ArrayList<>();

        for (Issue issue : issues) {
            response.add(convertToResponseDto(issue));
        }

        return response;
    }

    public IssueResponseDto getIssueById(Long id) {

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Issue not found with id " + id
                        )
                );

        return convertToResponseDto(issue);
    }

    public IssueResponseDto updateIssue(Long id, IssueRequestDto dto) {

        Issue existingIssue =
                issueRepository.findById(id).orElse(null);

        if (existingIssue == null) {
            return null;
        }

        User reportedBy =
                userRepository.findById(dto.getReportedById()).orElse(null);

        Category category =
                categoryRepository.findById(dto.getCategoryId()).orElse(null);

        Location location =
                locationRepository.findById(dto.getLocationId()).orElse(null);

        if (reportedBy == null || category == null || location == null) {
            return null;
        }

        existingIssue.setTitle(dto.getTitle());
        existingIssue.setDescription(dto.getDescription());
        existingIssue.setUserPriority(dto.getUserPriority());
        existingIssue.setSeverity(dto.getSeverity());
        existingIssue.setSafetyRisk(dto.getSafetyRisk());
        existingIssue.setPeopleAffected(dto.getPeopleAffected());

        existingIssue.setReportedBy(reportedBy);
        existingIssue.setCategory(category);
        existingIssue.setLocation(location);

        Issue updatedIssue = issueRepository.save(existingIssue);

        return convertToResponseDto(updatedIssue);
    }

    public void deleteIssue(Long id) {
        issueRepository.deleteById(id);
    }

    private IssueResponseDto convertToResponseDto(Issue issue) {

        Long assignedTechnicianId = null;

        if (issue.getAssignedTechnician() != null) {
            assignedTechnicianId = issue.getAssignedTechnician().getId();
        }

        return new IssueResponseDto(
                issue.getId(),
                issue.getTitle(),
                issue.getDescription(),
                issue.getUserPriority(),
                issue.getSystemPriority(),
                issue.getSeverity(),
                issue.getSafetyRisk(),
                issue.getPeopleAffected(),
                issue.getStatus(),
                issue.getCreatedAt(),
                issue.getUpdatedAt(),
                issue.getDeadline(),
                issue.getReportedBy().getId(),
                issue.getCategory().getId(),
                issue.getLocation().getId(),
                assignedTechnicianId
        );
    }
    private boolean isValidStatusTransition(
            IssueStatus currentStatus,
            IssueStatus newStatus) {

        return switch (currentStatus) {

            case REPORTED ->
                    newStatus == IssueStatus.ACKNOWLEDGED;

            case ACKNOWLEDGED ->
                    newStatus == IssueStatus.ASSIGNED;

            case ASSIGNED ->
                    newStatus == IssueStatus.IN_PROGRESS;

            case IN_PROGRESS ->
                    newStatus == IssueStatus.RESOLVED;

            case RESOLVED ->
                    newStatus == IssueStatus.CLOSED
                            || newStatus == IssueStatus.REOPENED;

            case REOPENED ->
                    newStatus == IssueStatus.ASSIGNED;

            case CLOSED ->
                    false;
        };
    }
    public IssueResponseDto updateIssueStatus(
            Long id,
            IssueStatus newStatus) {

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Issue not found with id " + id
                        )
                );

        if (!isValidStatusTransition(
                issue.getStatus(),
                newStatus)) {

            throw new IllegalStateException(
                    "Invalid status transition from "
                            + issue.getStatus()
                            + " to "
                            + newStatus
            );
        }

        issue.setStatus(newStatus);

        Issue updatedIssue = issueRepository.save(issue);

        return convertToResponseDto(updatedIssue);
    }

    private Priority calculateSystemPriority(IssueRequestDto dto) {

        int score = 0;

        switch (dto.getSeverity()) {
            case LOW -> score += 1;
            case MEDIUM -> score += 2;
            case HIGH -> score += 3;
            case CRITICAL -> score += 4;
        }

        if (Boolean.TRUE.equals(dto.getSafetyRisk())) {
            score += 3;
        }

        int people = dto.getPeopleAffected();

        if (people <= 10) {
            score += 1;
        } else if (people <= 50) {
            score += 2;
        } else {
            score += 3;
        }

        if (score <= 3) {
            return Priority.LOW;
        } else if (score <= 5) {
            return Priority.MEDIUM;
        } else if (score <= 7) {
            return Priority.HIGH;
        } else {
            return Priority.CRITICAL;
        }
    }
    public IssueResponseDto assignTechnician(
            Long issueId,
            Long technicianId) {

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Issue not found with id " + issueId
                        )
                );

        User technician = userRepository.findById(technicianId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id " + technicianId
                        )
                );

        if (technician.getRole() != Role.TECHNICIAN) {
            throw new IllegalStateException(
                    "Selected user is not a technician"
            );
        }

        if (issue.getStatus() != IssueStatus.ACKNOWLEDGED) {
            throw new IllegalStateException(
                    "Issue must be ACKNOWLEDGED before technician assignment"
            );
        }

        issue.setAssignedTechnician(technician);
        issue.setStatus(IssueStatus.ASSIGNED);

        Issue updatedIssue = issueRepository.save(issue);

        return convertToResponseDto(updatedIssue);
    }
    private LocalDateTime calculateDeadline(
            Priority priority,
            LocalDateTime createdAt) {

        return switch (priority) {
            case CRITICAL -> createdAt.plusHours(4);
            case HIGH -> createdAt.plusHours(24);
            case MEDIUM -> createdAt.plusDays(3);
            case LOW -> createdAt.plusDays(7);
        };
    }
}