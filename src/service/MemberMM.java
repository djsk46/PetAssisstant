package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import DAO.MemberDao;
import bean.Forward;
import bean.Member;
import bean.PetApply;

public class MemberMM {
	HttpServletRequest request;
	HttpServletResponse response;
	
	
	public MemberMM(HttpServletRequest request, HttpServletResponse response) {
		this.request=request;
		this.response=response;
	}


	public Forward access() {
		Forward fw=new Forward();
		String id=request.getParameter("id");
		System.out.println("id="+id);
		String pw=request.getParameter("pw");
		System.out.println("pw="+pw);
		MemberDao mDao=new MemberDao();
		int result=mDao.access(id,pw);	//1:성공, -1:id부재,  0:pw부재
		mDao.close();
		if(result==-1) {
			System.out.println("id가 존재하지 않아");
			request.setAttribute("msgAccess", "id존재하지 않아요!");
		}
		else if(result==0){
			System.out.println("pw가 틀렸어");
			request.setAttribute("msgAccess", "pw가 틀립니다.");
		}
		else {//로그인 성공시 
			HttpSession session=request.getSession();
			session.setAttribute("id", id);
			session.setAttribute("admin", makeHtml());
		}
		
		fw.setPath("main.jsp");
		fw.setRedirect(false);
		return fw;
	}


	private Object makeHtml() {
		StringBuilder sb=new StringBuilder();
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id"); //로그인한 사람의 아이디를 꺼냄
		if(id.equals("admin")) {
			sb.append("<input class='admin' type='submit' value='전체회원리스트' formaction='userlist' />");
			sb.append("<input class='admin' type='submit' value='펫시터지원리스트' formaction='petapplylist' />");
			sb.append("<input class='admin' type='submit' value='블랙리스트' formaction='blacklist' />");
				
		}
		return sb;
	}


	public Forward joinfrm() {
		Forward fw=new Forward();
		fw.setPath("JoinForm.jsp");
		fw.setRedirect(false);
		return fw;
	}


	public Forward logout() {
		HttpSession session=request.getSession();
		session.invalidate();
		Forward fw=new Forward();
		fw.setPath("main.jsp");
		fw.setRedirect(true);
		return fw;
	}


	public Forward home() {
		Forward fw=new Forward();
		fw.setPath("main.jsp");
		fw.setRedirect(false);
		return fw;
	}


	public Forward petsittersearch() {
		Forward fw=new Forward();
		fw.setPath("petsittersearch.jsp");
		fw.setRedirect(false);
		return fw;
	}


	public Forward memberjoin() {
		Forward fw=new Forward();
		Member mb=new Member();
	      mb.setId(request.getParameter("id"));
	      System.out.println("ID "+request.getParameter("id"));
	      mb.setPw(request.getParameter("pw"));
	      System.out.println("PW "+request.getParameter("pw"));
	      mb.setName(request.getParameter("name"));	 
	      System.out.println("이름 "+request.getParameter("name"));
	      
	      mb.setGender(request.getParameter("gender"));	      	 
	      System.out.println("성별 "+request.getParameter("gender"));
	      
	      mb.setBirth(request.getParameter("birth"));	      
	      System.out.println("생일 "+request.getParameter("birth"));
	      
	      mb.setTel(request.getParameter("tel"));
	      System.out.println("전화번호 "+request.getParameter("tel"));
	      mb.setMail(request.getParameter("mail1")+"@"+request.getParameter("mail2"));
	      System.out.println("이메일 "+request.getParameter("mail1")+"@"+request.getParameter("mail2"));
	      mb.setAddr(request.getParameter("addr1")+request.getParameter("addr2"));
	      System.out.println("주소 "+request.getParameter("addr1")+request.getParameter("addr2"));
	      
	      MemberDao mDao=new MemberDao();
	      boolean result= mDao.memberJoin(mb);
	      mDao.close();
	      if(result) {
	         fw.setPath("main.jsp");
	         fw.setRedirect(true);
	      }else {
	         request.setAttribute("msg", "회원가입 실패");
	         fw.setPath("JoinForm.jsp");
	         fw.setRedirect(false);
	      }
	      return fw;
	}


