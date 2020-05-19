package ru.nc.portal.repository;

import org.springframework.stereotype.Repository;
import ru.nc.portal.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @Override
    void delete(Role role);
}
