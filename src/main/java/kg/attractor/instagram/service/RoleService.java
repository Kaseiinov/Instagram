package kg.attractor.instagram.service;


import kg.attractor.instagram.exceptions.RoleNotFoundException;
import kg.attractor.instagram.model.Role;
import kg.attractor.instagram.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findRoleByName(String name){
        return roleRepository.findRoleByRole(name).orElseThrow(RoleNotFoundException::new);
    }


    public Role findRoleById(Long id){
        return roleRepository.findById(id).orElseThrow(RoleNotFoundException::new);
    }

}
