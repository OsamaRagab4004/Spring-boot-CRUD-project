package com.flowna.app.deck.v1;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowna.app.Course.v1.Course;
import com.flowna.app.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"courses", "user"})
@Table(name = "deck")
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "deck_course",
            joinColumns = @JoinColumn(name="deck_id"),
            inverseJoinColumns = @JoinColumn(name="course_id")
    )
    private Set<Course> courses = new HashSet<>();

    // Utility method to add a course to the deck and maintain bidirectional relationship
    public void addNewCourse(Course course) {
        this.courses.add(course);
        course.getDecks().add(this); // Update the Course's decks set
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }



}
