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

# Application of Embeddings 
1- Semantic Searching

# Vector Databases
Vector Db's let us search based on meaning, not just keywords. SpringAI makes it simple with VectorStore
# Simple Vector Store 
This SimpleVectorStore helps you load the documents, split them into embeddings and store them for semantic search.
Let us now start with this for practice, then move to real vector DB's like PgVector for production.
 # Steps to Implement 
    1- Load the data file
    2- Break into Chunks
    3- Create DataInitializer Class
    4- VectorStore Setup


# PG Vector Store 
Open source Postgre SQL extension for handling vector technologies.
ALlows storing embeddings in Postgres and performing similarity searches.

# Spring AI + PG Vectore
In Spring AI, PGVector works as a Vector Store implementation
You will need 
    1- Dependencies
    2- DB Configuration-> URL,username,password
    3- Vector Store properties 
        1- index type
        2- distance type (cosine, L2, etc)
        3- dimensions
        4- max document batch size 
