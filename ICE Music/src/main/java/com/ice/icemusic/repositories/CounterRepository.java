package com.ice.icemusic.repositories;

import com.ice.icemusic.entities.models.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Integer> {
    @Query(value = "SELECT * FROM counter", nativeQuery = true)
    Counter findCount();

    @Transactional
    @Modifying
    @Query(value = "UPDATE counter c SET c.count = :count WHERE c.id = 1", nativeQuery = true)
    void updateCount(@Param("count") final Long count);
}
