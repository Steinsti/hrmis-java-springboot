package io.github.steinsti.hrims.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String  username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    public static Object builder() {
        throw new UnsupportedOperationException("Unimplemented method 'builder'");
    }    
}

