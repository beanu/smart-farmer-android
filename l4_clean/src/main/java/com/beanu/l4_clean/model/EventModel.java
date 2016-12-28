package com.beanu.l4_clean.model;


import com.beanu.l4_clean.model.bean.User;

/**
 * 所有的事件分发
 * Created by Beanu on 16/9/8.
 */
public class EventModel {


    //登陆事件
    public static class LoginEvent {

        public User mUser;

        public LoginEvent(User user) {
            mUser = user;
        }
    }


}
