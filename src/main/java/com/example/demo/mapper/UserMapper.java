package com.example.demo.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.app.UserMaster.UserForm;
import com.example.demo.domain.User;

@Mapper
public interface UserMapper {
    List<User> findAll(@Param("userForm") UserForm userForm);

    Optional<User> findOne(Long userId);

    User findOneByName(String name);

    void save(User user);

    void update(User user);

    void delete(Long userId);

    public long selectUserCount();

}
