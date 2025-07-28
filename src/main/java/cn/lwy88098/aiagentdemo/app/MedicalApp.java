package cn.lwy88098.aiagentdemo.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MedicalApp {

    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = "扮演深耕宠物医疗健康领域的专家。开场向用户表明身份，告知用户可咨询宠物健康相关的问题，同时引导用户在必要时寻求专业兽医的帮助。" +
            "基于宠物医学常识、常见疾病特征、日常护理规范等知识，为用户解答关于宠物（犬、猫为主，兼顾其他常见宠物如小型哺乳动物、鸟类、水族等）的健康问题。" +
            "明确自身局限性，不替代兽医进行诊断、治疗，仅提供初步参考信息，当用户描述的情况涉及疾病诊断、用药、紧急医疗等专业场景时，必须优先引导用户联系线下兽医或宠物医院。。" +
            "主动传递科学养宠理念，包括合理饮食、疫苗接种、驱虫计划、行为健康等知识，帮助用户预防宠物常见疾病。";

    /**
     * AI客户端
     * @param dashscopeChatModel 调用的AI大模型
     */
    public MedicalApp(ChatModel dashscopeChatModel) {

        InMemoryChatMemoryRepository chatMemoryRepository = new InMemoryChatMemoryRepository();
        int MAX_MESSAGES = 10;
        MessageWindowChatMemory messageWindowChatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(MAX_MESSAGES)
                .build();
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(messageWindowChatMemory).build()
                )
                .build();
    }

    /**
     * AI对话，支持多轮基础对话
     * @param message 用户Prompt
     * @param chatId 对话窗口ID
     * @return AI大模型返回结果
     */
    public String doChat(String message ,String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(a -> a.param(ChatMemory.DEFAULT_CONVERSATION_ID, chatId))
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content:{}",content);
        return content;
    }
}
