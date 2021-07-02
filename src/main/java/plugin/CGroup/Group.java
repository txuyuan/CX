package plugin.CGroup;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.OfflinePlayer;
import org.bukkit.Bukkit;
import java.util.Iterator;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.Collection;
import java.util.ArrayList;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Group implements ConfigurationSerializable
{
    private String name;
    private String alias;
    private String colour;
    private String owner;
    private ArrayList<String> members;
    private ArrayList<String> invites;
    
    public Group(String name, String alias, String colour, String owner, ArrayList<String> members, ArrayList<String> invites) {
        this.members = new ArrayList<String>();
        this.invites = new ArrayList<String>();
        this.name = name;
        this.alias = alias;
        this.colour = colour;
        this.owner = owner;
        this.members = new ArrayList<String>(members);
        this.invites = new ArrayList<String>(invites);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getFormattedName() {
        return "§" + this.getFormatColour() + "§n" + this.name + "§r";
    }
    
    public Boolean setName(String newName, FileConfiguration data) {
        newName = newName.replace("_", " ");
        if (data.getConfigurationSection("groups") != null) 
            for (String existingAlias : data.getConfigurationSection("groups").getKeys(false)) 
                if (((Group)data.getObject("groups." + existingAlias, (Class)Group.class)).getName().toLowerCase().equals(newName.toLowerCase())) 
                    return false;
        this.name = newName;
        return true;
    }
    
    public String getAlias() {
        return this.alias;
    }
    
    public String getFormattedAlias() {
        return "§" + this.getFormatColour() + "§n§o" + this.alias + "§r";
    }
    
    public Short setAlias(String newAlias, FileConfiguration data) {
        newAlias = newAlias.toUpperCase();
        if (newAlias.length() != 3) 
            return 1;
        if (aliasExists(newAlias, data)) 
            return 2;
        if (newAlias.equals("ALL")) 
            return 3;
        this.alias = newAlias;
        return 0;
    }
    
    public static Boolean aliasExists(String alias, FileConfiguration data) {
        if (data.getConfigurationSection("groups") != null && data.getConfigurationSection("groups").getKeys(false).contains(alias)) 
            return true;
        return false;
    }
    
    public String getColour() {
        return this.colour;
    }
    
    public Boolean setColour(String newColour) {
        this.colour = newColour;
        if (this.getFormatColour() != 'x') 
            return true;
        return false;
    }
    
    public Character getFormatColour() {
        String colour;
        switch (colour = this.colour) {
            case "dark_aqua": 
                return '3';
            case "dark_blue": 
                return '1';
            case "dark_gray": 
                return '8';
            case "dark_purple": 
                return '5';
            case "dark_green": 
                return '2';
            case "yellow": 
                return 'e';
            case "red": 
                return 'c';
            case "aqua": 
                return 'b';
            case "blue": 
                return '9';
            case "gold": 
                return '6';
            case "gray": 
                return '7';
            case "black": 
                return '0';
            case "green": 
                return 'a';
            case "white": 
                return 'f';
            case "light_purple": 
                return 'd';
            case "dark_red": 
                return '4';
            default:
                break;
        }
        return 'x';
    }
    
    public String getOwner() {
        return this.owner;
    }
    
    public void setOwner(String newOwner) {
        this.owner = newOwner;
    }
    
    public Boolean ownedBy(String queryOwner) {
        if (queryOwner.equals(this.owner)) 
            return true;
        return false;
    }
    
    public ArrayList<String> getMembers() {
        ArrayList<String> buffer = new ArrayList<String>(this.members);
        return buffer;
    }
    
    public Boolean addMember(String newMember) {
        ArrayList<String> buffer = new ArrayList<String>();
        if (this.members.contains(newMember)) 
            return false;
        buffer.add(newMember);
        this.members = new ArrayList<String>(buffer);
        return true;
    }
    
    public Boolean removeMember(String removedMemberName) {
        ArrayList<String> buffer = new ArrayList<String>(this.members);
        String removedMember = Bukkit.getOfflinePlayer(removedMemberName).getUniqueId().toString();
        if (buffer.remove(removedMember)) {
            this.members = new ArrayList<String>(buffer);
            return true;
        }
        return false;
    }
    
    public ArrayList<String> getInvites() {
        ArrayList<String> buffer = new ArrayList<String>(this.invites);
        return buffer;
    }
    
    public Short sendInvite(String invitedPlayerName) {
        ArrayList<String> bufferMembers = new ArrayList<String>(this.members);
        ArrayList<String> bufferInvites = new ArrayList<String>(this.invites);
        OfflinePlayer invitedPlayerPlayer = Bukkit.getOfflinePlayer(invitedPlayerName);
        if (!invitedPlayerPlayer.hasPlayedBefore()) 
            return 1;
        String invitedPlayer = invitedPlayerPlayer.getUniqueId().toString();
        if (bufferMembers.contains(invitedPlayer)) 
            return 2;
        if (bufferInvites.contains(invitedPlayer)) 
            return 3;
        bufferInvites.add(invitedPlayer);
        this.invites = new ArrayList<String>(bufferInvites);
        return 0;
    }
    
    public Boolean revokeInvite(String revokedPlayerName) {
        ArrayList<String> buffer = new ArrayList<String>(this.invites);
        String revokedPlayer = Bukkit.getOfflinePlayer(revokedPlayerName).getUniqueId().toString();
        if (buffer.remove(revokedPlayer)) {
            this.invites = new ArrayList<String>(buffer);
            return true;
        }
        return false;
    }
    
    public Boolean acceptInvite(String acceptedPlayer) {
        ArrayList<String> bufferMembers = new ArrayList<String>(this.members);
        ArrayList<String> bufferInvites = new ArrayList<String>(this.invites);
        if (bufferInvites.remove(acceptedPlayer)) {
            bufferMembers.add(acceptedPlayer);
            this.members = new ArrayList<String>(bufferMembers);
            this.invites = new ArrayList<String>(bufferInvites);
            return true;
        }
        return false;
    }
    
    public Boolean rejectInvite(String acceptedPlayer) {
        ArrayList<String> bufferInvites = new ArrayList<String>(this.invites);
        if (bufferInvites.remove(acceptedPlayer)) {
            this.invites = new ArrayList<String>(bufferInvites);
            return true;
        }
        return false;
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<String, Object>();
        serialized.put("name", this.name);
        serialized.put("alias", this.alias);
        serialized.put("colour", this.colour);
        serialized.put("owner", this.owner);
        serialized.put("members", this.members);
        serialized.put("invites", this.invites);
        return serialized;
    }
    
    public static Group deserialize(Map<String, Object> map) {
    	ArrayList<String> members = (ArrayList<String>) map.get("members");
        return new Group(map.get("name").toString(), map.get("alias").toString(), map.get("colour").toString(), map.get("owner").toString(), (ArrayList<String>)map.get("members"), (ArrayList<String>)map.get("invites"));
    }
    
    public static Group getGroup(String alias, FileConfiguration data) {
        alias = alias.toUpperCase();
        if (aliasExists(alias, data)) 
            return (Group)data.getObject("groups." + alias, (Class)Group.class);
        return null;
    }
}
