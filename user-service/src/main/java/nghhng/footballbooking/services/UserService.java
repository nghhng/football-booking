package nghhng.footballbooking.services;


import nghhng.footballbooking.dao.UserDAO;
import nghhng.footballbooking.dto.CreateUserRequest;
import nghhng.footballbooking.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nghhng.common.exception.AppException;
import nghhng.common.exception.ErrorCommon;

import java.util.List;
import java.util.Optional;


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

    public UserDAO getById(String id){
        Optional<UserDAO> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        } else{
            throw new AppException(ErrorCommon.USER_NOT_FOUND);
        }
    }




}
