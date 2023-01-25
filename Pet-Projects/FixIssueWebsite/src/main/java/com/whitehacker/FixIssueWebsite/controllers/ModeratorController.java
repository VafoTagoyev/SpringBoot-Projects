package com.whitehacker.FixIssueWebsite.controllers;

import com.whitehacker.FixIssueWebsite.model.Issue;
import com.whitehacker.FixIssueWebsite.model.User;
import com.whitehacker.FixIssueWebsite.repository.IssueRepository;
import com.whitehacker.FixIssueWebsite.security.CurrentUserFinder;
import com.whitehacker.FixIssueWebsite.services.IssueService;
import com.whitehacker.FixIssueWebsite.services.UserService;
import com.whitehacker.FixIssueWebsite.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {

    @Autowired
    UserService usService;
    @Autowired
    CurrentUserFinder currentUserFinder;
    @Autowired
    IssueService issueService;
    @Autowired
    IssueRepository issueRepository;


    @GetMapping
    public String moderatorHomePage(Model model) {
        long currentUserId = currentUserFinder.getCurrentUserId();
        User currentUser = usService.findById(currentUserId);
        model.addAttribute("currentUser", currentUser);
        return "moderator/home";
    }

    @GetMapping("/issues")
    public String getAllPages(Model model){
        return getOnePage(model, 1);
    }
    @GetMapping("/issues/page/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage){
        Page<Issue> page = issueService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();

        List<Issue> issues = page.getContent();
        List<Map<String, Object>> issueList = new ArrayList<Map<String, Object>>();
        for(Issue issue: issues) {
            Map<String, Object> issueMap = new HashMap<String, Object>();
            issueMap.put("issueId", issue.getIssueId());
            issueMap.put("firstName", issue.getFirstName());
            issueMap.put("lastName", issue.getLastName());
            issueMap.put("email", issue.getEmail());
            issueMap.put("title", issue.getTitle());
            issueMap.put("tags", Arrays.toString(issue.getTags()));
            issueList.add(issueMap);
        }

        model.addAttribute("issues", issueList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);

        return "moderator/show-issues";
    }

    @GetMapping(value="/issues/showFiles")
    public String downloadFiles(@RequestParam long issueId, Model model) {
        Issue issue = issueService.findById(issueId);
        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
        String[] files = issue.getFiles();
        String[] filePaths = new String[files.length];
        for(int i = 0; i < files.length; i++) {
            filePaths[i] = "/" + FileUploadUtil.uploadDir + "/" + files[i];
            Map<String, Object> fileMap = new HashMap<String, Object>();
            fileMap.put("name", files[i]);
            fileMap.put("path", filePaths[i]);
            fileList.add(fileMap);
        }
        model.addAttribute("files", fileList);
        return "moderator/show-files";
    }

}
