package ru.nc.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nc.portal.model.PageType;

@Repository
public interface PageTypeRepository extends JpaRepository<PageType, Long> {
    PageType findByTypeName(String typeName);
}
