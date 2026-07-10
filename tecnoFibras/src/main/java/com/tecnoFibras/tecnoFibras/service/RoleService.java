package com.tecnoFibras.tecnoFibras.service;

import com.tecnoFibras.tecnoFibras.domain.Role;
import com.tecnoFibras.tecnoFibras.repository.RoleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<role> getRoles() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<role> getRole(Integer id) {
        return roleRepository.findById(id);
    }

    @Transactional
    public void save(role role) {
        roleRepository.save(role);
    }

    @Transactional
    public void delete(Integer id) {
        roleRepository.deleteById(id);
    }
}
