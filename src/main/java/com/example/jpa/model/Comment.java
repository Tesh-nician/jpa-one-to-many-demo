package com.example.jpa.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "comments")
public class Comment extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
        //un-deleted for //bi-directional mapping //
    @OnDelete(action = OnDeleteAction.CASCADE)
    //un-deleted for bi-directionl mapping//
    @JsonIgnore
    private Post post;



    //Toegevoegd, anders werkt de code niet!!!
    public Comment() {
    }

    //constructor toegevoegd voor bi-directional mapping
    public Comment(String text) {
        this.text = text;
    }



    public @NotNull String getText() {
        return text;
    }

    public void setText(@NotNull String text) {
        this.text = text;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", post=" + post +
                '}';
    }
}
