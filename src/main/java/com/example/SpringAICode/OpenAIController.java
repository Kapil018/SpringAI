package com.example.SpringAICode;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/openai")
public class OpenAIController {

//    @Autowired
//    private OpenAiChatModel openAiChatModel;


    private ChatClient chatClient;
    @Autowired
    @Qualifier("openAiEmbeddingModel")
    private EmbeddingModel embeddingModel;

//        ChatMemory chatMemory = MessageWindowChatMemory.builder().build();

        //Constructor Injection
//        public OpenAIController(ChatClient.Builder builder){
//            this.chatClient = builder
//                    .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
//                    .build();
//        }
    public OpenAIController(OpenAiChatModel chatModel){
        this.chatClient = ChatClient.create(chatModel);
    }

    @GetMapping("/api/{message}")
    public String getAnswer(@PathVariable String message){
        ChatResponse chatResponse = chatClient.prompt(message).call().chatResponse();
        System.out.println(chatResponse.getMetadata().getModel());
        System.out.println(chatResponse.getMetadata().getUsage());
        String response = chatResponse.getResult().getOutput().getText();
        return response;
    }
    @PostMapping("/api/recommend")
    public String recommendMovie(@RequestParam String type, @RequestParam String year, @RequestParam String lang){
        String template = """ 
                I want to watch a {type} movie tonight with good rating,
                looking for movies around this year {year}.
                The language I am looking for is {lang}.
                Suggest one specific movie and tell me the cast and length of the movie.
                Don't ask extra questions!

                Response format should be:
                1. Movie Name
                2. Basic Plot
                3. Cast
                4. Length
                5. IMDB Rating
                """;
        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(Map.of("type",type, "year",year, "lang", lang));
        String response = chatClient.prompt(prompt).call().content();
        return response;
    }

    @PostMapping("/api/embedding")
    public float[] embedding(@RequestParam String text){
        return embeddingModel.embed(text);
    }

}

