package com.example.demo.app.UserMaster;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/")
public class UserMasterController{

	private UserService userService;

	@Autowired
	public UserMasterController(UserService userService) {
		this.userService = userService;
	}

	//ユーザー一覧
	@GetMapping
	public String index(Model model,UserForm userForm) {
		List<User> list = userService.findAll();
		model.addAttribute("list", list);
		model.addAttribute("title", "ユーザー一覧");
		return "/index";
	}

	@GetMapping("/UserTouroku")
	public String UserTouroku(UserForm userForm,
			Model model,
			@ModelAttribute("complete")String complete) {
		model.addAttribute("title", "プロフィール登録");
	    model.addAttribute("radioItems",getRadioItems());
		return "UserMaster/UserTouroku";
	}

	@PostMapping("/UserTouroku")
	public String UserTourokuBack(UserForm userForm,
			Model model) {
		model.addAttribute("title", "プロフィール登録");
		return "UserMaster/UserTouroku";
	}

	@PostMapping("/UserTourokuKakunin")
	public String UserTourokuConfirm(@Validated UserForm userForm,
					BindingResult results,
					Model model) {

		//同じ名前のユーザーが登録されていたらエラー
		if (userService.findOneByName(userForm.getUserName()) !=null) {
			model.addAttribute("title","同名のユーザーが存在します");
			return"userMaster/UserTouroku";
		}

		if (results.hasErrors()) {
			model.addAttribute("title","エラーだ！");
			return"userMaster/UserTouroku";
		}

		model.addAttribute("title", "Confirm Page");
		return "UserMaster/UserTourokuKakunin";

	}

	@PostMapping("/complete")
	public String complete(@Validated UserForm userForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		// redirectAttributeはフラッシュスコープを実現するために必要なクラス

		//バリデーションは再び必要！（type hiddenにしていても書き換えは可能だから）
		if (result.hasErrors()) {
			model.addAttribute("title", "確認画面でエラー");
			return "UserMaster/UserTouroku";
		}

		User user = new User();

		user.setUserName(userForm.getUserName());
		user.setUserAge(Long.parseLong(userForm.getUserAge()));
		user.setContents(userForm.getContents());
		user.setUserSex(userForm.getUserSex());
		user.setUserBirthDay(userForm.getUserBirthDay());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		user.setCreateTime(timestamp);

		userService.save(user);

		//redirect
		/*addFlashAttribute()はリクエストを隔ててデータを保管する仕組みであるセッション
		という機能を内部的に使用している*/
		//model.addAttributeを使ってもデータを渡せないので注意！
		//このregistered!が一回表示されると、セッションのデータが破棄される（＝フラッシュスコープ）
		//このフラッシュスコープを簡単に実現するための仕組みがaddFlashAttribute
		redirectAttributes.addFlashAttribute("complete","Registered!");

		return "redirect:/UserMaster/UserTouroku";
	}

    /**
     * UserIdを取得し、一件のデータ更新
     * @param taskForm
     * @param result
     * @param id
     * @param model
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/{UserId}")
    public String showUpdate(
    	@ModelAttribute UserForm userForm,
    	//Postでhiddenで渡されてきたものはRequestParamで受け取る
    	@PathVariable Long UserId,
    	BindingResult result,
    	Model model) {

    	if (result.hasErrors()) {
    		return "/index";
		}

    	Optional<User> userOpt = userService.findOne(UserId);
    	Optional<UserForm> userFormOpt = userOpt.map(t ->makeUserForm(t));

    	if (userFormOpt.isPresent()) {
			userForm = userFormOpt.get();
		} else {

			return "/index";
		}

    	model.addAttribute("userForm", userForm);

    	return "UserMaster/UserTourokuUpdate";

    }

    /**
     * UserIdを取得し、一件のデータ更新
     * @param userForm
     * @param result
     * @param id
     * @param model
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/UpdateConfirm")
    public String updateConfirm(
    	@Valid @ModelAttribute UserForm userForm,
    	BindingResult result,
    	//Postでhiddenで渡されてきたものはRequestParamで受け取る
    	@RequestParam("UserId") int UserId,
    	Model model,
    	RedirectAttributes redirectAttributes) {

    	//TaskFormのデータをTaskに格納
//    	User user = makeUser(userForm, UserId);

        if (!result.hasErrors()) {

        	//更新処理、フラッシュスコープの使用、リダイレクト（個々の編集ページ）

//        	taskService.update(task);

        	redirectAttributes.addFlashAttribute("complete","変更が完了しました");
//        	return "redirect:/task/" + userId;
        	return "redirect:/task/";

        } else {
//            model.addAttribute("taskForm", taskForm);
            model.addAttribute("title", "タスク一覧");
            return "task/index";
        }


    }

	private Map<String,String> getRadioItems(){
	     Map<String, String> selectMap = new LinkedHashMap<String, String>();
	     selectMap.put("key_A", "男性");
	     selectMap.put("key_B", "女性");
	     return selectMap;
	}

    /**
     * UserのデータをTaskに入れて返す
     * @param taskForm
     * @param taskId 新規登録の場合は0を指定
     * @return
     */
    private User makeUser(UserForm userForm, Long UserId) {
        User user = new User();
        if(UserId != 0) {
        	user.setUserId(UserId);
        }
//        user.setUserId(1L);
//        user.setEditTime(editTime);
//        user.set
//        user.setDetail(taskForm.getDetail());
//        user.setDeadline(taskForm.getDeadline());
        return user;
    }

	private UserForm makeUserForm(User user) {

		UserForm userForm = new UserForm();

		userForm.setUserName(user.getUserName());
		userForm.setUserAge(user.getUserAge().toString());
		userForm.setUserBirthDay(user.getUserBirthDay());
		userForm.setContents(user.getContents());

		return userForm;

	}

}
