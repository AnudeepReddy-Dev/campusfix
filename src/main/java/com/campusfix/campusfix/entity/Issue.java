package com.campusfix.campusfix.entity;

import com.campusfix.campusfix.enums.IssueStatus;
import com.campusfix.campusfix.enums.Priority;
import com.campusfix.campusfix.enums.Severity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Priority userPriority;

    @Enumerated(EnumType.STRING)
    private Priority systemPriority;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    private Boolean safetyRisk;

    private Integer peopleAffected;

    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "reported_by_id")
    private User reportedBy;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "assigned_technician_id")
    private User assignedTechnician;

    public Issue() {
    }

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (status == null) {
            status = IssueStatus.REPORTED;
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getUserPriority() {
        return userPriority;
    }

    public void setUserPriority(Priority userPriority) {
        this.userPriority = userPriority;
    }

    public Priority getSystemPriority() {
        return systemPriority;
    }

    public void setSystemPriority(Priority systemPriority) {
        this.systemPriority = systemPriority;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Boolean getSafetyRisk() {
        return safetyRisk;
    }

    public void setSafetyRisk(Boolean safetyRisk) {
        this.safetyRisk = safetyRisk;
    }

    public Integer getPeopleAffected() {
        return peopleAffected;
    }

    public void setPeopleAffected(Integer peopleAffected) {
        this.peopleAffected = peopleAffected;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
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

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getAssignedTechnician() {
        return assignedTechnician;
    }

    public void setAssignedTechnician(User assignedTechnician) {
        this.assignedTechnician = assignedTechnician;
    }
}