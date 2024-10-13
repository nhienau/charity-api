package com.test.charity_api.security;

import com.test.charity_api.entity.Donor;
import com.test.charity_api.entity.Role;
import com.test.charity_api.repository.DonorRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private DonorRepository donorRepository;

    @Autowired
    public CustomUserDetailsService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Donor donor = donorRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new User(donor.getId(), donor.getPassword(), mapRolesToAuthorities(donor.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
