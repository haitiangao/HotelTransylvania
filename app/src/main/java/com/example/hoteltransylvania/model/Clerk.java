package com.example.hoteltransylvania.model;


public class Clerk {


    private String clerkUserName;

    private String clerkPassword;


    public Clerk(String clerkUserName, String clerkPassword) {
        this.clerkUserName = clerkUserName;
        this.clerkPassword = clerkPassword;
    }
    public Clerk(){}


    public String getClerkUserName() {
        return clerkUserName;
    }

    public void setClerkUserName(String clerkUserName) {
        this.clerkUserName = clerkUserName;
    }

    public String getClerkPassword() {
        return clerkPassword;
    }

    public void setClerkPassword(String clerkPassword) {
        this.clerkPassword = clerkPassword;
    }
}