	public Forward petapply() {
		MemberDao mDao=new MemberDao();
		List<String> sList=null;
		sList=mDao.petapply();
		for(int i=0;i<sList.size();i++) {
		System.out.println(i+"번째 sList = "+sList.get(i));
		}
		
		Gson g = new Gson();
		String r = g.toJson(sList);
		System.out.println("===========");
		System.out.println(r);
		request.setAttribute("jsontest" , r);
		Forward fw=new Forward();
		fw.setPath("petapply.jsp");
        fw.setRedirect(false);
		return fw;
	}


	public Forward insetPetApply() {
		Forward fw=new Forward();
		System.out.println("제목 : "+request.getParameter("title"));		
		System.out.println("내용 : "+request.getParameter("cont"));	
		System.out.println("단가 : "+request.getParameter("price"));
		System.out.println("이미지 : "+request.getParameter("imgFile"));
		System.out.println("질문1 : "+request.getParameter("questions1"));
		System.out.println("질문2 : "+request.getParameter("questions2"));
		System.out.println("질문3 : "+request.getParameter("questions3"));
		System.out.println("질문4 : "+request.getParameter("questions4"));
		System.out.println("질문5 : "+request.getParameter("questions5"));
		System.out.println("질문6 : "+request.getParameter("questions6"));
		System.out.println("질문7 : "+request.getParameter("questions7"));
			
		HttpSession session=request.getSession();
		String id=session.getAttribute("id").toString();
		System.out.println("id="+id);
		PetApply pa=new PetApply();		
		pa.setId(session.getAttribute("id").toString());
		pa.setTitle(request.getParameter("title"));
		pa.setCont(request.getParameter("cont"));
		pa.setPrice(request.getParameter("price"));
		pa.setImgFile(request.getParameter("imgFile"));
		pa.setQuestions1(request.getParameter("questions1"));
		pa.setQuestions2(request.getParameter("questions2"));
		pa.setQuestions3(request.getParameter("questions3"));
		pa.setQuestions4(request.getParameter("questions4"));
		pa.setQuestions5(request.getParameter("questions5"));
		pa.setQuestions6(request.getParameter("questions6"));
		pa.setQuestions7(request.getParameter("questions7"));
		MemberDao mDao=new MemberDao();
	
	    boolean result= mDao.insetPetApply(pa);
	    mDao.close();
	    if(result) {
	         fw.setPath("main.jsp");
	         fw.setRedirect(true);
	      }else {
	        System.out.println("지원포워딩 실패");
	         fw.setPath("petapply.jsp");
	         fw.setRedirect(false);
	      }
		return fw;
	}


	public Forward updateUser() {
		Forward fw=new Forward();
		MemberDao mDao=new MemberDao();
		HttpSession session=request.getSession();
		String id=session.getAttribute("id").toString();
		System.out.println("회원타입 변경 id="+id);
		boolean result= mDao.updateUser(id);
		mDao.close();
		 if(result) {
	         fw.setPath("main.jsp");
	         fw.setRedirect(true);
	      }else {
	         fw.setPath("petapply.jsp");
	         fw.setRedirect(false);
	      }
		return fw;
	}


	public Forward petapplylist() {
		
		Forward fw=new Forward();
		fw.setPath("petapplylist.jsp");
        fw.setRedirect(false);
        
		return fw;
	}


	public Forward blackList() {
		
		Forward fw=new Forward();
		fw.setPath("blacklist.jsp");
		fw.setRedirect(false);
		return fw;
	}


	public Forward userlist() {
		
		Forward fw=new Forward();
		fw.setPath("userlist.jsp");
		fw.setRedirect(false);
		return fw;
	}
	
	
	
	

}
