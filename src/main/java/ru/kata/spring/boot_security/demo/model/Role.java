package ru.kata.spring.boot_security.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_of_role")
    private String nameOfRole;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<User> people;

    public Role(){
    }

    public Role(String name_of_role) {
        this.nameOfRole = name_of_role;
    }

    public Role(Long id, String name_of_role, Collection<User> people) {
        this.id = id;
        this.nameOfRole = name_of_role;
        this.people = people;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfRole() {
        return nameOfRole;
    }

    public void setNameOfRole(String nameOfRole) {
        this.nameOfRole = nameOfRole;
    }

    public Collection<User> getPeople() {
        return people;
    }

    public void setPeople(Collection<User> people) {
        this.people = people;
    }


    @Override
    public String getAuthority() {
        return getNameOfRole();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(getId(), role.getId()) && Objects.equals(getNameOfRole(), role.getNameOfRole()) && Objects.equals(getPeople(), role.getPeople());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNameOfRole(), getPeople());
    }


    @Override
    public String toString() {
        return nameOfRole.substring("ROLE_".length());
    }
}
