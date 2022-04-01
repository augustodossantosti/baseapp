package com.development.baseapp.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * The class <code>{@link Domain}</code> represents a simple domain model object.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Data
@AllArgsConstructor
@Document(collection = "domain")
public class Domain {

    @Id
    private String id;
    private String info;
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime createDate;

    public Domain() {
        info = "";
        createDate = LocalDateTime.now();
    }

    public Domain(final String info) {
        this.info = info;
        createDate = LocalDateTime.now();
    }
}
