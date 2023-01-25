package com.whitehacker.FixIssueWebsite.model;

import jakarta.persistence.*;

@Entity
@Table(name="issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long issueId;

    @Column(name="email")
    private String email;
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @Column(name="title")
    private String title;

    @Column(name="filenames")
    private String files;

    @Column(name="tags")
    private String tags;
    public Issue() {

    }
    public Issue(String email, String firstName, String lastName, String title, String[] files, String[] tags) {
        super();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.files = String.join(",", files);
        this.tags = String.join(",", tags);
    }

    public long getIssueId() {return issueId;}

    public void setIssueId(long issueId) {this.issueId = issueId;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String[] getFiles() {return files.split(",");}

    public void setFiles(String[] files) {this.files = String.join(",", files);}

    public String[] getTags() {return tags.split(",");}

    public void setTags(String[] tags) {this.tags = String.join(",", tags);}
}

