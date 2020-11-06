package com.authkit;

import java.security.Principal;
import java.util.Objects;
import java.util.Set;

public class AuthKitPrincipal implements Principal {

    private String issuer;
    private String subject;
    private String audience;
    private String givenName;
    private String familyName;
    private String email;
    private String preferredUsername;
    private Set<String> roles;
    private Set<String> permissions;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferredUsername() {
        return preferredUsername;
    }

    public void setPreferredUsername(String preferredUsername) {
        this.preferredUsername = preferredUsername;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getName() {
        return preferredUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthKitPrincipal that = (AuthKitPrincipal) o;
        return Objects.equals(issuer, that.issuer) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(audience, that.audience) &&
                Objects.equals(givenName, that.givenName) &&
                Objects.equals(familyName, that.familyName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(preferredUsername, that.preferredUsername) &&
                Objects.equals(roles, that.roles) &&
                Objects.equals(permissions, that.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issuer, subject, audience, givenName, familyName, email, preferredUsername, roles, permissions);
    }
}