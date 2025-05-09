package likelion.side_project_blog.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;



    public Article(String title, String content, String author, String password) {
        this.title=title;
        this.content=content;
        this.author=author;
        this.password=password;
        this.createdAt=LocalDateTime.now();
    }


    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }


//    @PrePersist
//    protected void onCreate() {
//        this.createdAt = LocalDateTime.now();
//    }



}
