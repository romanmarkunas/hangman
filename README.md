# Deployment
1) Checkout from Git using git clone
2) Setup JNDI data source jndi-name in hangman-servlet.xml to server provided connection pool
3) Add database driver dependency to gradle.build, using syntax similar to mysql-connector-java
4) 
5) Create database named hangman with tables:
5.1) words with columns: word[VARCHAR(40)]
5.2) games with columns: id[4 byte int], secretword[VARCHAR(40)], revealedword[VARCHAR(40)], triesleft[4 byte int],
notusedchars[VARCHAR(30)], expirydate[DATETIME]
5.3) Database migration could be done using Hibernate migration script - TODO

# Architecture
Project architecture has 4 layers, similar to Onion architecture. Main idea of architecture is dependency inversion,
that is the inner layer knows nothing about outer layer.
## 1) Domain
The innermost layer. Contains business wide data structures, which are usually stored in databases. In this project
domain contains words, which might be used in any word game.
## 2) Service
This layer contains Domain access objects and business wide rules. In this project WordsDAO and random word generator
will occupy this layer.
## 3) Applications
This layer contains application logic (user use cases). In this project we have 3 use cases: hangman game, database
update and managing page.
## 4) Infrastructure
The outermost layer. This layer contains databases, framework related code (I don't like core logic to be dependant on
framework syntax), controllers, views, etc - basically everything that doesn't affect application logic, subject to
frequent change or maintained by other department/company developers.
