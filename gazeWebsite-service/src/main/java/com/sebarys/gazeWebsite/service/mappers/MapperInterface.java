package com.sebarys.gazeWebsite.service.mappers;

public interface MapperInterface<DBO, DTO> {

    public DBO convertToDBO(final DTO dto);

    public DTO convertToDTO(final DBO dbo);

    public Iterable<DBO> convertToDBO(final Iterable<DTO> dtos);

    public Iterable<DTO> convertToDTO(final Iterable<DBO> dbos);

}