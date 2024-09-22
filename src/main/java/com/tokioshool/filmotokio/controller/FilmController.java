package com.tokioshool.filmotokio.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tokioshool.filmotokio.config.HelperRest;
import com.tokioshool.filmotokio.domain.Film;
import com.tokioshool.filmotokio.domain.Person;
import com.tokioshool.filmotokio.domain.Review;
import com.tokioshool.filmotokio.domain.Score;
import com.tokioshool.filmotokio.domain.TypePersonEnum;
import com.tokioshool.filmotokio.domain.User;
import com.tokioshool.filmotokio.dto.ReviewDTO;
import com.tokioshool.filmotokio.security.JwtResponse;
import com.tokioshool.filmotokio.service.FileService;
import com.tokioshool.filmotokio.service.FilmService;
import com.tokioshool.filmotokio.service.PersonService;
import com.tokioshool.filmotokio.service.ScoreService;
import com.tokioshool.filmotokio.service.UserService;

@Controller
public class FilmController {
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private final static String URL_NEW_REVIEW = "http://localhost:8090/new-review";
	private final static String URL_LOGIN = "http://localhost:8090/login";
	
	public static String usernameRest;
	public static String passwordRest;
	
	@Value("${url.restapi.username}")
	public void setUsernameRest(String u) {
		usernameRest = u;
	}
	
	@Value("${url.restapi.password}")
	public void setPasswordRest(String u) {
		passwordRest = u;
	}
	
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private FilmService filmService;
	@Autowired
	private PersonService personService;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private UserService userService;
	@Autowired
	private FileService fileService;
	
	private ReviewDTO convertToDTOReview(Review review) {
		return modelMapper.map(review, ReviewDTO.class);
	}
	
	@GetMapping("/search-film")
	public String searchFilm() {
		return "search-film";
	}
	
	@GetMapping("/films-result")
	public String filmsResult(@RequestParam ("title") String title, Model model) {
		ArrayList<Film> films = filmService.findByTitlePiece(title);
		model.addAttribute("films", films);
		return "films-result";
	}
	
	@GetMapping("film/{id}")
	public String film(@PathVariable Long id, Model model) {
		String mediaString = "Nenhuma avaliação";
		Film film = filmService.findById(id);
		User userActive = userService.findByActive(true);
		Review review = new Review();
		List<ReviewDTO> reviewsDTO = null;
		try {
			ResponseEntity<ReviewDTO[]> responseEntity = null;
			User authenticationUser = HelperRest.getAuthenticationUser();
			String authenticationBody = HelperRest.getBody(authenticationUser);
			HttpHeaders authenticationHeader = HelperRest.getHeaders();
			HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeader);
			ResponseEntity<JwtResponse> authenticationResponse = restTemplate.exchange(URL_LOGIN, HttpMethod.POST, authenticationEntity, JwtResponse.class);
			if(authenticationResponse.getStatusCode().equals(HttpStatus.OK)) {
				String token = "Bearer " + authenticationResponse.getBody().getToken();
				HttpHeaders headers = HelperRest.getHeaders();
				headers.set("Authorization", token);
				HttpEntity<Void> jwtEntity = new HttpEntity<>(headers);
				responseEntity = restTemplate.exchange("http://localhost:8090/film/" + film.getId() + "/reviews", HttpMethod.GET, jwtEntity,ReviewDTO[].class);
				ReviewDTO[] revi = responseEntity.getBody();
				reviewsDTO = Arrays.asList(revi);
			}
				
		}catch (Exception e) {
			logger.error("Não foi possível aceder ao serviço REST");
		}
		model.addAttribute("film", film);
		model.addAttribute("score", new Score());
		model.addAttribute("review",review);
		model.addAttribute("reviews", reviewsDTO);
		List<Score> mediaScores = scoreService.findByFilmName(film.getTitle());
		double media = 0;
		
