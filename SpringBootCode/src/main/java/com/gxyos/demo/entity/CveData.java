package com.gxyos.demo.entity;

import javax.naming.ldap.PagedResultsControl;

public class CveData {
    private String Name;
    private String Status;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getReferences() {
        return References;
    }

    public void setReferences(String references) {
        References = references;
    }

    public String getPhase() {
        return Phase;
    }

    public void setPhase(String phase) {
        Phase = phase;
    }

    public String getVotes() {
        return Votes;
    }

    public void setVotes(String votes) {
        Votes = votes;
    }

    private String Description;
    private String References;
    private String Phase;
    private String Votes;






}
