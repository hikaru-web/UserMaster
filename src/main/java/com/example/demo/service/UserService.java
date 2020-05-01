package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;


//mapperを具体的に操作するクラス
@Service
public class UserService {
	@Autowired
    private UserMapper userMapper;

    @Transactional
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Transactional
    public Optional<User> findOne(Long userId) {
        return userMapper.findOne(userId);
    }

    @Transactional
    public User findOneByName(String userName) {
    	return userMapper.findOneByName(userName);
    }

    @Transactional
    public void save(User user) {
    	userMapper.save(user);
    }

    @Transactional
    public void update(User user) {
    	userMapper.update(user);
    }

    @Transactional
    public void delete(Long userId) {
    	userMapper.delete(userId);
    }
}