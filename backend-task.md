# Intern backend engineer test task

## Description

Implement a REST service for twitter-like posts feed.
Users can register, login, create and like posts.

## System Entities
1. user - email, password, full name, photo 
2. post - body up to 255 symbols
3. like

## Requirements

Unauthorised user can:
1. Register using email. User should confirm email via link.
2. Login in application using google account.
3. See all feeds with likes count.

Authorised user can:
1. See all posts feeds with likes count.
2. See own post feed.
3. Like/unlike to any post
4. See users who liked specific post
5. Create post
6. Delete post
7. Delete acc
8. Change photo and personal info

## Tech Details

1. Main stack - kotlin/java8 + spring core/boot/mvc/security/data/... + gradle
2. Authentication using JWT token
3. Any database
4. Swagger UI with OpenApi service http endpoints description 
5. Pack application in docker container(just create **Dockerfile**)
6. Use docker-compose file to run service, db and other monitoring tools.
7. Integration tests for service, that should cover various user scenarios.
8. Public Github Repo from begging, working with Pull Requests

## Ordered Output
1. swagger definition + database scheme
2. register/login + jwt token + inmemory store + tests
3. database + docker setup
4. connect app to database
5. post endpoints impl + tests
4. test endpoints impl + tests


## Hints

* You can create a simple ui for demonstration
* Create small, atomic git commits. Implement features one-by-ony.

## Hard mode
If described above requirements are too easy for you, consider next addition tasks:
1. Enable websocket for full-duplex communication between client and server side.
