package com.example.demo.app.UserMaster;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.example.demo.helper.PagenationHelper;
import com.example.demo.service.UserService;
import com.example.demo.util.CalendarUtil;
import com.example.demo.util.MapUtil;

@Controller
@RequestMapping("/")
public class UserMasterController{

	private UserService userService;

	@Autowired
	public UserMasterController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 初期画面。ユーザー一覧と登録ボタンを表示する。
	 * @param model
	 * @param userForm
	 * @return
	 */
	@GetMapping
	public String index(Model model,
			UserForm userForm,
			Pageable pageable) {

		userForm.setSize(pageable.getPageSize());
		userForm.setPage(pageable.getPageNumber());

		Page<User> page = userService.findAll(userForm,pageable);
		PagenationHelper ph = new PagenationHelper(page.getNumber(), page.getSize(), page.getTotalPages());

		model.addAttribute("page", page);
		model.addAttribute("ph", ph);
		model.addAttribute("title", "ユーザー一覧");
		return "/index";
	}

	/**
	 *初期画面から登録画面へ遷移
	 * @param userForm
	 * @param model
	 * @param complete
	 * @return
	 */
	@GetMapping("/UserTouroku")
	public String UserTouroku(UserForm userForm,
			Model model,
			@ModelAttribute("complete")String complete) {
		model.addAttribute("title", "プロフィール登録");

		model.addAttribute("selectItemsYear",MapUtil.SELECT_ITEMS_YEAR);
		model.addAttribute("selectItemsMonth", MapUtil.SELECT_ITEMS_MONTH);
		model.addAttribute("selectItemsDay", MapUtil.SELECT_ITEMS_DAY);
	    model.addAttribute("radioItems",getRadioItems());
		return "UserMaster/UserTouroku";
	}

	/**【登録画面】
	 * 戻るボタンを押したときに登録画面へ遷移する。
	 * @param userForm
	 * @param model
	 * @return
	 */
	@PostMapping("/UserTouroku")
	public String UserTourokuBack(UserForm userForm,
			Model model) {
		model.addAttribute("title", "プロフィール登録");
		model.addAttribute("selectItemsYear",MapUtil.SELECT_ITEMS_YEAR);
		model.addAttribute("selectItemsMonth", MapUtil.SELECT_ITEMS_MONTH);
		model.addAttribute("selectItemsDay", MapUtil.SELECT_ITEMS_DAY);
		return "UserMaster/UserTouroku";
	}

	/**【登録確認画面】
	 * 入力情報をValidateし、確認画面へ遷移させる。
	 * @param userForm
	 * @param results
	 * @param model
	 * @return
	 */
	@PostMapping("/UserTourokuKakunin")
	public String UserTourokuConfirm(@Validated UserForm userForm,
					BindingResult results,
					Model model) {

		if (results.hasErrors()) {
			model.addAttribute("title","Error");
			model.addAttribute("selectItemsYear",MapUtil.SELECT_ITEMS_YEAR);
			model.addAttribute("selectItemsMonth", MapUtil.SELECT_ITEMS_MONTH);
			model.addAttribute("selectItemsDay", MapUtil.SELECT_ITEMS_DAY);
			return"userMaster/UserTouroku";
		}

		model.addAttribute("title", "Confirm Page");
		return "UserMaster/UserTourokuKakunin";

	}

