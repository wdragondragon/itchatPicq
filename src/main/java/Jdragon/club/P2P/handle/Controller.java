package Jdragon.club.P2P.handle;

import Jdragon.club.P2P.bean.User_info;

public interface Controller {
     void handleMsg(String message, User_info user_info);
     void WXhandleFileMsg(User_info user_info,
                          String filepath);
    void WXhandleImgMsg(User_info userInfo, String filepath);
    void WXhandleVoiceMsg(User_info userInfo, String filepath);
}
