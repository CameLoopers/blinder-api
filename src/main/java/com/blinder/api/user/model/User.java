package com.blinder.api.user.model;

import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.filter.model.Filter;
import com.blinder.api.location.model.Location;
import com.blinder.api.model.BaseEntity;
import com.blinder.api.possibleMatch.model.PossibleMatch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String surname;
    private String nickname;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    private Gender gender;

    private Date birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images = new ArrayList<>();

    //@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "filter_id")
    private Filter filter;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> blockedUsers = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "from_user_id")
    private List<PossibleMatch> possibleMatches = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "characteristics_id")
    private Characteristics characteristics;

    private boolean isMatched = false;
    private boolean isBanned = false;

    @Override
    public void onPrePersist() {
        super.onPrePersist();
        this.isMatched = false;
        this.isBanned = false;
    }

    public int getAge() {
        Date now = new Date();
        long diff = now.getTime() - birthDate.getTime();
        return (int) (diff / (1000L * 60 * 60 * 24 * 365));
    }
}

