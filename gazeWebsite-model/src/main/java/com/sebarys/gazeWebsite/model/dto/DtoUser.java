package com.sebarys.gazeWebsite.model.dto;

/**
 * Created by Kamil S on 2016-03-25.
 */
public class DtoUser {
    private Long id;
    private String password;
    private String username;
    private String email;
//    private Long basketId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//
//    public Long getBasketId() {
//        return basketId;
//    }
//
//    public void setBasketId(Long basketId) {
//        this.basketId = basketId;
//    }
}
