package com.whitehacker.FixIssueWebsite.repository;

import com.whitehacker.FixIssueWebsite.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends PagingAndSortingRepository<Issue, Long>, JpaRepository<Issue, Long> {

}
