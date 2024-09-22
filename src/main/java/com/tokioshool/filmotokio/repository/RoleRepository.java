package com.tokioshool.filmotokio.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioshool.filmotokio.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByName(String name);
	Set<Role> findAll();
}
