# CX
A plugin bundle I made for my own use. Just thought I might put it here in case anyone wanted. 

> *All commands are provided without the preceding `/` that indicates a cmd*

**Contents**
* [CHome](#chome)
* [CGroup](#cgroup)
* [CChat](#cchat)
* [CMenu](#cmenu)
* [Miscallenous](#miscallenous)


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
## CGroup
Group chat system for communicating with friends/associates/team members privately without cluttering the global chat. Each group has an admin to control the group and its members

***Commands***

`cgroup group` - Subcommand covering group administration
* `create <name> <alias> <colour>` - Create a new group
* `disband <alias>` - Disband a group
* `remove <alias> <member>` - Remove a member from a group
* `transfer <alias> <member>` - Transfer ownership of a group
* `info <alias` - Find primary info of group (owner, members, invites)

`cgroup invite` - Subcommand managing invites
* `send <alias> <invitee>` - Send an invite to a player to join a group
* `revoke <alias> <invitee>` - Revoke an invite for a player to join a group
* `accept <alias>` - Accept an invite sent to you

`cgroup channel <alias>` - Switch to speaking in a different group *(Alt alias: /cch)*

`cgroup help` - Provides helpful information
* `channel | group | invite` - Usage info about each subcommand

***Permissions***

`cgroup.use` - Utilise overall CGroup functions



<br></br>
## CChat
Aesthetic improvements to chat. Join/leave messges and a few commands

***Commands***

`say` - Altered formatting when sent from console

`print <string>` - Prints input string as plain text to all players



<br></br>
## CMenu
Allows a player to be hidden from the server list menu (Client-side screen showing all the servers you have saved. Accessed when clicking multiplayer from the minecraft opening screen)

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
---
Source code licensed under [GNU GPL v3.0](./LICENSE)

Notes and documentation licensed under [GNU FDL v1.3](./LICENSE-Documents)
