package com.zane;

import com.zane.dto.UserRegisterDto;
import com.zane.vo.LoginInforVo;

public interface ISysLoginService {
    /**
     * login method
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * register method
     * @param user
     * @return
     */
    Boolean register(UserRegisterDto user);

    /**
     * logout method
     * @return
     */
    void logout();

    LoginInforVo getLoginInforVo();
}
