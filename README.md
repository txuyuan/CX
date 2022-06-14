# CX

A plugin bundle I made for my own use. Just thought I might put it here in case anyone wanted.

> *All commands are provided without the preceding `/` that indicates a cmd*

**Contents**

* [CHome](#chome)
* [CChat](#cchat)
* [CMenu](#cmenu)
* [Miscellaneous](#miscallenous)

<br></br>

## CHome

Teleportation system for player convenience. Uses 1 player-unique home location and one global shop district

***Commands***

`chome home` - Teleport to own home

`chome home <player>` - Teleport to player's home (OP function)

`chome sethome` - Set personal home location

`chome death` - Teleport to previous death location

`chome shop` - Teleport to global shop

`chome setshop` - Set global shop location (OP function)

***Permissions***

`chome.home` - Teleport to home (Default:All)

`chome.sethome` - Set home (Default:All)

`chome.death` - Teleport to previous death location (Default:All)

`chome.shop` - Teleport to shop

`chome.admin` - Set global shop and access other player's homes

<br></br>

## CChat

Aesthetic improvements to chat. Join/leave messges and a few commands

***Commands***

`say` - Altered formatting when sent from console

`print <string>` - Prints input string as plain text to all players

<br></br>

## CMenu

Allows a player to be hidden from the server list menu (Client-side screen showing all the servers you have saved.
Accessed when clicking multiplayer from the minecraft opening screen)

***Commands***

`cmenu <player>` - Toggles whether the player is hidden

***Permissions***

`cmenu.use` - Utilise CMenu functions

<br></br>

## Miscallenous

### Spectator TP

By default, CX blocks people from using the teleport function inbuilt into spectator mode.

***Permissions***

`cx.allowSpecTp` - Utilising teleport function inbuilt in spectator mode (Default:OP)

<br></br>

## Licenses

Source code licensed under [GNU GPL v3.0](./LICENSE)
