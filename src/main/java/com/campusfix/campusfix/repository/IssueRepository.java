package com.campusfix.campusfix.repository;

import com.campusfix.campusfix.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}