package com.example.demo.app.userMaster;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userMaster")
public class UserMasterController {

	@GetMapping
	public String index(Model model) {
		model.addAttribute("title", "インデックス");
		return "/index";
	}

	@GetMapping("/UserTouroku")
	public String UserTouroku(UserForm userForm,Model model) {
		model.addAttribute("title", "Inquiry Form");
		return "userMaster/UserTouroku";
	}

	@PostMapping("/UserTourokuKakunin")
	public String UserTourokuConfirm(@Validated UserForm userForm
					,BindingResult results
					,Model model) {

		if (results.hasErrors()) {
			model.addAttribute("title","エラーだ！");
			return"userMaster/UserTouroku";
		}

		model.addAttribute("title", "Confirm Page");
		return "userMaster/UserTourokuKakunin";

	}


}
