package com.example.PaymentGatewayDemo.serviceImpl;

import com.example.PaymentGatewayDemo.dto.ResponseDTO;
import com.example.PaymentGatewayDemo.dto.UserDTO;
import com.example.PaymentGatewayDemo.entity.User;
import com.example.PaymentGatewayDemo.enums.StatusCodeEnum;
import com.example.PaymentGatewayDemo.exception.UserAlreadyExistException;
import com.example.PaymentGatewayDemo.repository.UserRepository;
import com.example.PaymentGatewayDemo.service.UserService;
import com.example.PaymentGatewayDemo.util.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ResponseDTO register(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()){
            throw new UserAlreadyExistException(Constants.USER_ALREADY_EXIST);
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        return new ResponseDTO(StatusCodeEnum.CREATED.getStatusCode(), Constants.USER_ADDED_SUCCESSFULLY, null);
    }
}
