package nghhng.footballbooking.services;


import nghhng.footballbooking.dao.UserDAO;
import nghhng.footballbooking.dto.CreateUserRequest;
import nghhng.footballbooking.dto.GetUserByUsernameRequest;
import nghhng.footballbooking.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tunght.toby.common.entity.UserEntity;
import tunght.toby.common.exception.AppException;
import tunght.toby.common.exception.Error;
import tunght.toby.common.security.AuthUserDetails;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDAO> getAllUsers(){
        return userRepository.findAll();
    }

    public UserDAO createUser (CreateUserRequest createUserRequest) {
        UserDAO userDAO = UserDAO.builder()
                        .name(createUserRequest.getName())
                        .age(createUserRequest.getAge())
                        .gender(createUserRequest.getGender())
                        .phone(createUserRequest.getPhone())
                        .email(createUserRequest.getEmail())
                        .username(createUserRequest.getUsername())
                        .password(createUserRequest.getPassword())
                        .build();

        UserDAO userSaved = userRepository.save(userDAO);
        if(userSaved != null){
            return getUserByUsername(userSaved.getUsername());
        }
        else return null;

    }

    public UserDAO getUserByUsername(String username){
        UserDAO user = userRepository.findByUsername(username);
        return user;
    }

    public UserDAO currentUser(AuthUserDetails authUserDetails) {
        UserDAO userEntity = userRepository.findById(authUserDetails.getId()).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
        return userEntity;
    }


}
