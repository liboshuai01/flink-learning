package cn.liboshuai.flink.netty.demo06.common.protocol.command;

// 定义 Command 接口，存储指令标识
public interface Command {
    Byte LOGIN_REQUEST = 1; // 登录请求指令
    Byte LOGIN_RESPONSE = 2; // 登录响应指令
    Byte MESSAGE_REQUEST = 3; // 消息请求指令
    Byte MESSAGE_RESPONSE = 4; // 消息响应指令
    Byte LOGOUT_REQUEST = 5; // 登出请求指令
    Byte LOGOUT_RESPONSE = 6; // 登出响应指令
    Byte CREATE_GROUP_REQUEST = 7; // 创建群组请求指令
    Byte CREATE_GROUP_RESPONSE = 8; // 创建群组响应指令
    Byte LIST_GROUP_MEMBERS_REQUEST = 9; // 群组列表请求指令
    Byte LIST_GROUP_MEMBERS_RESPONSE = 10; // 群组列表响应指令
    Byte JOIN_GROUP_REQUEST = 11; // 加入群组请求指令
    Byte JOIN_GROUP_RESPONSE = 12; // 加入群组响应指令
    Byte QUIT_GROUP_REQUEST = 13; // 退出群组请求指令
    Byte QUIT_GROUP_RESPONSE = 14; // 退出群组响应指令
    Byte GROUP_MESSAGE_REQUEST = 15; // 群组消息请求指令
    Byte GROUP_MESSAGE_RESPONSE = 16; // 群组消息响应指令
    Byte HEARTBEAT_REQUEST = 17; // 心跳请求指令
    Byte HEARTBEAT_RESPONSE = 18; // 心跳响应指令
}
