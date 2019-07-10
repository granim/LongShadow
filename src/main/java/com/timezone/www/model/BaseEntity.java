package com.timezone.www.model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entity")
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public boolean isNew() {
        return this.id == null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

