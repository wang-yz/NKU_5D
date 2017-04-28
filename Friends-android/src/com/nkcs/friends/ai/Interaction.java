package com.nkcs.friends.ai;

import java.util.Date;

import android.content.Intent;

import com.google.gson.Gson;
import com.nkcs.friends.activity.UserUpdateActivity;
import com.nkcs.friends.ai.ChatMessage.Type;
import com.nkcs.friends.fragment.FragmentManagerActivity;


public class Interaction {
	/**
	 * 进行用户msg交互处理
	 * @param msg
	 * @return
	 */
	@SuppressWarnings("null")
	public static ChatMessage sendMessage(String msg)
	{
		ChatMessage chatMessage = new ChatMessage();
		Result result = new Result();
		if (msg.equals("你好")) {
			result.setText("嗨！你好！");
			chatMessage.setMsg(result.getText());
		} else if (msg.contains("修改个人信息")) {
			chatMessage.setMsg("修改个人信息");
		} else if (msg.contains("查看日历")) {
			chatMessage.setMsg("查看日历");
		} else if (msg.contains("选择兴趣")) {
			chatMessage.setMsg("选择兴趣");
		} else if (msg.contains("查看好友")) {
			chatMessage.setMsg("查看好友");
		} else {
			chatMessage.setMsg("让我想一想");
		}
		
		chatMessage.setDate(new Date());
		chatMessage.setType(Type.INCOMING);
		return chatMessage;
	}
}
