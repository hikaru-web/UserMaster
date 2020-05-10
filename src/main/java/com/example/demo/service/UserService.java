package com.example.demo.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.app.UserMaster.UserForm;
import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;


//mapperを具体的に操作するクラス
@Service
public class UserService {
	@Autowired
    private UserMapper userMapper;

    @Transactional
    public Page<User> findAll(UserForm userForm,Pageable pageable) {

    	long count = userMapper.selectUserCount();

    	List<User> userList = Collections.emptyList();

    	if (count >  0) {
    		//普通の全検索
    		userList = userMapper.findAll(userForm);
		}

    	//ここでPageImplが、良い感じにデータを成形してくれるはず→それはJPAを利用したときのみ？
    	//userList 取得したデータ　pageable ページに関する情報を持ったやつ　count データ数
    	Page<User> page = new PageImpl<User>(userList, pageable, count);

        return page;
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