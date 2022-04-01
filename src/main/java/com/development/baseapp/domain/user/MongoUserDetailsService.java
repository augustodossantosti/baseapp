package com.development.baseapp.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MongoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(mapAuthorities(user.getAuthorities()))
                .credentialsExpired(isCredentialsExpired(user.getAuthorities()))
                .accountExpired(user.isExpired())
                .accountLocked(user.islocked())
                .disabled(!user.getEnabled())
                .build();
    }

    private Collection<? extends GrantedAuthority> mapAuthorities(final List<Authority> authorities) {
        return authorities.stream().map(authority ->
                new SimpleGrantedAuthority(authority.getRole().name())).collect(Collectors.toList());
    }

    private Boolean isCredentialsExpired(final List<Authority> authorities) {
        for (Authority authority : authorities) {
            if (authority.getRevokeDate() != null) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
