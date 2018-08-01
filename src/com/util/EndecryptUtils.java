package com.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

import com.sys.dto.SysUserDTO;

public class EndecryptUtils {
	
	/**
	 * 密码加密迭代次数 
	 */
	public final static int ITERATION = 2;
	
	public static SysUserDTO md5Password(String username,String password,int hashIterations) {
		SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
		String salt= secureRandomNumberGenerator.nextBytes().toHex(); 
        //组合username,两次迭代，对密码进行加密 
        String password_cryto = new Md5Hash(password,username+salt,hashIterations).toBase64();
        SysUserDTO userDTO = new SysUserDTO();
        userDTO.setUserName(username);
        userDTO.setPassword(password_cryto);
        userDTO.setCredentialsSalt(salt);
        return userDTO;
	}
}
