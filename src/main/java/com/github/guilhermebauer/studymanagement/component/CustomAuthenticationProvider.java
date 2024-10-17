//package com.github.guilhermebauer.studymanagement.component;
//
//import com.github.guilhermebauer.studymanagement.model.RoleEntity;
//import com.github.guilhermebauer.studymanagement.model.UserEntity;
//import com.github.guilhermebauer.studymanagement.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.stream.Stream;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    private static final String USER_NOT_FOUND_MESSAGE = "An user with that email: %s was not found!";
//    private static final String INCORRECT_PASSWORD_MESSAGE = "The password you entered is incorrect or incomplete!";
//
//    private final UserRepository userRepository;
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public CustomAuthenticationProvider(UserRepository userRepository,PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//        final String username = authentication.getName();
//        final String password = authentication.getCredentials().toString();
//        UserEntity userEntity = userRepository.findUserByEmail(username)
//                .orElseThrow(() -> new BadCredentialsException(String.format(USER_NOT_FOUND_MESSAGE, username)));
//
//        if(!passwordEncoder.matches(password,userEntity.getPassword())){
//            throw new BadCredentialsException(INCORRECT_PASSWORD_MESSAGE);
//        }
//
//        Stream<RoleEntity> stream = userEntity.getRoles().stream();
//        return new UsernamePasswordAuthenticationToken(username,password,stream);
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
//    }
//}