	/**
	 * 入力情報を登録する
	 * @param userForm
	 * @param result
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
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

		//誕生日を結合する
		int birthDayYear = Integer.parseInt(userForm.getBirthDayYear());
		int birthDayMonth = Integer.parseInt(userForm.getBirthDayMonth());
		int birthDay = Integer.parseInt(userForm.getBirthDay());
		LocalDate userBirthDay = LocalDate.of(birthDayYear, birthDayMonth, birthDay);

		//エンティティにフォームの値を詰め込む
		user.setUserName(userForm.getUserName());
		user.setUserAge(Long.parseLong(userForm.getUserAge()));
		user.setContents(userForm.getContents());
		user.setUserSex(userForm.getUserSex());
		user.setUserBirthDay(userBirthDay);
		user.setContents(userForm.getContents());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		user.setCreateTime(timestamp);

		userService.save(user);

		//redirect
		/*addFlashAttribute()はセッション(=リクエストを隔ててデータを保管する仕組み)
		という機能を内部的に使用している*/
		//model.addAttributeを使ってもデータを渡せないので注意！
		//このregistered!が一回表示されると、セッションのデータが破棄される（＝フラッシュスコープ）
		//このフラッシュスコープを簡単に実現するための仕組みがaddFlashAttribute
		redirectAttributes.addFlashAttribute("complete","Registered!");

		return "redirect:/";
	}

	/**
	 * 明細画面を表示する。
	 * @param userForm
	 * @param UserId
	 * @param result
	 * @param model
	 * @return
	 */
	@GetMapping("/{UserId}")
	public String showDetails(
			@ModelAttribute UserForm userForm,
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

    	//誕生日をセットします
    	userForm.setUserBirthDayForNenGappi(CalendarUtil.LocalDateTimeToNenGappi(userForm.getUserBirthDay()));

    	model.addAttribute("userForm", userForm);

    	return "UserMaster/UserSyousai";


	}

	/**
	 * 明細画面を表示する。
	 * @param userForm
	 * @param UserId
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/{UserId}")
	public String BackDetails(
			@ModelAttribute UserForm userForm,
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

    	return "UserMaster/UserSyousai";
	}

    /**
     * UserIdに紐づく登録情報を取得し、編集可能な状態で表示する。
     * @param userForm
     * @param
     * @param Userid
     * @param model
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/update/{UserId}")
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
    	@RequestParam("UserId") int userId,
    	Model model,
    	RedirectAttributes redirectAttributes) {

    	//UserFormのデータをUserに格納
    	User user = makeUser(userForm, userId);

        if (!result.hasErrors()) {

        	//更新処理、フラッシュスコープの使用、リダイレクト（個々の編集ページ）

        	userService.update(user);

        	redirectAttributes.addFlashAttribute("complete","変更が完了しました");
        	return "redirect:/";

        } else {
            model.addAttribute("title", "エラーが発生しました。");
            return "/index";
        }

    }


    /**
     * ユーザーidを取得し、一件のデータ削除
     * @param id
     * @param model
     * @return
     */
    @PostMapping("/delete")
    public String delete(
    	//リクエストパラメータはStringとして取得するが、
    	//@RequestParam{""}int idとしておくことで、
    	//自動的にintに変換される
    	@RequestParam("UserId") Long userId,
    	Model model) {

    	userService.delete(userId);

        return "redirect:/";
    }

	private Map<String,String> getRadioItems(){
	     Map<String, String> selectMap = new LinkedHashMap<String, String>();
	     selectMap.put("key_A", "男性");
	     selectMap.put("key_B", "女性");
	     return selectMap;
	}

    /**
     * UserFormのデータをUserに入れて返す
     * @param userForm
     * @param userId 新規登録の場合は0を指定
     * @return
     */
    private User makeUser(UserForm userForm, int userId) {
        User user = new User();
        user.setUserId(userId);
        user.setUserAge(Long.parseLong(userForm.getUserAge()));
        user.setUserName(userForm.getUserName());
        user.setUserBirthDay(userForm.getUserBirthDay());
        user.setContents(userForm.getContents());
        return user;
    }

	/**
	 * UserのデータをUserFormに入れて返す
	 * @param user
	 * @return userForm
	 */
	private UserForm makeUserForm(User user) {

		UserForm userForm = new UserForm();

		userForm.setUserId(user.getUserId());
		userForm.setUserName(user.getUserName());
		userForm.setUserAge(user.getUserAge().toString());
		userForm.setUserBirthDay(user.getUserBirthDay());
		userForm.setContents(user.getContents());

		return userForm;

	}

}
