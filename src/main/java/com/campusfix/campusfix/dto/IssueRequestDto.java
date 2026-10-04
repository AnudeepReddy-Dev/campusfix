package com.campusfix.campusfix.dto;

import com.campusfix.campusfix.enums.Priority;
import com.campusfix.campusfix.enums.Severity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class IssueRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "User priority is required")
    private Priority userPriority;

    @NotNull(message = "Severity is required")
    private Severity severity;

    @NotNull(message = "Safety risk is required")
    private Boolean safetyRisk;

    @NotNull(message = "People affected is required")
    @Min(value = 1, message = "People affected must be at least 1")
    private Integer peopleAffected;

    @NotNull(message = "Reported user id is required")
    private Long reportedById;

    @NotNull(message = "Category id is required")
    private Long categoryId;

    @NotNull(message = "Location id is required")
    private Long locationId;

    public IssueRequestDto() {
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

    public Long getReportedById() {
        return reportedById;
    }

    public void setReportedById(Long reportedById) {
        this.reportedById = reportedById;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}