package com.example.SpringAICode;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ollama")
public class OllamaController {

    private ChatClient chatClient;
    @Autowired
    @Qualifier("ollamaEmbeddingModel")
    private EmbeddingModel embeddingModel;

    public OllamaController(OllamaChatModel chatModel){
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
    //Generating the Embeddings
    @PostMapping("/api/embedding")
    public float[] generateEmbedding(@RequestParam String text){
        return embeddingModel.embed(text);
    }

    //Get Similarities -> useful for searching
    @PostMapping("/api/similarity")
    public double getSimilarities(@RequestParam String text1, @RequestParam String text2){
        float[] embedding1 = embeddingModel.embed(text1);
        float[] embedding2 = embeddingModel.embed(text2);

        double dotProduct = 0;
        double norm1 = 0;
        double norm2 = 0;
        for(int i=0;i< embedding1.length;i++){
            dotProduct += embedding1[i] * embedding2[i];
            norm1 += Math.pow(embedding1[i],2);
            norm2 += Math.pow(embedding2[i],2);
        }
        //return dotProduct /(Math.sqrt(norm1) * Math.sqrt(norm2));
        //In Percentage
        return (dotProduct  /(Math.sqrt(norm1) * Math.sqrt(norm2)))*100;
    }


}

