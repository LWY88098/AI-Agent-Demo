package cn.lwy88098.aiagentdemo.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MedicalAppTest {

    @Resource
    private MedicalApp medicalApp;

    @Test
    void doChat() {
        String chatId = UUID.randomUUID().toString();

        // 第一轮对话
        String message = "你好，我有一只小狗，它是阿拉斯加犬";
        String answer = medicalApp.doChat(message ,chatId);
        Assertions.assertNotNull(answer);

        // 第二轮对话
        message = "它最近一直掉毛而且皮肤很红，请问这是什么问题";
        answer = medicalApp.doChat(message,chatId);
        Assertions.assertNotNull(answer);

        // 第三轮对话
        message = "我的小狗有什么问题来着？刚才跟你说过，帮我回忆一下";
        answer = medicalApp.doChat(message,chatId);
        Assertions.assertNotNull(answer);
    }
}