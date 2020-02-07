package com.cocktail.model.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder // builder를 사용할수 있게 합니다.
@Entity // jpa entity임을 알립니다.
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	@Column(name = "uid")
	private int uid;

	@JsonIgnore
	private String password;
	private String email;

	// private String image;
	// private String intro;
	private String nickname;

	// @OneToMany(mappedBy = "boardrecipe")
	// private List<BoardRecipe> boardRecipeList = new ArrayList<>();

	// @OneToMany(mappedBy = "user")
	// @JsonManagedReference
	// private List<UserScrap> userScrapList = new ArrayList<>();

	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private List<String> roles = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public String getUsername() {
		return this.email;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isEnabled() {
		return true;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	// @JsonManagedReference
	// @OneToMany
	// @Builder.Default
	// @JoinColumn(name = "cmid")
	// private List<Comments> commentsArray = new ArrayList<>();

	// @Column(insertable = false, updatable = false)
	// private LocalDateTime createDate;
	// @ManyToOne(targetEntity = Comments.class, fetch = FetchType.EAGER)
	// @JoinColumn(name = "cmid")
	// private Comments comments;

	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private List<CocktailLike> users = new ArrayList<>();

}
