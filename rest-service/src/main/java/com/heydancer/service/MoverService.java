package com.heydancer.service;

import com.heydancer.dto.MoverDTO;
import com.heydancer.entity.Mover;

import java.util.List;

public interface MoverService {
    List<MoverDTO> getAllConfirmed();

    List<MoverDTO> getMoversInConfirmationStatus();

    void replyToAll(String message);

    List<MoverDTO> getAllByLastName(String filter);

    void save(Mover mover, String firstName, String lastName, String email, String link, String subdivision);

    void changeRegistrationState(Long moverId, Boolean status);
}
