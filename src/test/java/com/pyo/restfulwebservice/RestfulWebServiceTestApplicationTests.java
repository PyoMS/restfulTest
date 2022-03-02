package com.pyo.restfulwebservice;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pyo.restfulwebservice.user.User;
import com.pyo.restfulwebservice.user.UserController;
import com.pyo.restfulwebservice.user.UserDaoService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RestfulWebServiceTestApplicationTests {
	
	@InjectMocks
	private UserDaoService userDaoService;
	
	@Mock
	private UserController userController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	@Test
	public void selectTest() throws Exception {
		// given
		doReturn(userList()).when(userDaoService).findAll();
		
		// when
		final ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.get("/users")
				);
		// then
		final MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	private List<User> userList(){
		final List<User> userList = new ArrayList<>();    
		for (int i = 0; i < 5; i++) {        
//			userList.add(new User("test@test.test", "test"));   
			userList.add(new User(i,"kenneth"+i, new Date(), "pass"+i, "701010-111111"));
		}    
		return userList;
	}
}
