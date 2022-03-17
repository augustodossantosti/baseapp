package com.development.baseapp.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The class <code>{@link DomainRepository}</code> is the <i>repository</i> of
 * {@link Domain}. This class is managed by <i>Spring Data Rest</i> and provides
 * some endpoints for retrive domain data information.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Repository
@RepositoryRestResource(collectionResourceRel = "domains", path = "domains")
public interface DomainRepository extends CrudRepository<Domain, Long> {

    @Override
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    List<Domain> findAll();

    @Override
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    Optional<Domain> findById(Long aLong);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    <S extends Domain> S save(S entity);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteById(Long aLong);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void delete(Domain entity);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAll(Iterable<? extends Domain> entities);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAll();
}
