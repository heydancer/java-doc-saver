package com.heydancer.mapper;

import com.heydancer.dto.MoverDTO;
import com.heydancer.entity.Mover;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MoverMapper implements BaseMapper<MoverDTO, Mover> {
    @Override
    public MoverDTO toDTO(Mover mover) {
        return MoverDTO.builder()
                .id(mover.getId())
                .firstName(mover.getFirstName())
                .lastName(mover.getLastName())
                .email(mover.getEmail())
                .link(mover.getLink())
                .subdivision(mover.getSubdivision())
                .isActive(mover.getIsActive())
                .state(mover.getState())
                .build();
    }

    @Override
    public List<MoverDTO> toDTOList(List<Mover> movers) {
        return movers.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
