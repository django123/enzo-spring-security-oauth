package com.enzo.jwt;

import com.enzo.entities.Permission;
import com.enzo.entities.UserEntity;
import com.enzo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {


    //get user from the database, via Hibernate
    @Autowired
    private UserRepository userRep;


    @Transactional
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        UserEntity user = userRep.getUserByEmail(username);

        if(user == null) throw new UsernameNotFoundException("Cette Utilisateur n'existe pas");
        if(user.getIsLock() || user.getIsDeleted()) throw new UsernameNotFoundException("Cette Utilisateur ne peut se connecter");
        List<GrantedAuthority> authorities = buildUserAuthority(user.getPermissions());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }


    private List<GrantedAuthority> buildUserAuthority(List<Permission> permissions) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // add user's authorities
        for (Permission permission : permissions) {
            if(!permission.getIsDeleted()) setAuths.add(new SimpleGrantedAuthority(permission.getCode()));
        }

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }
}

