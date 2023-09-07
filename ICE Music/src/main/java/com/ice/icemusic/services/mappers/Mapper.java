package com.ice.icemusic.services.mappers;

import org.modelmapper.ModelMapper;

public abstract class Mapper {
    private static final ModelMapper mapper = new ModelMapper();

    public static <S, D> D map(final S source, final Class<D> destinationClass) {
        return mapper.map(source, destinationClass);
    }
}
