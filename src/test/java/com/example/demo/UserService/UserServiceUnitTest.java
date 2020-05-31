package com.example.demo.UserService;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import com.example.demo.app.UserMaster.UserForm;
import com.example.demo.domain.User;

//↓JUnit5にMockitoという拡張機能を統合する。
@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceの単体テスト")
class UserServiceUnitTest {

//	@Mock
//	private UserMapper userMapper;

//	@Mock
//	private Pageable pageable;

//	@InjectMocks
//	private UserService userService;



	@Test
	@DisplayName("テーブルuserの全件取得で0件の場合のテスト")
	void testFindAllReturnEmptyList() {
		Page<User> user = null;
		UserForm userForm = null;

//		when(userService.findAll(userForm,pageable)).thenReturn(user);

//		Page<User> actrualList = userService.findAll(userForm,pageable);
//
//		verify(userMapper,times(1)).findAll(userForm);
//
//		assertEquals(0, actrualList.getSize());
	}

	@Test
	@DisplayName("テーブルUserの全件取得で1件の場合のテスト")
	void testFindAllReturnOneList() {

		//モックから返すリストに一つのUserオブジェクトをセット
		List<User> list = new ArrayList<>();
		UserForm userForm = null;
		User user1 = new User();
		list.add(user1);

		//「userMapperがfindAll(全件取得)したら、User型のListが返ってくるはずだ」
//		when(userMapper.findAll(userForm)).thenReturn(list);

		// 実際に全件取得してactualListにつめる
//		Page<User> actualList = userService.findAll(userForm,pageable);

		//userMapperのfindAllは1回だけ実行されるはずだ
//		verify(userMapper,times(1)).findAll(userForm);

		//取得される件数は一件のはずだ
//		assertEquals(1, actualList.getSize());
	}


}
