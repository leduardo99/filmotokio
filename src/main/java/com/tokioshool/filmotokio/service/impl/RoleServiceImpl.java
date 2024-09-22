package com.tokioshool.filmotokio.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokioshool.filmotokio.domain.Role;
import com.tokioshool.filmotokio.repository.RoleRepository;
import com.tokioshool.filmotokio.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Set<Role> findAll() {
		return roleRepository.findAll();
	}
}
