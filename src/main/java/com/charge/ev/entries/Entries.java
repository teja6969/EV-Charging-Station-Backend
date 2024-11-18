package com.charge.ev.entries;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Component
@Entity
public class Entries {
    @Id
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String role;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(unique = true, nullable = false)
    private long phone;
    
    public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
	public String toString() {
		return "Entries [username=" + username + ", password=" + password + ", email=" + email + ", role=" + role
				+ ", createdAt=" + createdAt + ", phone=" + phone + "]";
	}
}
