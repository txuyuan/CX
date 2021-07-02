## CX - A plugin bundle I made for my own use
Just thought I might put it here in case anyone wanted. 

### Content Summary:
CHome:
 > Teleportation system
 > 
 > Teleport to home - Home is set by and unique to player, can be accessed from anywhere
 > 
 > Teleport to death - Teleports to last death point, single-use for each death
 > 
 > Teleport to shop - Teleports to a common shopping district point, configurable by admin (Just did this for fun, might not be useful)

CGroup:
 > Group chat system
 > 
 > Useful for communicating with team members (etc) without cluttering the global chat or just for private information
 >
 > Allows creation and management of different groups, in which members can message other members
 > 
 > Group owner (creator or otherwise), can invite, kick (etc) other players

CChat:
 > Simple aesthetics for the chat
 > 
 > Join/leave message
 > 
 > Different aesthetic for /say - Makes it nicer when you talk from console
 > 
 > /print function
 > 
 > Allows CGroup to function


## Functions



### CHome
Commands
> `/chome home`
>> Teleport to Home
> `/chome sethome`
>> Set personal home at current location
> `/chome death`
>> Teleport to last death location
> `/chome help`
>> Display command usages
Permissions
> `chome.home`
>> Permission to use /chome home
> `chome.sethome`
>> Permission to use /chome sethome
> `chome.death`
>> Permission to use /chome death


### CGroup
Commands
> `/cgroup group`  
> The main command for managing group chats

>> `/cgroup group create <name> <alias> <colour>`  
>>> Create a new group

>> `/cgroup disband <alias>`  
>>> Disband a group you own

>> `/cgroup group remove <alias> <member>`  
>>> Remove a member from a group you own

>> `/cgroup group transfer <alias> <member>`  
>>> Transfer ownership of a group you own

>> `/cgroup group info <alias`
>>> Find primary info of group (owner, members, invites)

> `/cgroup invite` - The command for managing invites
>> `/cgroup invite send <alias> <invitee>`  
>>> Send an invite to a player to join a group you own

>> `/cgroup invite revoke <alias> <invitee>`  
>>> Revoke an invite sent to a player from a group you own

>> `/cgroup invite accept <alias>`  
>>> Accept an invite sent to you from a group

> `/cgroup channel <alias>` - Switch to speaking in a different group
>> Alternative alias - /cch
>> If the alias does not exist or `ALL`, you will be sent to global chat

> `/cgroup help` - Command for info`
>> /cgroup help channel|group|invite
>>> Find usage information for each subcommand (same info as here)
Permissions

> `cgroup.use`
>> Permission to use CGroup
