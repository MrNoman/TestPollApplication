package com.testTaskPoll.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "options")
public class Option implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn (name="content_id", referencedColumnName = "id"),
            @JoinColumn (name="poll_id", referencedColumnName = "poll_id")
    })
    @JsonBackReference
    @OrderBy ("id ASC")
    private Content content;

    @Column(name = "value")
    @NotEmpty
    @Size(min=1, max=90)
    private String value;

    @Column(name = "created_at") @CreationTimestamp
    private Timestamp createdAt;

    public Option() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }


}
