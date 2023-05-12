package com.heydancer.mapper;

import java.util.List;

public interface BaseMapper<D, M> {
    D toDTO(M m);

    List<D> toDTOList(List<M> mList);
}