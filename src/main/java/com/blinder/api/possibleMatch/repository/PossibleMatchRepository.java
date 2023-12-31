package com.blinder.api.possibleMatch.repository;

import com.blinder.api.possibleMatch.model.PossibleMatch;
import com.blinder.api.possibleMatch.model.PossibleMatchStatus;
import com.blinder.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PossibleMatchRepository extends JpaRepository<PossibleMatch, String> {

    Optional<PossibleMatch> findPossibleMatchByFromAndTo(User from, User to);

    @Query("SELECT pm FROM PossibleMatch pm WHERE (pm.to = :user AND pm.status = 'MATCHED')")
    List<PossibleMatch> findMatchedUsers(@Param("user") User user);

    @Query("SELECT pm FROM PossibleMatch pm WHERE (pm.from = :user AND pm.status = 'LIKED')")
    List<PossibleMatch> findLikedUsers(@Param("user") User user);

    @Query("SELECT pm FROM PossibleMatch pm WHERE (pm.to = :user AND pm.status = 'LIKED')")
    List<PossibleMatch> findUsersWhoLike(@Param("user") User user);

    @Query("SELECT pm FROM PossibleMatch pm WHERE (pm.to = :user AND pm.status = 'DISLIKED')")
    List<PossibleMatch> findDislikedUsers(@Param("user") User user);

    List<PossibleMatch> findAllPossibleMatchesByFrom(User from);

    List<PossibleMatch> findAllPotentialMatchesByFromAndStatus(User from, PossibleMatchStatus status);

}