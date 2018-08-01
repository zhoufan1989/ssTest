package com.sys.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.controller.BaseController;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.sys.exception.SystemException;
import com.util.ShiroUtils;

@Controller
@RequestMapping("/")
public class SysLoginController extends BaseController{
	
	@Autowired
	private Producer captchaProducer;
	

	/**
	 * 登录
	 */
	@RequestMapping(value="sys/login", method=RequestMethod.POST)
	@ResponseBody
	public Object login(String username,String password, String captcha, HttpServletRequest request) throws IOException{
		String kaptcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(!captcha.equalsIgnoreCase(kaptcha)) {
//			request.setAttribute("error", "验证码错误！");
			return error("验证码错误");
		}
		try {
			Subject subject = ShiroUtils.getSubject();
			// sha256加密
			password = new Sha256Hash(password).toHex();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
		} catch (UnknownAccountException e) {
			return error(e.getMessage());
		}catch (IncorrectCredentialsException e) {
			return error(e.getMessage());
		}catch (LockedAccountException e) {
			return error(e.getMessage());
		}catch (AuthenticationException e) {
			return error(e.getMessage());
		}
		return putData();
	}
	
	/**
	 * 获取验证码
	 */
	@RequestMapping(value="captcha.html")
	public void kaptcha(HttpServletRequest req, HttpServletResponse rsp) {
		ServletOutputStream out = null;
		try {
//			HttpSession session = req.getSession();
			rsp.setDateHeader("Expires", 0);
			rsp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			rsp.addHeader("Cache-Control", "post-check=0, pre-check=0");
			rsp.setHeader("Pragma", "no-cache");
			rsp.setContentType("image/jpeg");
			
			String capText = captchaProducer.createText();
//			session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
			BufferedImage image = captchaProducer.createImage(capText);
//			out = rsp.getOutputStream();
			
			// 保存到shiro session
			ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
			out = rsp.getOutputStream();
			
			ImageIO.write(image, "jpg", out);
			out.flush();
		} catch (IOException e) {
			throw new SystemException(e);
		}finally {
			try {
				out.close();
			} catch (IOException e) {	
				throw new SystemException(e);
			}
		}
	}
	
	/**
	 * 退出
	 */
	@RequestMapping(value = "logout.html", method = RequestMethod.GET)
	public String logout() {
		ShiroUtils.logout();
		return "redirect:login.html";
	}
}
