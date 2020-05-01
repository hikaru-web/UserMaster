package com.example.demo.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;

//↓JUnit5にMockitoという拡張機能を統合する。
@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceの単体テスト")
class UserServiceUnitTest {

	@Mock
	private UserMapper userMapper;

	@InjectMocks
	private UserService userService;

	@Test
	@DisplayName("テーブルuserの全件取得で0件の場合のテスト")
	void testFindAllReturnEmptyList() {
		List<User> list = new ArrayList<>();
		when(userService.findAll()).thenReturn(list);

		List<User> actrualList = userService.findAll();

		verify(userMapper,times(1)).findAll();

		assertEquals(0, actrualList.size());
	}

	@Test
	@DisplayName("テーブルUserの全件取得で1件の場合のテスト")
	void testFindAllReturnOneList() {

		//モックから返すリストに一つのUserオブジェクトをセット
		List<User> list = new ArrayList<>();
		User user1 = new User();
		list.add(user1);

		//「userMapperがfindAll(全件取得)したら、User型のListが返ってくるはずだ」
		when(userMapper.findAll()).thenReturn(list);

		// 実際に全件取得してactualListにつめる
		List<User> actualList = userService.findAll();

		//userMapperのfindAllは1回だけ実行されるはずだ
		verify(userMapper,times(1)).findAll();

		//取得される件数は一件のはずだ
		assertEquals(1, actualList.size());
	}


}
