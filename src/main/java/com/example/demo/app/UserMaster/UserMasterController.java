package com.example.demo.app.UserMaster;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class UserMasterController{

	@GetMapping
	public String index(Model model) {
		model.addAttribute("title", "インデックス");
		return "/index";
	}

	@GetMapping("/UserTouroku")
	public String UserTouroku(UserForm userForm,
			Model model) {
		model.addAttribute("title", "プロフィール登録");
		return "UserMaster/UserTouroku";
	}

	@PostMapping("/UserTourokuKakunin")
	public String UserTourokuConfirm(@Validated UserForm userForm,
					BindingResult results,
					Model model) {

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
			model.addAttribute("title", "InquiryForm");
			return "inquiry/form_boot";
		}

//		User user = new User();
//
//		user.setName(userForm.getName());
//		user.setAge(userForm.getAge());
//		user.setContents(userForm.getContents());
//		user.setSex(userForm.getSex());
//		user.setBirthDay(userForm.getBirthDay());

		//redirect
		/*addFlashAttribute()はリクエストを隔ててデータを保管する仕組みであるセッション
		という機能を内部的に使用している*/
		//model.addAttributeを使ってもデータを渡せないので注意！
		//このregistered!が一回表示されると、セッションのデータが破棄される（＝フラッシュスコープ）
		//このフラッシュスコープを簡単に実現するための仕組みがaddFlashAttribute
		redirectAttributes.addFlashAttribute("complete","Registered!");

		return "redirect:/inquiry/form";
	}

}
