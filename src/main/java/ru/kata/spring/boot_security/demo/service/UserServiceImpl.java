package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                rolesToAuthorities(user.getRoles()));
    }

    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<User> findAll() {
       return userRepository.findAll();
    }

    public void save(User user, Set<Role> roles) {
        user.setRoles(roles);
        user.setPassword(user.getPassword());
        userRepository.save(user);
    }

    // реализовать
    public void update(User user, Set<Role> roles) {
        user.setEmail(user.getEmail());
        user.setRoles(roles);
        user.setUsername(user.getUsername());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setPassword(user.getPassword());
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }



    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    private Collection<? extends GrantedAuthority> rolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(i -> new SimpleGrantedAuthority(i.getName())).collect(Collectors.toList());
    }
}
