# itchatPicq
简单的适配了微信机器人框架和QQ机器人框架，实现集合微信和QQ的机器人的适配器。采用的是小桂的cq框架Picqbot和itchat4j微信机器人框架。暂时只能对私聊的信息进行通配转发，如果要使用群聊只能使用一起打包的底层机器人框架的监听器。

[底层使用微信机器人框架itchat4j](https://github.com/wdragondragon/itchat4j)

[底层使用QQ机器人框架PicqBotX](https://github.com/wdragondragon/PicqBotX)

注：除了我封装的四个监听方法和四个发送方法，所有在底层的两个机器人框架中的方法均可重写。具体不同端机器人的方法请点到以上链接查看。
## 监听方法
### 1、handleMsg
    @Override
    public void handleMsg(PrivateMsg msg) {}
### 2、WXhandleFileMsg
    @Override
    public void WXhandleFileMsg(PrivateMsg msg) {}
### 3、WXhandleImgMsg
    @Override
    public void WXhandleImgMsg(PrivateMsg msg) {}
### 4、WXhandleVoiceMsg
    @Override
    public void WXhandleVoiceMsg(PrivateMsg msg) {}
    
## 发送方法
### 1、SendMsgToID
(需要发送的消息，QQ号或微信号在User_info中已封装，发送者是什么类型用户User_info中已封装)
### 2、SendWXFileMsgToID
(file的路径创建实例时定义，QQ号或微信号在User_info中已封装，发送者是什么类型用户User_info中已封装)
### 3、WXhandleImgMsg
(file的路径创建实例时定义，QQ号或微信号在User_info中已封装，发送者是什么类型用户User_info中已封装)
### 4、SendWXVoiceToID
(file的路径创建实例时定义，QQ号或微信号在User_info中已封装，发送者是什么类型用户User_info中已封装)

## 例子

### Client
```java
package Test;

import Jdragon.club.P2P.bean.*;
import Jdragon.club.P2P.handle.Adapter;
import cc.moecraft.icq.sender.IcqHttpApi;

/**
 * User_adr中存放自己设置的路径
 * User_info中有用户信息
 * 继承的四个接口无需调用，接受到信息时会自动处理。
 * //继承发送文本方法
 * SendMsgToID(
 * 需要发送的消息，QQ号或微信号在User_info中已封装，发送者是什么类型用户User_info中已封装)
 * //继承的微信转发语音、图片、文件方法
 * SendWXFileMsgToID()
 * SendWXPicMsgToID()
 * SendWXVoiceToID()
 * (file的路径创建实例时定义，QQ号或微信号在User_info中已封装，发送者是什么类型用户User_info中已封装)
 */
public class Client extends Adapter{
    public Client(IcqHttpApi httpAPI) {
        super(httpAPI);
        User_adr.file = "C:\\Users\\Lenovo\\Desktop\\酷Q Pro\\data\\file\\";
        User_adr.image = "C:\\Users\\Lenovo\\Desktop\\酷Q Pro\\data\\image\\";
        User_adr.voice = "C:\\Users\\Lenovo\\Desktop\\酷Q Pro\\data\\record\\";
        User_adr.viedo = "C:\\Users\\Lenovo\\Desktop\\酷Q Pro\\data\\viedo\\";
    }
    @Override
    public void handleMsg(PrivateMsg msg) {
        String message = msg.getMessage();
        User_info user_info = msg.getUser_info();
        SendMsgToID(message,user_info.getQQnumber(),user_info.getType());
    }
    @Override
    public void WXhandleFileMsg(PrivateMsg msg) {
        String filepath = msg.getPath();
        User_info user_info = msg.getUser_info();
        SendWXFileMsgToID(filepath,user_info.getQQnumber(),user_info.getType());
    }
    @Override
    public void WXhandleImgMsg(PrivateMsg msg) {
        String filepath = msg.getPath();
        User_info user_info = msg.getUser_info();
        SendWXPicMsgToID(filepath,user_info.getQQnumber(),user_info.getType());
    }
    @Override
    public void WXhandleVoiceMsg(PrivateMsg msg) {
        String filepath = msg.getPath();
        User_info user_info = msg.getUser_info();
        SendWXVoiceToID(filepath,user_info.getQQnumber(),user_info.getType());
    }
}

```

### test
```java
package Test;

import Jdragon.club.P2P.handle.WXandQQListener;
import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import cc.moecraft.icq.sender.IcqHttpApi;
import cn.zhouyafeng.itchat4j.Wechat;

import java.io.File;

public class test {
    public static void main(String []args){
        //创建QQ机器人对象 ( 传入配置 )
        PicqBotX bot = new PicqBotX(new PicqConfig(9999).setDebug(true));
        // 添加一个机器人账户 ( 名字, 发送URL, 发送端口 )
        bot.addAccount("Bot01", "127.0.0.1", 5700);
        IcqHttpApi httpApi = bot.getAccountManager()
                .getNonAccountSpecifiedApi();
        //创建监听类适配器
        WXandQQListener controller = new Client(httpApi);
        // 注册事件监听器, 可以注册多个监听器
        bot.getEventManager().registerListeners(
                controller
        );
        bot.startBot();

        //微信机器人扫码登录的二维码的路径
        String qrPath = System.getProperty("user.dir")+ File.separator +"login";
        Wechat wechat = new Wechat(controller, qrPath); // 【注入】
        wechat.start();
        // 启动服务，会在qrPath下生成一张二维码图片，
        // 扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，
        // 过期时会自动更新，所以你可能需要重新打开图片

    }
}

```
