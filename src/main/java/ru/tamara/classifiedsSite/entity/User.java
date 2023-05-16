package ru.tamara.classifiedsSite.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.tamara.classifiedsSite.dto.Role;

import javax.persistence.*;

import java.util.*;

/**
 * Класс, описывающий пользователя
 *
 * @see Image
 * @see Ad
 * @see Comment
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "site_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(length = 32, nullable = false)
    private String email;

    @Column(name = "first_name", length = 32)
    private String firstName;

    @Column(name = "last_name", length = 32)
    private String lastName;

    @Column(length = 18)
    private String phone;

    @OneToOne (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(length = 250, nullable = false)
    private String password;

    @Column(length = 32, name = "user_name", nullable = false)
    private String username;

    @Column(length = 5, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ad> ads = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = new HashSet<>();
        roles.add(this.role);
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(int id, String email, String firstName, String lastName, String phone, Image image, String password,
                String username, Role role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.image = image;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public User(String password, String username, Role role) {
        this.password = password;
        this.username = username;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (id != 0) {
            return id == user.id;
        } else {
            return Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName)
                    && Objects.equals(lastName, user.lastName) && Objects.equals(phone, user.phone)
                    && Objects.equals(image, user.image) && Objects.equals(password, user.password)
                    && Objects.equals(username, user.username) && role == user.role;
        }
    }

    @Override
    public int hashCode() {
        if (id != 0) {
            return Objects.hash(id);
        } else {
            return Objects.hash(email, firstName, lastName, phone, image, password, username, role);
        }
    }
}
