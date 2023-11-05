package com.halilkrkn.usersapi.data.mapper;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {
    ModelMapper entityToDto();

    ModelMapper dtoToEntity();
}
