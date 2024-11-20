package app.staic;

import app.models.User;
import app.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;


public class Static {
    public static User getUser(UserRepository userRepository, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userRepository.findByUsername(userDetails.getUsername()).isPresent()){
            return userRepository.findByUsername(userDetails.getUsername()).get();
        }
        return null;
    }

}
