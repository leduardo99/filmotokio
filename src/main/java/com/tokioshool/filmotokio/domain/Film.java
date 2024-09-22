package com.tokioshool.filmotokio.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "film")
@Table(name = "films")
public class Film {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String title;

	@Column
	private int year;

	@Column
	private int duration;

	@Column
	private String synopsis;

	@Column
	private String poster;

	@Column
	private boolean migrate;

	@Column
	private LocalDate dateMigrate;

	@OneToMany(mappedBy = "film_review")
	private List<Review> reviews;

	@OneToMany(mappedBy = "film_score")
	private List<Score> scores;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user_film;

	@ManyToMany
	@JoinTable(
			name = "person_music",
			joinColumns = @JoinColumn(name = "film_id"),
			inverseJoinColumns = @JoinColumn(name = "person_id"))
	private List<Person> musicians;

	@ManyToMany
	@JoinTable(
			name = "person_actor",
			joinColumns = @JoinColumn(name = "film_id"),
			inverseJoinColumns = @JoinColumn(name = "person_id"))
	private List<Person> actors;

	@ManyToMany
	@JoinTable(
			name = "person_writer",
			joinColumns = @JoinColumn(name = "film_id"),
			inverseJoinColumns = @JoinColumn(name = "person_id"))
	private List<Person> screenwriters;

	@ManyToOne
	@JoinColumn(name = "director_id", nullable = true)
	private Person director;

	@ManyToOne
	@JoinColumn(name = "photo_id", nullable = true)
	private Person photographer;

	@Override
	public String toString() {
		return "Film [id=" + id + ", title=" + title + ", year=" + year + ", duration=" + duration + ", synopsis="
				+ synopsis + ", poster=" + poster + ", migrate=" + migrate + ", dateMigrate=" + dateMigrate + "]";
	}
}
