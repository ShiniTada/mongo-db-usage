package com.example.demo.service.mapper;

public interface IMapper<M, D> {
    D mapModelToDto(M model);

    M mapDtoToModel(D dto);
}
