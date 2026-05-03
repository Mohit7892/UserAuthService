package com.scaler.userauthservice.repositories;

import com.scaler.userauthservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {

    @Override
    <S extends Session> S save(S entity);
    Optional<Session> findByToken(String token);
}
