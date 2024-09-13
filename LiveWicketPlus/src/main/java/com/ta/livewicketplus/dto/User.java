package com.ta.livewicketplus.dto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String favoriteTeams;
    private String favoritePlayers;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getFavoriteTeams() {
		return favoriteTeams;
	}

	public void setFavoriteTeams(String favoriteTeams) {
		this.favoriteTeams = favoriteTeams;
	}

	public String getFavoritePlayers() {
		return favoritePlayers;
	}

	public void setFavoritePlayers(String favoritePlayers) {
		this.favoritePlayers = favoritePlayers;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    
}
