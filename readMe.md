# Spring AI Advisor 
This is used to modify requests/responses to the LLM 
We can use them for 
 1- Adding memory to the chat
 2- Safeguarding or censoring repo
 3- Monitoring or altering input/output 


# Running LLM's in Local
Using Ollama 
- Spring AI is designed to use Mistral model. In case the model is not installed in our local machine it throws errors
- To change the model, we need to specify the model name is application.properties file

# Embeddings 

Numerical vector representations of text (or other inputs) that help AI systems understand relationships and similarities.

Embeddings Using API Client 

POST API : https://api.openai.com/v1/embeddings 
JSON
{
"model": "text-embedding-3-small",
"input": "Whale",
"dimensions": 2
}

Embeddings using Spring AI

Embeddings are stored in Vector Data Bases

# Laws of Cosines

Cosine Similarity : measures how close two vectors are by comparing the cosine of the angle between them.
It is useful for checking how semantically similar two words or texts are:
Formula for Cosine similarity  = A.B/(||A||. ||B||)
Range 
 1 -> Identical direction (high similarity)
 0 -> Orthogonal (no similarity)
 -1 -> Opposite Direction 

# Applicaiton of Embeddings 
1- Semantic Searching

