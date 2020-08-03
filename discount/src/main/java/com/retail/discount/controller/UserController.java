package com.retail.discount.controller;

import com.retail.discount.dtos.UserDTO;
import com.retail.discount.entity.User;
import com.retail.discount.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @GetMapping("getUser/{id}")
    public UserDTO getUser(@PathVariable("id") String id){
        return service.getUser(id);
    }

    @GetMapping("getAllUsers")
    public List<UserDTO> getAll(){
        return service.getAllUser();
    }

    @PostMapping("addUser/{userTypeId}")
    public UserDTO addUser(@RequestBody UserDTO userDTO,
                           @PathVariable("userTypeId") String userTypeId)throws Exception{
        return service.createUser(userDTO, userTypeId);
    }
}
