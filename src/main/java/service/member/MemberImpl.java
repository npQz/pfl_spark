package service.member;

import model.member.MemberDao;
import model.member.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Service
public class MemberImpl implements Member {

  private MemberDao memberDao;
  private MemberDto memberDto;

  @Autowired
  public MemberImpl(MemberDao memberDao, MemberDto memberDto) {
    this.memberDao = memberDao;
    this.memberDto = memberDto;
  }

  @Override
  public boolean getMemberCheck(HttpServletRequest request, HttpServletResponse response, String email, String pw) throws Exception {

    memberDto = memberDao.memberCheck(email);

    if (memberDto == null){
      response.setContentType("text/html; charset=utf-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      out.println("alert('아이디가 존재하지 않습니다.')");
      out.println("location.href='/signIn.me'");
      out.println("</script>");
      out.close();
      return true;

    } else if (!pw.equals(memberDto.getPassword())) {
      response.setContentType("text/html; charset=utf-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      out.println("alert('비밀번호가 일치하지 않습니다.')");
      out.println("location.href='/signIn.me'");
      out.println("</script>");
      out.close();
      return true;
    }

    HttpSession session = request.getSession();
    session.setAttribute("email", email);
    session.setAttribute("name", memberDto.getName());

    return true;

  }

  @Override
  public boolean idChk(String email) {
    memberDto = memberDao.memberCheck(email);

    if (memberDto == null) {
      return true;
    } else {
      return false;
    }

  }


}
