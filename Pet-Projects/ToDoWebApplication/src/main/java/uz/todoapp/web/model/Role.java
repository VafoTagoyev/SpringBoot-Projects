package uz.todoapp.web.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
    @SequenceGenerator(name = "role_generator", sequenceName = "role_seq", allocationSize = 1)
    private Long id;

    @Column(length = 60, nullable = false, unique = true)
    private String name;

    // Default constructor
    public Role() {
    }

    // Constructor with parameters
    public Role(String name) {
        this.name = name;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
