package com.development.baseapp.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Authority {

    private Role role;
    private LocalDateTime grantDate;
    private LocalDateTime revokeDate;

    public Authority() {
        grantDate = LocalDateTime.now();
    }
}
