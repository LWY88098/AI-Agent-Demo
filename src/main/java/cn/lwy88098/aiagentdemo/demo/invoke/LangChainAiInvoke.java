package cn.lwy88098.aiagentdemo.demo.invoke;

import dev.langchain4j.community.model.dashscope.QwenChatModel;

/**
 * LangChain4j DashScope 框架调用 AI 大模型
 */
public class LangChainAiInvoke {
    public static void main(String[] args) {
        QwenChatModel qwenChatModel = QwenChatModel.builder()
                .apiKey(TestApiKey.API_KEY)
                .modelName("qwen-plus")
                .build();
        String answer = qwenChatModel.chat("你好");
        System.out.println(answer);
    }
}
