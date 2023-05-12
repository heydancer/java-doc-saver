package com.heydancer;

import com.heydancer.entity.Mover;
import com.heydancer.entity.enums.MoverState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MoverRepository extends JpaRepository<Mover, Long> {
    Optional<Mover> findByTelegramId(Long id);
    List<Mover> findMoverByState(MoverState state);

    @Query("SELECT m FROM Mover AS m " +
            "WHERE m.state = :state")
    List<Mover> findMoversByState(MoverState state);

    List<Mover> findByLastName(String lastName);
}
