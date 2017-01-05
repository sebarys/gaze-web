package com.sebarys.gazeWebsite.model.dbo;

import javax.persistence.*;

@Entity
@Table(name = "user_roles",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"role", "username"}))
public class UserRole {
    private Integer userRoleId;
    private User user;
    private String role;

    public UserRole() {
    }

    public UserRole(Integer userRoleId, User user, String role) {
        this.userRoleId = userRoleId;
        this.user = user;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id", unique = true, nullable = false)
    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "role", nullable = false, length = 40)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
