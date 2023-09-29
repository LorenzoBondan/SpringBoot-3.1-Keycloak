package com.metaway.keycloak.services;

import com.metaway.keycloak.services.exceptions.ForbiddenException;
import com.metaway.keycloak.util.CustomUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthService {

    @Autowired
    private CustomUserUtil customUserUtil;

    public String authenticatedUsername(){
        try{
            return customUserUtil.getLoggedUsername();
        }catch (Exception e){
            throw new UsernameNotFoundException("Invalid user");
        }
    }

    public void validateSelfOrAdmin(String username) {
        String me = authenticatedUsername();
        Collection<String> roles = getLoggedUserRoles();
        if (roles.contains("client_admin")) {
            return;
        }
        if(!me.equals(username)) {
            throw new ForbiddenException("Access denied. Should be self or admin");
        }
    }

    public Collection<String> getLoggedUserRoles(){
        return customUserUtil.getLoggedUserRoles();
    }
}
