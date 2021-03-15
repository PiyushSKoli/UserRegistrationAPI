package com.registration.RegistrationApp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.registration.RegistrationApp.Dto.ResultModel;
import com.registration.RegistrationApp.Entity.Users;
import com.registration.RegistrationApp.Service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class RegistrationAppApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Test
	public void createUserTest() throws Exception {
		Users mockUser = new Users();
		mockUser.setName("Piyush");
		mockUser.setSurname("Koli");
		mockUser.setEmail("piyush@gmail.com");
		mockUser.setDob(Date.valueOf("2021-02-11"));
		mockUser.setCity("Pune");
		mockUser.setContactNumber("9088888888");
		mockUser.setDesignation("Java");
		mockUser.setPinCode(12344555);
		mockUser.setJoiningDate(Date.valueOf("2021-02-11"));

		List<String> stringList = new ArrayList<String>();
		stringList.add("Success");

		ResultModel resultModel = new ResultModel();
		resultModel.setMessage("Success");
		resultModel.setData(stringList);

		String inputInJson = this.mapToJson(resultModel);

		String Url = "/users/saveUpdateUser";

		Mockito.when(userService.saveUpdateUser(Mockito.any(Users.class))).thenReturn(stringList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(Url).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(inputInJson);

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	public void UpdateUserTest() throws Exception {
		Users mockUser = new Users();
		mockUser.setUserId("Neosoft_8");
		mockUser.setName("Piyush");
		mockUser.setSurname("Koli");
		mockUser.setEmail("piyush@gmail.com");
		mockUser.setDob(Date.valueOf("2021-02-11"));
		mockUser.setCity("Pune");
		mockUser.setContactNumber("9088888888");
		mockUser.setDesignation("Java");
		mockUser.setPinCode(12344555);
		mockUser.setJoiningDate(Date.valueOf("2021-02-11"));

		List<String> stringList = new ArrayList<String>();
		stringList.add("Success");

		ResultModel resultModel = new ResultModel();
		resultModel.setMessage("Success");
		resultModel.setData(stringList);

		String inputInJson = this.mapToJson(resultModel);

		String Url = "/users/saveUpdateUser";

		Mockito.when(userService.saveUpdateUser(Mockito.any(Users.class))).thenReturn(stringList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(Url).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(inputInJson);

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	public void getAllUserTest() throws Exception {
		Users mockUser1 = new Users();

		mockUser1.setId(8);
		mockUser1.setName("Piyush");
		mockUser1.setSurname("Koli");
		mockUser1.setEmail("piyush@gmail.com");
		mockUser1.setCity("Pune");
		mockUser1.setContactNumber("9876543210");
		mockUser1.setDob(null);
		mockUser1.setDesignation("Java");
		mockUser1.setPinCode(12344555);
		mockUser1.setUserId("Neosoft_8");
		mockUser1.setJoiningDate(null);

		Users mockUser2 = new Users();
		mockUser2.setId(9);
		mockUser2.setName("Punam");
		mockUser2.setSurname("patil");
		mockUser2.setEmail("punam@gmail.com");
		mockUser2.setCity("Pune");
		mockUser2.setContactNumber("1234567890");
		mockUser2.setDob(null);
		mockUser2.setDesignation("Java");
		mockUser2.setPinCode(12344555);
		mockUser2.setUserId("Neosoft_9");
		mockUser2.setJoiningDate(null);

		ResultModel resultModel = new ResultModel();
		List<Users> usersList = new ArrayList<Users>();
		resultModel.setData(usersList);
		resultModel.setMessage("Success");
		usersList.add(mockUser1);
		usersList.add(mockUser2);

		String Url = "/users/getAllUsers";

		Mockito.when(userService.getAllUsers()).thenReturn(usersList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(Url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(resultModel);

		MockHttpServletResponse response = result.getResponse();

		String outputInJson = result.getResponse().getContentAsString();

		assertThat(outputInJson).isEqualTo(expectedJson);

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void getUserByUserId() throws Exception {
		Users mockUser1 = new Users();

		mockUser1.setId(8);
		mockUser1.setName("Punam");
		mockUser1.setSurname("patil");
		mockUser1.setEmail("Punam@gmail.com");
		mockUser1.setCity("Pune");
		mockUser1.setContactNumber("9876543210");
		mockUser1.setDob(null);
		mockUser1.setDesignation("Java");
		mockUser1.setPinCode(12344555);
		mockUser1.setUserId("Neosoft_8");
		mockUser1.setJoiningDate(null);

		ResultModel resultModel = new ResultModel();
		resultModel.setData(mockUser1);
		resultModel.setMessage("Success");

		String Url = "/users/getByUserId/NeoSoft_8";

		Mockito.when(userService.getByUserId(Mockito.anyString())).thenReturn(mockUser1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(Url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(resultModel);

		MockHttpServletResponse response = result.getResponse();

		String outputInJson = result.getResponse().getContentAsString();

		assertThat(outputInJson).isEqualTo(expectedJson);

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void getUserByNameSurnamePincode() throws Exception {
		Users mockUser1 = new Users();

		mockUser1.setId(2);
		mockUser1.setName("Prem");
		mockUser1.setSurname("Barhate");
		mockUser1.setEmail("Prem@gmail.com");
		mockUser1.setCity("Pune");
		mockUser1.setContactNumber("9876543210");
		mockUser1.setDob(null);
		mockUser1.setDesignation("Java");
		mockUser1.setPinCode(423005);
		mockUser1.setUserId("Neosoft_2");
		mockUser1.setJoiningDate(null);

		List<Users> usersList = new ArrayList<Users>();
		usersList.add(mockUser1);
		ResultModel resultModel = new ResultModel();
		resultModel.setData(usersList);
		resultModel.setMessage("Success");

		String Url = "/users/getByNameAndSurenameAndPincode?name=Prem&surname=Barhate&pinCode=423005";

		Mockito.when(
				userService.getByNameAndSurenameAndPincode(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(usersList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(Url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(resultModel);

		MockHttpServletResponse response = result.getResponse();

		String outputInJson = result.getResponse().getContentAsString();

		assertThat(outputInJson).isEqualTo(expectedJson);

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void getByDobANdJoiningDate() throws Exception {
		Users mockUser1 = new Users();

		mockUser1.setId(5);
		mockUser1.setName("Piyush");
		mockUser1.setSurname("Koli");
		mockUser1.setEmail("piyush@gmail.com");
		mockUser1.setCity("Pune");
		mockUser1.setContactNumber("9876543210");
		mockUser1.setDob(Date.valueOf("1993-08-30"));
		mockUser1.setDesignation("Java");
		mockUser1.setPinCode(12344555);
		mockUser1.setUserId("Neosoft_5");
		mockUser1.setJoiningDate(Date.valueOf("2021-01-01"));

		Users mockUser2 = new Users();
		mockUser2.setId(4);
		mockUser2.setName("Punam");
		mockUser2.setSurname("Patil");
		mockUser2.setEmail("punam@gmail.com");
		mockUser2.setCity("Pune");
		mockUser2.setContactNumber("1234567890");
		mockUser2.setDob(Date.valueOf("1993-08-30"));
		mockUser2.setDesignation("Java");
		mockUser2.setPinCode(12344555);
		mockUser2.setUserId("Neosoft_4");
		mockUser2.setJoiningDate(Date.valueOf("2021-02-11"));

		ResultModel resultModel = new ResultModel();
		List<Users> usersList = new ArrayList<Users>();
		resultModel.setData(usersList);
		resultModel.setMessage("Success");
		usersList.add(mockUser1);
		usersList.add(mockUser2);

		String Url = "/users/getByDobAndJoiningDate";

		Mockito.when(userService.getByDobAndJoiningDate()).thenReturn(usersList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(Url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(resultModel);

		MockHttpServletResponse response = result.getResponse();

		String outputInJson = result.getResponse().getContentAsString();

		assertThat(outputInJson).isEqualTo(expectedJson);

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void deleteByUserId() throws Exception {

		ResultModel resultModel = new ResultModel();
		resultModel.setMessage("Success");

		String Url = "/users/deleteUser?userId=NeoSoft_7";

		Mockito.when(userService.deleteUser(Mockito.anyString())).thenReturn("Success");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(Url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(resultModel);

		MockHttpServletResponse response = result.getResponse();

		String outputInJson = result.getResponse().getContentAsString();

		assertThat(outputInJson).isEqualTo(expectedJson);

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void deleteUserById() throws Exception {

		ResultModel resultModel = new ResultModel();
		resultModel.setMessage("Success");

		String Url = "/users/deleteUserById/1";

		Mockito.when(userService.deleteUserById(Mockito.anyInt())).thenReturn("Success");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(Url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(resultModel);

		MockHttpServletResponse response = result.getResponse();

		String outputInJson = result.getResponse().getContentAsString();

		assertThat(outputInJson).isEqualTo(expectedJson);

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		objectMapper.setDateFormat(df);
		return objectMapper.writeValueAsString(object);
	}

	/*
	 * @Test void contextLoads() { }
	 */
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
}
