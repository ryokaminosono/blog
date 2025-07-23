package com.example.demo;

import java.util.List;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.springframework.util.StringUtils;


@Controller
public class BlogController {
	
	/*ユーザーマスタのインスタンス化*/
	@Autowired
	private M_UserRepository userRepos;
	
	/*ポスト情報テーブルのインスタンス化*/
	@Autowired
	private Posts_InfoRepository postsInfoRepos;
	
	/*ライク情報テーブルのインスタンス化*/
	@Autowired
	private Like_InfoRepository likeInfoRepos;
	
	@Autowired
	private Comments_InfoRepository commentsInfoRepos; 
	
	private HttpSession session;
	
	
    public BlogController (HttpSession session) {
        // フィールドに代入する
        this.session = session;
    }
    
    
	/*ログインページの表示*/
	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("users/login");
		return mav;
	}
	
	
	/*メイン画面遷移処理*/
	@PostMapping("main1")
	public ModelAndView main1(@RequestParam("ID") String id, @RequestParam("PASS") String pass) {
		ModelAndView mav = new ModelAndView();
		this.session.setAttribute("id", id);
		String userId = (String)this.session.getAttribute("id");
		this.session.setAttribute("pass", pass);
		
		/*NULL・空文字チェック*/
		if(!StringUtils.hasText(id) || !StringUtils.hasText(pass)) {
			/*idもしくはpassが空白、NULLだった場合のメッセージ出力*/
			mav.addObject("errorMasssege", "IDまたはパスワードが入力されていません");
			mav.setViewName("users/login");
			return mav;
		}
		
		/*ログインユーザーチェック処理*/
		String user = null;
		String password = null;
		
		/*Optionalは値があるかもしれないし、ないかもしれないという状況を扱うためのクラス
		 *今回だとfindByIdでDBから取得したデータがあれば「sqlrecord」に値を入れてくれる。
		 *データがなければ空を返す。*/

		Optional<M_User> userData = userRepos.findById(id);
		
		/*値があるかどうかを確認するメソッド(Optional型の場合使えるメソッド)*/
		if(!userData.isPresent()) {
			mav.addObject("errorMasssege", "ユーザーが登録されていません。");
			mav.setViewName("users/login");
			return mav;
		} 
		
		M_User u = userData.get();
		user = u.getUser_id();
		password = u.getUser_password();	
		
		/*実際のチェック箇所*/
		if(user.equals(id) && password.equals(pass)) {
			mav.setViewName("users/main");
			mav.addObject("name", u.getUser_name());
			mav.addObject("userid", userId);
			
			/*メイン画面表示内容取得処理*/
			List<Posts_Info> postslist = postsInfoRepos.findAll();
			mav.addObject("blogs", postslist);
		} else {
		    // 認証失敗時の画面遷移を明示的に指定！
		    mav.addObject("errorMasssege", "IDまたはパスワードが違います。");
		    mav.setViewName("users/login");
		}
		    
		return mav;
		
	}
	
	
	/*メインページブログ選択後の遷移処理*/
	@GetMapping("/blog/{postsId}")
	public ModelAndView blog(@PathVariable int postsId) {
		return createBlogDetailView(postsId);
	}
	
	
	
	
	/*いいねカウント処理*/
		@Transactional
		@PostMapping("/likeup")
	public ModelAndView likeup(@RequestParam("posts_id") int postsId) {
		String userId = (String)session.getAttribute("id");
		List<Like_Info> likelist = likeInfoRepos.findBylikerecord(userId, postsId);
	
		/*同レコードの存在チェック*/
		if(likelist.isEmpty()){
			/*同じレコードがないのでLike_Infoにレコードを追加*/
			Like_Info likedata1 = new Like_Info();
			likedata1.setPosts_Id(postsId);
			likedata1.setReg_Dt(LocalDateTime.now());
			likedata1.setReg_User_Id(userId);
			likeInfoRepos.saveAndFlush(likedata1);		
		}else {
			/*同じレコードがあるのでLike_Infoのレコードを削除*/
			Like_Info likeRecord = likelist.get(0);
			int likeSeqNo = likeRecord.getSeqNo();
			likeInfoRepos.deleteById(likeSeqNo);
			
		}
		
		/*ブログ、いいね件数、コメント取得処理*/
		return createBlogDetailView(postsId);
		
	} 
	
		
	/*コメント登録処理*/
	@PostMapping("/commentAdd")
	public ModelAndView commentAdd(@RequestParam("comments")String comments, @RequestParam("posts_id")int postsId) {
		/*新規コメントの登録処理*/
		Comments_Info comment = new Comments_Info();
		String userId = (String)session.getAttribute("id");
		comment.setPosts_id(postsId);
		comment.setComments(comments);
		comment.setReg_user_id(userId);
		comment.setReg_dt(LocalDateTime.now());
		commentsInfoRepos.saveAndFlush(comment);
		
		/*ブログ、いいね件数、コメント取得処理*/
		return createBlogDetailView(postsId);
		
	}
		

	
	/*マイページブログ編集画面遷移処理*/
	@GetMapping("/blog1/{id}")
	public ModelAndView blog1(@PathVariable int id) {
		ModelAndView mav = new ModelAndView();
		Optional<Posts_Info> post = postsInfoRepos.findById(id);
		if(post.isPresent()) {
			Posts_Info blog = post.get();
			mav.addObject("blog", blog);
			mav.setViewName("users/edit");
		} else {
			mav.setViewName("users/mypage");
			mav.addObject("errorMasssege", "指定されたブログが見つかりません。");
		}
		
		return mav;
	}
	
	
	/*ブログ編集処理*/
	@PostMapping("/edit")
	@Transactional
	public ModelAndView edit(@ModelAttribute("blog") Posts_Info blog) {
		blog.setReg_dt(LocalDateTime.now());
		postsInfoRepos.save(blog);
		ModelAndView mav = new ModelAndView();
		String userId = (String)session.getAttribute("id");
		List<Posts_Info> blogs = postsInfoRepos.findByUserId(userId);
		mav.addObject("blogs", blogs);
		mav.setViewName("users/mypage");
		return mav;
	}
	

	
	/*ユーザーの新規登録画面遷移*/
	@GetMapping("/new1")
	public ModelAndView new1() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("users/new1");
		return mav;
	}
	
	
	/*ユーザーの新規登録処理*/
	@PostMapping("/create1") 
	@Transactional
	public ModelAndView create(@RequestParam("user_id") String id, @RequestParam("pass") String pass, @RequestParam("name") String name) {
		ModelAndView mav = new ModelAndView();
		
		/*現在日時の取得*/
		LocalDateTime ldt = LocalDateTime.now();
		/*引数で受け取ったユーザー情報と現在日時をM_USER型のuに入れる*/
		M_User u = new M_User(id, pass, name, ldt);
		userRepos.saveAndFlush(u);
		mav.setViewName("users/login");
		return mav;
	}
	
	/*マイページ遷移処理*/
	@GetMapping("/mypage")
	public ModelAndView mypage() {
		ModelAndView mav = new ModelAndView();
		String userid = (String)this.session.getAttribute("id");
		List<Posts_Info> posts = postsInfoRepos.findByUserId(userid);
		mav.addObject("blogs", posts);
		mav.setViewName("users/mypage");
		return mav;
	}
	
	
	/*ブログの新規登録画面遷移*/
	@GetMapping("new2")
	public ModelAndView new2() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("users/new2");
		return mav;
	}
	
	
	/*ブログの新規登録処理*/
	@PostMapping("create2")
	@Transactional
	public String create2(@RequestParam("title") String title, @RequestParam("contents") String contents) {
		Posts_Info post = new Posts_Info();
		post.setTitle(title);
		post.setContents(contents);
		post.setUser_id((String)this.session.getAttribute("id"));
		post.setReg_dt(LocalDateTime.now());
		postsInfoRepos.save(post);
		return "redirect:/mypage";
	}
	
	
