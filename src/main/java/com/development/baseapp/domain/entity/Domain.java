package com.development.baseapp.domain.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * The class <code>{@link Domain}</code> represents a simple domain model object.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Data
@Entity
public class Domain {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "INFO")
    private String info;

    @DateTimeFormat
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    public Domain() {
        createDate = LocalDateTime.now();
    }

    public Domain(final Long id, final String info) {
        this.id = id;
        this.info = info;
        createDate = LocalDateTime.now();
    }
}
