package com.chrisnkl.lmsauthserver.infrastructure.repository;

import com.chrisnkl.lmsauthserver.infrastructure.persistence.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    @EntityGraph(attributePaths = {
            "roles",
            "roles.permissions"
    })
    Optional<UserEntity> findByUsername(String username);

    @Query("""
    SELECT DISTINCT u
    FROM UserEntity u
    LEFT JOIN FETCH u.roles r
    LEFT JOIN FETCH r.permissions
    WHERE u.username = :username
    """)
    Optional<UserEntity> findByUsernameWithRolesAndPermissions(
            @Param("username") String username
    );

    @Modifying
    @Query("""
    UPDATE UserEntity u
    SET u.passwordHash = :passwordHash
    WHERE u.id = :userId
""")
    int updatePasswordHash(
            @Param("userId") UUID userId,
            @Param("passwordHash") String passwordHash
    );

    @Query("""
    SELECT DISTINCT u
    FROM UserEntity u
    LEFT JOIN FETCH u.roles r
    LEFT JOIN FETCH r.permissions
    WHERE u.id = :userId
    """)
    Optional<UserEntity> findByUserIdWithRolesAndPermissions(
            @Param("userId") UUID userId
    );

    Optional<UserEntity> findByEmail(String email);
}
