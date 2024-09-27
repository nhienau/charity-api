package com.test.charity_api.dto;

import lombok.Data;

@Data
public class AccountDTO {

    private int id;
    private String username;
    private String password;
    private boolean status;

    public AccountDTO() {
    }

    public AccountDTO(int id, String username, String password, boolean status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
    }

}
