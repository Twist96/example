package com.gasco.Models;

public class User {
    private String href;
    private String id;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private String loginId;
    private boolean loginEnabled;
    private String passwordHint = null;
    private float successiveFailedLogins;
    private String roleTypeId;
    private String roleName;
    private boolean requirePasswordChange;
    private String primaryEmail = null;
    private String primaryPhone = null;
    private String lastSignInDate;


    // Getter Methods

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }

    public boolean getEnabled() {
        return enabled;
    }

    public String getLoginId() {
        return loginId;
    }

    public boolean getLoginEnabled() {
        return loginEnabled;
    }

    public String getPasswordHint() {
        return passwordHint;
    }

    public float getSuccessiveFailedLogins() {
        return successiveFailedLogins;
    }

    public String getRoleTypeId() {
        return roleTypeId;
    }

    public String getRoleName() {
        return roleName;
    }

    public boolean getRequirePasswordChange() {
        return requirePasswordChange;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public String getLastSignInDate() {
        return lastSignInDate;
    }

    // Setter Methods

    public void setHref(String href) {
        this.href = href;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setLoginEnabled(boolean loginEnabled) {
        this.loginEnabled = loginEnabled;
    }

    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

    public void setSuccessiveFailedLogins(float successiveFailedLogins) {
        this.successiveFailedLogins = successiveFailedLogins;
    }

    public void setRoleTypeId(String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setRequirePasswordChange(boolean requirePasswordChange) {
        this.requirePasswordChange = requirePasswordChange;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public void setLastSignInDate(String lastSignInDate) {
        this.lastSignInDate = lastSignInDate;
    }

}
