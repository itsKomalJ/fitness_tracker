package com.example.fitness_tracker.data.pojo;

import com.example.fitness_tracker.data.enums.Roles;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserDetails extends User {

    private long id;
    private Roles roles;

    private final String ROLE_PREFIX = "ROLE_";

    public UserDetails(String userName, String password, long id, Roles roles, Collection<? extends GrantedAuthority> authorities){
        super(userName,password,authorities);
        this.id=id;
        this.roles=roles;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();

        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + roles));

        return list;
    }

}
