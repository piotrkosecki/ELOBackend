# ELOBackend

This will be service for managing game statistics and match making

## Objectives

### Rest API for communication with the game server

First versions will contain only basic service without user authentication.
In the current basic form api should implement following endpoints:

    GET     /user/:userId -> UUID as String     | returns User
    POST    /user/:userLogin -> String          | returns Ok
    TBD(match API)
    
### Ranking calculation

Every time the game will be finished the user rating should be updated.
Rating will be based on the Elo rating system(https://en.wikipedia.org/wiki/Elo_rating_system).
