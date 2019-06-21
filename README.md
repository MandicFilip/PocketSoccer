# PocketSoccer
This Android app is multiplayer game for two players. Project is done in Java programming langueage using API 26 (Android Oreo 8.0.0).

This is a university project on a Software Engineering Department fourth year subject called Programming mobile devices, at the School of Electrical Engineering from University of Belgrade.

## Features
* Users can play game in singleplayer (against bot) or multiplayer mode.
* Users can change parameters and game enviorement.
* Results from each game is stored in database.
* Users can see thier results against other players.
* Game can be paused at any time and continued without loss of current game.

## New Game

Before start players can input thier names and pick flags for thier teams.
Each player has three disks which can be moved by swiping on display from disk in direction where disk should go. Speed of disk will depend on swipe length.

Disks can hit the ball and make it move. Direction will depend on collision angle and the ball speed will depend on speed of the disk and the ball before the collision.

If the player scores, he receives one point. Winner is the player with more points when game ends.

## Game Parameters

In settings menu, user can:
* pick background - grass, hard court, indoor court
* set game end condition - number of goals or time. User sets number of goals that one player has to score to win, or how long will game last in minutes
* game speed - low, medium, high
* game mode - singleplayer or multiplayer

## Results

In statistics user can see results from  each game ever played. User can click on result and see list of games played by players who played the clicked game.

## Resume game

Resume game option will be displayed if there is unfinished game with was interrupted by leaving the app.
