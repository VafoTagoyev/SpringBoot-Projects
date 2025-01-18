package com.whitehacker.FixIssueWebsite.controllers;

import com.whitehacker.FixIssueWebsite.model.Issue;
import com.whitehacker.FixIssueWebsite.services.IssueService;
import com.whitehacker.FixIssueWebsite.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Controller
public class IssueController {

    @Autowired
    private IssueService issueService;

    @GetMapping(value="/home")
    public String submit(Model model) {
        model.addAttribute("data", new Issue());
        return "index";
    }

    @PostMapping(value="/submit")
    public String saveNewIssue(Issue issue, @RequestParam("file") MultipartFile[] files) throws IOException {
        String[] fileIds = new String[files.length];
        for(int i = 0; i < files.length; i++) {
            String fileName = UUID.randomUUID().toString(); // generate unique name for every file
            String originalFileName = files[i].getOriginalFilename();
            assert originalFileName != null;
            String[] fileExtension = originalFileName.split("\\.");
            if (fileExtension.length > 1)
                fileName += "." + fileExtension[fileExtension.length - 1];
            fileIds[i] = fileName;
            FileUploadUtil.saveFile(fileName, files[i]);
        }

        issue.setFiles(fileIds);
        issueService.save(issue);

        return "redirect:/submit/issuesubmitted";
    }

    @GetMapping(value="/submit/issuesubmitted")
    public String issueSubmitted() {
        return "issue/issue-submitted";
    }
}
