package com.tokioshool.filmotokio.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "person")
@Table(name = "people")
public class Person {
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name;

	@Column
	private String surname;

	@Column
	@Enumerated(value = EnumType.STRING)
	private TypePersonEnum typePersonEnum;

	@ManyToMany
	@JoinTable(name = "person_music",
			joinColumns = @JoinColumn(name = "person_id"),
			inverseJoinColumns = @JoinColumn(name = "film_id"))
	private List<Film> film_musicians;

	@ManyToMany
	@JoinTable(name = "person_actor",
			joinColumns = @JoinColumn(name = "person_id"),
			inverseJoinColumns = @JoinColumn(name = "film_id"))
	private List<Film> film_actors;

	@ManyToMany
	@JoinTable(name = "person_writer",
			joinColumns = @JoinColumn(name = "person_id"),
			inverseJoinColumns = @JoinColumn(name = "film_id"))
	private List<Film> film_screenwriters;

	@OneToMany(mappedBy = "director")
	private List<Film> director_films;

	@OneToMany(mappedBy = "photographer")
	private List<Film> photographer_films;

	@Override
	public String toString() {
		return super.toString();
	}
}
