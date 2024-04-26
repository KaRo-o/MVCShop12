package com.model2.mvc.web.user;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


//==> 회원관리 RestController
@CrossOrigin
@RestController
@RequestMapping("/user/*")
public class UserRestController {
	
	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음
	
	@Value("${pageUnit}")
	int pageUnit;
	@Value("${pageSize}")
	int pageSize;
		
	public UserRestController(){
		System.out.println(this.getClass());
	}
	
	@GetMapping("json/getUser/{userId}")
	public User getUser( @PathVariable String userId ) throws Exception{
		
		System.out.println("/user/json/getUser : GET");
		
		User user = userService.getUser(userId);
		System.out.println(user);
		//Business Logic
		return user;
	}

	@PostMapping("json/login")
	public User login(	@RequestBody User user,
									HttpSession session ) throws Exception{
	
		System.out.println("/user/json/login : POST");
		//Business Logic
		System.out.println("::"+user);
		User dbUser = new User();
		System.out.println(dbUser);
		dbUser=userService.getUser(user.getUserId());
		
		
		if(dbUser != null) {
			if( user.getPassword().equals(dbUser.getPassword())){
				dbUser.setActive(true);
			}
		}
		
		
		System.out.println(dbUser);
		
		return dbUser;
	}
	
	@PostMapping("json/addUser")
	public void addUser(@RequestBody User user) throws Exception {
		
		userService.addUser(user);
		
	}
	
	@PostMapping(value="json/listuser")
	public Map<String,Object> listUser(@RequestBody Search search	)throws Exception {
		
		if(search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		
		System.out.println("search"+search);
		search.setPageSize(pageSize);
		
		
		
		Map<String,Object> map = userService.getUserList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		map.put("resultPage", resultPage);
		
		System.out.println(map);
		
		return map;
	}
}























