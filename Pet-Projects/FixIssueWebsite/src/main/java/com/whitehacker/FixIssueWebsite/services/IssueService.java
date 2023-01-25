package com.whitehacker.FixIssueWebsite.services;

import com.whitehacker.FixIssueWebsite.model.Issue;
import com.whitehacker.FixIssueWebsite.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepo;
    public void save(Issue issue) {
        issueRepo.save(issue);
    }

    public Issue findById(long id) {
        Issue issue = issueRepo.findById(id).get();
        return issue;
    }

    public List<Issue> findAll(){
        return (List<Issue>) issueRepo.findAll();
    }

    public Page<Issue> findPage(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,3);
        return issueRepo.findAll(pageable);
    }
}