/*リターン処理記述箇所開始*/
	
	/*ユーザー新規登録画面から戻る処理*/
	@GetMapping("return1")
	public ModelAndView return1() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("users/login");
		return mav;
	}
	
	
	/*マイページからメイン画面に戻る処理*/
	@GetMapping("return2")
	public ModelAndView return2() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("users/main");
		
		/*セッション情報からユーザーIDの取得*/
		String userId = (String)this.session.getAttribute("id");
		mav.addObject("userid", userId);
		
		/*画面表示のブログ取得処理*/
		List<Posts_Info> blogs = postsInfoRepos.findAll();
		mav.addObject("blogs", blogs);
		return mav;
	}
	
	
	/*ブログ新規登録画面からマイページに戻る処理*/
	@GetMapping("return3")
	public ModelAndView return3() {
		ModelAndView mav = new ModelAndView();
		String userId = (String)this.session.getAttribute("id");
		List<Posts_Info> blogs = postsInfoRepos.findByUserId(userId);
		mav.addObject("blogs", blogs);
		mav.setViewName("users/mypage");
		return mav;
	}	
	
	/*ブログページからメイン画面に戻る処理*/
	@GetMapping("return4")
	public ModelAndView return4() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("users/main");
		
		/*セッション情報からユーザーIDの取得*/
		String userId = (String)this.session.getAttribute("id");
		mav.addObject("userid", userId);
		
		/*画面表示のブログ取得処理*/
		List<Posts_Info> blogs = postsInfoRepos.findAll();
		mav.addObject("blogs", blogs);
		return mav;
	}
	
	
	/*ログアウト処理*/
	@GetMapping("logout")
	public ModelAndView logout() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("users/login");
		this.session.removeAttribute("id");
		return mav;
	}
	
	
	/*ブログ、いいね、コメント共通取得処理*/
	public ModelAndView createBlogDetailView (int postsId) {
		ModelAndView mav = new ModelAndView();
		
		/*ブログ取得処理*/
		Optional<Posts_Info> blogList = postsInfoRepos.findById(postsId);
		if(blogList.isPresent()) {
			Posts_Info blog = blogList.get();
			mav.addObject("blog", blog);
		} else {
			mav.setViewName("users/main");
			mav.addObject("errorMessage", "指定されたブログが見つかりませんでした。");
		}
		
		/*いいね取得処理*/
		Integer count = likeInfoRepos.countLikesByPostId(postsId);
			mav.addObject("likecount", count);
		
		/*コメント取得処理*/
		List<Comments_Info> comentsList = commentsInfoRepos.findByPostsId(postsId);
			mav.addObject("comments", comentsList);
			mav.setViewName("users/blog");
		
		return mav;
	}
	
	
}




