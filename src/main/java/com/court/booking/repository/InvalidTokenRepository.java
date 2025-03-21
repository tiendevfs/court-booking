package com.court.booking.repository;

import com.court.booking.Model.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {
    boolean existsById(String id);
}
