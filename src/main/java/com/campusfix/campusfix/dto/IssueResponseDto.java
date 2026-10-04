package com.campusfix.campusfix.dto;

import com.campusfix.campusfix.enums.IssueStatus;
import com.campusfix.campusfix.enums.Priority;
import com.campusfix.campusfix.enums.Severity;

import java.time.LocalDateTime;

public class IssueResponseDto {

    private Long id;
    private String title;
    private String description;
    private Priority userPriority;
    private Priority systemPriority;
    private Severity severity;
    private Boolean safetyRisk;
    private Integer peopleAffected;
    private IssueStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deadline;

    private Long reportedById;
    private Long categoryId;
    private Long locationId;
    private Long assignedTechnicianId;

    public IssueResponseDto() {
    }

    public IssueResponseDto(
            Long id,
            String title,
            String description,
            Priority userPriority,
            Priority systemPriority,
            Severity severity,
            Boolean safetyRisk,
            Integer peopleAffected,
            IssueStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deadline,
            Long reportedById,
            Long categoryId,
            Long locationId,
            Long assignedTechnicianId) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.userPriority = userPriority;
        this.systemPriority = systemPriority;
        this.severity = severity;
        this.safetyRisk = safetyRisk;
        this.peopleAffected = peopleAffected;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deadline = deadline;
        this.reportedById = reportedById;
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.assignedTechnicianId = assignedTechnicianId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getUserPriority() {
        return userPriority;
    }

    public Priority getSystemPriority() {
        return systemPriority;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Boolean getSafetyRisk() {
        return safetyRisk;
    }

    public Integer getPeopleAffected() {
        return peopleAffected;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public Long getReportedById() {
        return reportedById;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public Long getAssignedTechnicianId() {
        return assignedTechnicianId;
    }
}