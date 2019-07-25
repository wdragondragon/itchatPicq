package Jdragon.club.P2P.handle;

import Jdragon.club.P2P.bean.PrivateMsg;

public interface Controller {
    void handleMsg(PrivateMsg msg);
    void WXhandleFileMsg(PrivateMsg msg);
    void WXhandleImgMsg(PrivateMsg msg);
    void WXhandleVoiceMsg(PrivateMsg msg);
}

