package com.development.baseapp.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {

    private String username;
    private String password;
    private Boolean enabled;
    private List<Authority> authorities;
    private LocalDateTime createDate;
    private LocalDateTime lockDate;
    private LocalDateTime expireDate;
    private String email;

    public Boolean islocked() {
        return lockDate != null;
    }

    public Boolean isExpired() {
        if (expireDate == null) {
            return Boolean.FALSE;
        }
        return expireDate.isBefore(LocalDateTime.now());
    }

}
