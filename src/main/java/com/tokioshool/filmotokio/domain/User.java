package com.tokioshool.filmotokio.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "user")
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column
	private String name;
	
	@Column
	private String surname;
	
	@Column 
	private String email;
	
	@Column 
	private String image;
	
	@Column
	private Date birthDate;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate creationDate;
	
	@Column 
	private LocalDateTime lastLogin;
	
	@Column 
	private boolean active;
	
	@ManyToMany
	@JoinTable(name="user_role", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles;
	
	@OneToMany(mappedBy = "reUser")
	private List<Review> reviews;
	
	@OneToMany(mappedBy = "user_film")
	private List<Film> films;
	
	@OneToMany(mappedBy = "user_score")
	private List<Score> scores;
	
	@Override
	public String toString() {
		
		return super.toString();
	}
}