		boolean hasScore = false;
		boolean hasReview = false;
		for (Score sco : film.getScores()) {
			if(sco.getUser_score() == userActive ) {
				hasScore = true;
			}
		}
		for (Review rev : film.getReviews()) {
			if(rev.getReUser() == userActive ) {
				hasReview = true;
			}
		}
		double suma = 0;
		if(mediaScores.size()>=1) {
			
			for (Score score2 : mediaScores) {
				suma = suma + score2.getValue();
			}
			media = suma / mediaScores.size();
			DecimalFormat df = new DecimalFormat("#.0");
			mediaString = df.format(media);
		}
		model.addAttribute("hasScore", hasScore);
		model.addAttribute("hasReview", hasReview);
		model.addAttribute("media", mediaString);
		return "film";
	}
	
	@PostMapping("/new-score")
	public String newScore(@ModelAttribute @Validated Score score, BindingResult Bresult, Model model, RedirectAttributes rAttributes) {
		Film film = filmService.findByTitle(score.getNameFilm());
		User userActive = userService.findByActive(true);
		scoreService.add(score, film, userActive);
		logger.info("Pontuação adicionada");
		rAttributes.addFlashAttribute("menssagem", "Pontuação adicionada");
		rAttributes.addFlashAttribute("messagemclass", "alert alert-success");
		return "redirect:/film/" + film.getId();
	}
	
	@PostMapping("/new-review")
	private String getReview(@ModelAttribute @Validated Review review, BindingResult Bresult, RedirectAttributes rAttributes) {
		Film film = filmService.findByTitle(review.getFilm_review().getTitle());
		ReviewDTO reviewDTO = convertToDTOReview(review); 
		try {
			@SuppressWarnings("unused")
			ResponseEntity<ReviewDTO> responseEntity = null;
			User authenticationUser = HelperRest.getAuthenticationUser();
			String authenticationBody = HelperRest.getBody(authenticationUser);
			HttpHeaders authenticationHeader = HelperRest.getHeaders();
			HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeader);
			ResponseEntity<JwtResponse> authenticationResponse = restTemplate.exchange(URL_LOGIN, HttpMethod.POST, authenticationEntity, JwtResponse.class);
			if(authenticationResponse.getStatusCode().equals(HttpStatus.OK)) {
				String token = "Bearer " + authenticationResponse.getBody().getToken();
				HttpHeaders headers = HelperRest.getHeaders();
				headers.set("Authorization", token);
				HttpEntity<ReviewDTO> jwtEntity = new HttpEntity<>(reviewDTO, headers);
				responseEntity = restTemplate.exchange(URL_NEW_REVIEW, HttpMethod.POST, jwtEntity, ReviewDTO.class);
			}	
		}catch (Exception e) {
			logger.error("Não foi possível aceder ao serviço REST");
		}
		rAttributes.addFlashAttribute("mensagemRe", "Revisão adicionada");
		rAttributes.addFlashAttribute("mensagemclassRe", "alert alert-success");
		return "redirect:/film/" + film.getId();
	}
	
	@GetMapping("/new-person")
	public String newPerson(Model model) {
		model.addAttribute("person", new Person());
		return "new-person";
	}
	
	@PostMapping("/create-person")
	public String createPerson(@ModelAttribute Person person, Model model,  RedirectAttributes rAttributes) {
		Pattern nomPat = Pattern.compile("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+");
		Matcher nombre = nomPat.matcher(person.getName());
		Matcher apellido = nomPat.matcher(person.getSurname());
		if(nombre.matches() && apellido.matches()) {
			personService.add(person);
			rAttributes.addFlashAttribute("mensagem", "Persona adicionada");
			rAttributes.addFlashAttribute("mensagemclass", "alert alert-success");
		}else {
			rAttributes.addFlashAttribute("mensagem", "Erro de dados");
			rAttributes.addFlashAttribute("mensagemclass", "alert alert-danger");
		}
		return "redirect:/new-person";
	}
	
	@GetMapping("/new-film")
	public String newFilm(Model model){
		ArrayList<Person> directores = personService.findByTypePersonEnum(TypePersonEnum.DIRECTOR);
		model.addAttribute("directores", directores);
		ArrayList<Person> guionistas = personService.findByTypePersonEnum(TypePersonEnum.GUIONISTA);
		model.addAttribute("guionistas", guionistas);
		ArrayList<Person> atores = personService.findByTypePersonEnum(TypePersonEnum.ACTOR);
		model.addAttribute("actores", atores);
		ArrayList<Person> musicos = personService.findByTypePersonEnum(TypePersonEnum.MUSICO);
		model.addAttribute("musicos", musicos);
		ArrayList<Person> fotografos = personService.findByTypePersonEnum(TypePersonEnum.FOTOGRAFO);
		model.addAttribute("fotografos", fotografos);
		model.addAttribute("film", new Film());
		return "new-film";
	}
	
	@PostMapping("/create-film")
	public String uploadCartel(@ModelAttribute @Validated Film film, BindingResult Bresult, 
			@RequestParam ("poster") MultipartFile imageFile, RedirectAttributes rAttributes) {
		Pattern patTitulo = Pattern.compile("^[0-9a-zA-ZÀ-ÿ\\u00f1\\u00d1\s\\{\\}\\(\\)\\.\\,\\;\\:]+$");
		Pattern patNum = Pattern.compile("[0-9]{4}");
		Pattern patDur = Pattern.compile("^[0-9]+");
		Matcher anyo = patNum.matcher(Integer.toString(film.getYear()));
		Matcher duration = patDur.matcher(Integer.toString(film.getDuration()));
		Matcher titulo = patTitulo.matcher(film.getTitle());
		Matcher sinopsis = patTitulo.matcher(film.getSynopsis());
		if(anyo.matches() && duration.matches() && titulo.matches() && sinopsis.matches()) {
		 if(!imageFile.isEmpty()) {
			 fileService.uploadFile(imageFile);
		 }
		film.setPoster(imageFile.getOriginalFilename());
		User activeUser = userService.findByActive(true);
		film.setUser_film(activeUser);
		filmService.add(film);
		logger.info("Filme " + film.getTitle() + " adicionado") ;
		rAttributes.addFlashAttribute("mensagem", "Filme adicionado");
		rAttributes.addFlashAttribute("mensagemclass", "alert alert-success");
		}else if(!anyo.matches()){
			rAttributes.addFlashAttribute("mensagem", "Introduzir um ano correto (ex: 2004)");
			rAttributes.addFlashAttribute("mensagemclass", "alert alert-danger");
		}else if(!duration.matches()){
			rAttributes.addFlashAttribute("mensagem", "Introduzir a duração correta (minutos)");
			rAttributes.addFlashAttribute("mensagemclass", "alert alert-danger");
		}else if(!titulo.matches()){
			rAttributes.addFlashAttribute("mensagem", "Introduzir um título correto");
			rAttributes.addFlashAttribute("mensagemclass", "alert alert-danger");
		}else if(!sinopsis.matches()){
			rAttributes.addFlashAttribute("mensagem", "Introduzir uma sinopse correta");
			rAttributes.addFlashAttribute("mensagemclass", "alert alert-danger");
		}
		return "redirect:/new-film";
	}
	
	
}
