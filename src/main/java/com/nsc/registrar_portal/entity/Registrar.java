package com.nsc.registrar_portal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "registrars")
public class Registrar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String registrarId;

    private String fullname;
    private String password;
    private String email;
    private String phone;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String profilePic;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRegistrarId() { return registrarId; }
    public void setRegistrarId(String registrarId) { this.registrarId = registrarId; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getProfilePic() { return profilePic; }
    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }
}