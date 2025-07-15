package io.github.steinsti.hrims.model;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Departments {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, unique = true)
    private String departmentName;

    @Column(nullable = false, unique=true)
    private String departmentCode;

    private String description;

    @Builder.Default
    private LocalDateTime createdAt = null;
    @Builder.Default
    private LocalDateTime updatedAt = null;
    @Builder.Default
    private Boolean isActive = true;

    @jakarta.persistence.PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @jakarta.persistence.PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}