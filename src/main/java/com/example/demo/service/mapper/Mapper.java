package com.example.demo.service.mapper;

public interface Mapper<M, D> {
    D mapModelToDto(M model);

    M mapDtoToModel(D dto);
}
