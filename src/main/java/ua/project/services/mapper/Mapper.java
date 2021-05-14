package ua.project.services.mapper;


public interface Mapper<D, E> {

    D mapToEntity(E dto);

    E mapToDto(D entity);
}
