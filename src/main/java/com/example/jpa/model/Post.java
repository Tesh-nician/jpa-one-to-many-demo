package com.example.jpa.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Entity
@Table(name = "posts")
public class Post extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String title;
    @NotNull
    @Size(max = 250)
    private String description;
    @NotNull
    @Lob
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @Size(max = 100) String getTitle() {
        return title;
    }

    public void setTitle(@NotNull @Size(max = 100) String title) {
        this.title = title;
    }

    public @NotNull @Size(max = 250) String getDescription() {
        return description;
    }

    public void setDescription(@NotNull @Size(max = 250) String description) {
        this.description = description;
    }

    public @NotNull String getContent() {
        return content;
    }

    public void setContent(@NotNull String content) {
        this.content = content;
    }

    // Getters en Setters (weggelaten omwille van de beknoptheid): done with autogenerate
}