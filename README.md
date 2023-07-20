![](./images/title-export.png)

This plugin adds an AwakenSMP system with advanced features! for those don't know what Awaken SMP is: When you get killed by a player, you drop your head and can't respawn unless someone places that head to ground.

If you find any bugs, report to [my discord server](https://discord.gg/8PPgcmYNf4)! I always make my dms open and let you ping me on the server.

![](./images/features-export.png)

* AwakenSMP System!
* Customizable particles when you get revived!
* 3 Types of fancy animations to play when you get revived!
* Friend system to be officially friends with your best pal!
* Team system to manage your teams, and get a team chat!
* Craftable head!

![](./images/commands-export.png)

/awakensmp
* deadplayers - Gets a list of the players died. \
* kill \<player> -  Kills someone permanently. \
* reloadconfig - Reloads config files and data. \
* revive \<player> - Revives someone at your location. \

/friend
* add \<player> - Send a friend request to someone.
* accept \<id> - Accept a friend request sent to you.
* deny \<id> - Deny a friend request sent to you.
* cancel \<id> - Cancel a friend request you sent.
* list - Get a list of all your friends.
* modify \<player> - Change what your friends can see about you.
* inventory \<player> - See what your friends have in their inventory.
* armor \<player> - See what your friends have in their main/off-hand, and armor.
* compass \<player> - Get a compass that leads to your friends!
* teleport \<player> - Teleport to your friends.
* remove \<player> - Remove players

/team
* create \<name> - Create a new team with given name.
* delete - If you are owner of the team you are in, use this to delete that team.
* invite \<player> - Invite someone to your team. Only owners can do this.
* join \<id> - Join to a team that sent an invite to you.
* leave - Leave your current team. Team owners can't do this.
* members - See everyone on your team.
* my - Information about your team.
* reject \<id> - Reject a team invite.
* chat \<message> - Use team chat
* togglechat - Switch between team chat and normal chat. When you are on team chat, every message you sent will act like a message sent with /team chat.

/options - Open reviving options menu.

![](./images/permissions-export.png)

````text
awakensmp.command.team.chat - Allows to use /team chat

awakensmp.command.team.create - Allows to use /team create

awakensmp.command.team.delete - Allows to use /team delete

awakensmp.command.team.invite - Allows to use /team invite

awakensmp.command.team.join - Allows to use /team join

awakensmp.command.team.leave - Allows to use /team leave

awakensmp.command.team.members - Allows to use /team members

awakensmp.command.team.my - Allows to use /team my

awakensmp.command.team.reject - Allows to use /team reject

awakensmp.command.team.togglechat - Allows to use /team togglechat

awakensmp.command.particles - Allows to use /particles

awakensmp.command.friend.accept - Allows to use /friend accept

awakensmp.command.friend.add - Allows to use /friend add

awakensmp.command.friend.armor - Allows to use /friend armor

awakensmp.command.friend.cancel - Allows to use /friend cancel

awakensmp.command.friend.compass - Allows to use /friend compass

awakensmp.command.friend.deny - Allows to use /friend deny

awakensmp.command.friend.info - Allows to use /friend info

awakensmp.command.friend.inventory - Allows to use /friend inventory

awakensmp.command.friend.list - Allows to use /friend list

awakensmp.command.friend.modify - Allows to use /friend modify

awakensmp.command.friend.remove - Allows to use /friend remove

awakensmp.command.friend.teleport - Allows to use /friend teleport

awakensmp.god - Be invincible to dropping head.

awakensmp.kill - Be able to drop someone's head.

awakensmp.revive - Be able to revive someone with his head.
````

![](./images/installation-export.png)

1. Download the jar file
2. Put it to your server plugins folder
3. Start the server

![](./images/configuration-export.png)

There is two files for configuration. `config.yml` has configuration about plugin, and `lang.yml` has configuration about messages and text.

### config.yml
````yaml
# Effects that will be applied to dead players
when-dead:

  # If enabled, dead players won't be able to move.
  freeze: true

  # If enabled, dead players will have Blindness effect.
  blind: true

# If enabled, players will pop out particles when getting revived, they can also customize their particles with /particles
revive-particles: true

# If enabled, dead players will be able to get revived, even while they are offline.
# You can't revive someone offline yet. so don't mess with this.
offline-revives: false

# Features.
features:

  # Enables /friend command.
  friend: true

  # Enabled /team command.
  team: true

# Announcement options
announcements:

  # If enabled, everyone will get announcements when someone gets revived.
  revives: false

  # If enabled, everyone will get announcements when someone crafts a revive head.
  crafts: false

  # If enabled, everyone will get announcements when someone gets killed and drops his head, even while showDeathMessages are off.
  kills: false

# Customize Revive Head's recipe
recipe:

  # If enabled, everything will be ignored and default recipe will be used.
  use-default: true

  # Recipe's shape. Every letter is a symbol of a material. Customize these symbols and their materials in 'materials' to use them in this shape.
  shape:
    - 'DND'
    - 'TWT'
    - 'DND'

  # All materials that should be used inside 'shape'. See https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html for all materials.
  materials:
    D: 'DIAMOND_BLOCK'
    T: 'TOTEM_OF_UNDYING'
    W: 'NETHER_STAR'
    N: 'NETHERITE_INGOT'
    a: 'AIR'
````

### lang.yml
````yaml
# Announcement messages.
announcements:

  # When someone gets killed.
  # %killer% - Player who killed the victim.
  # %victim% - Player who got killed by the killer.
  killed: '&a%killer% &egot &a%victim%&e''s head!'

  # When someone crafts a Revive Head.
  # %player% - Player who crafted a Revive Head.
  # %head-name% - Revive Head's custom name provided by this config.
  head-crafted: '&a%player% &ejust crafted a %head-name%&e!'

  # When someone revives a player.
  # %reviver% - Player who revived someone.
  # %revived% - Player who got revived.
  # %head-name% - Revive Head's custom name provided by this config.
  revived: '&a%reviver% &eused a %head-name% &eand revived &a%revived%&e!'

# Command messages.
commands:

  # /awakensmp messages.
  awakensmp:

    # /awakensmp deadplayers messages.
    deadplayers:

      # Header of the list
      header: "&4-----&cDead &fPlayers&4-----"

      # A format to display for every dead player.
      # %player% - One of the dead players.
      format: '- &a%player%'

      # If no one died in the server, you will see this.
      no-one: 'No one died, yet...'

    # /awakensmp reloadconfig messages.
    reloadconfig:

      # If reloading the config was successful.
      success: '&aSuccessfully reloaded the config!'

    # If player entered a wrong sub command.
    help:

      # Header of the help menu.
      header: '&2----------&aHelp Menu&2----------'

      # A format for every sub command of /awakensmp.
      # %syntax% - Usage for a command.
      # %description% - Description of a command.
      format: '%syntax% - %description%'

      # Footer of the help menu.
      footer: '&2-----------------------------'

    # /awakensmp kill messages.
    kill:

      # If given player is not alive.
      not-alive: '&b%player% &cis dead already.'

      # If given player is not online.
      not-online: '&b%player% &cis not online.'

      # If command sender gave a wrong name.
      not-player: '&cThere is no one called &b%player%&c.'

      # If given player has awakensmp.god
      got-player: '&b%player% &cis a god, meaning there is no way to kill him.'

      # When you get killed with this command
      # %killer% - Player who ran this command and killed you.
      force: '&b%killer% &cKilled you by force!'

      # When successfully killed the given player.
      # %player% - Player who got killed.
      done: '&aSuccessfully killed &b%player%!'

  # /friend messages.
  friend:

    # If given player is not a friend of the player ran a command.
    not-friend: '&b%player% &cis not your friend.'

    # If given player is not online
    not-online: '&b%player% &cis not online.'

    # If given player disabled that thing from the player ran a command.
    not-allowed: '&b%player% &cdid not allow you to do that.'

    # /friend list messages.
    list:
      header: '&4--------&cYour Friends&4--------'
      format: '- &e%name%'
      footer: '&4---------------------------'

    # /friend accept messages.
    accept:
      not-uuid: '&b%uuid% &cis not a valid UUID.'
      not-req: '&cThere is no request with id &b%uuid%&c.'
      not-urs: '&cThis request was not sent to you.'
      not-friend: '&cThis is not a friend request.'
      done: '&aSuccessfully accepted &b%player%&a''s friend request!'
      hey: '&b%player% &aaccepted your friend request!'

    # /friend add messages.
    add:
      not-player: '&cThere is no one called &b%player%&c.'
      friend-already: '&cYou are already friends with &b%player%&c.'
      already-sent: '&cYou already sent a friend request to &b%player%&c.'
      done: '&aSuccessfully sent a friend request to &b%player%&a!'
      other: '&b%player% &esent a friend request to you!'
      urself: '&cYou can''t send friend request to yourself.'

    # /friend cancel messages
    cancel:
      not-uuid: '&b%uuid% &cis not a valid UUID.'
      not-req: '&cThere is no request with id &b%uuid%&c.'
      not-urs: '&cYou did not sent this request.'
      not-friend: '&cThis is not a friend request.'
      done: '&aSuccessfully canceled friend request!'

    # /friend deny messages.
    deny:
      not-uuid: '&b%uuid% &cis not a valid UUID.'
      not-req: '&cThere is no request with id &b%uuid%&c.'
      not-urs: '&cThis request was not sent to you.'
      not-friend: '&cThis is not a friend request.'
      done: '&aSuccessfully denied &b%player%&a''s friend request!'
      hey: '&b%player% &edenied your friend request.'

    # /friend info messages.
    info:
      header: '&5----------&d%player%''s Information&5----------'
      health: '&dHealth: &c%health%/%max%'
      food: '&dHunger: &#945623%food%'
      exp: '&dEXP: &a%exp%&2/&a%max% &2(&a%%percentage%&2) &aLevel %level%'
      location: '&dLocation: %x%&5,&d%y%&5,&d%z%'
      world: '&dWorld: &a%world%'
      footer: '&5------------------------------------------'

    # /friend teleport messages.
    teleport:
      success: '&aSuccessfully teleported to &b%player%&a!'
      hey: '&b%player% &eteleported to you.'

    # /friend remove messages.
    remove:
      done: '&aSuccessfully removed &b%player% &afrom friends!'

    # /friend modify messages.
    modify:
      not-player: '&b%player% &cis not a player'
      need-value: '&cYou need to enter &etrue &cor &efalse &cas third argument to change a value.'
      invalid-key: '&b%key% &cis not an option.'
      header: '&1----------&9Modify what %player% can do&1----------'
      worldAllowed: '&9See which dimension you are in: &a%value%'
      compassAllowed: '&9Get a compass that leads to you: &a%value%'
      inventoryAllowed: '&9See your inventory: &a%value%'
      locationAllowed: '&9See where you are: &a%value%'
      armorAllowed: '&9See the items in your hands and your armor: &a%value%'
      healthAllowed: '&9See how many hearts you have: &a%value%'
      expAllowed: '&9See your exp and level: &a%value%'
      foodAllowed: '&9See how much hungry you are: &a%value%'
      teleportAllowed: '&9Teleport to your location without asking: &a%value%'
      true: '&a✔'
      false: '&c✖'
      footer: '&1------------------------------------------------'

    # /friend compass messages.
    compass:

      # When you successfully got someone's compass.
      # %player% - Player who the given compass leads to.
      done: '&aSuccessfully gave you a compass to track &b%player%a!'

  # /team messages.
  team:

    # If player is not in a team but tried to use a team only command.
    not-in-team: '&cYou are not in a team'

    # If player is in a team.
    already-in-team: '&cYou are in a team already.'

    # When player needs to be owner of the team, but he is not.
    not-owner: '&cYou are not the owner of team called &b%team%&c.'

    # /team create messages.
    create:

      # When the given name contains invalid characters. It is important to tell the player that he needs to use a-z,A-Z and _ here.
      not-valid-chars: '&cYour team name should only include &ba-z characters&c,&bA-Z characters&c and &b_&c.'

      # Default description for a team.
      default-description: 'A brand new team!'

      # When the name player gave was taken by another team.
      same-name: '&b%name% &cis taken by another team.'

      # When player successfully creates a team.
      done: '&aSuccessfully created a new team called &b%team%&a!'

    # /team delete messages.
    delete:

      # When player successfully deletes his team.
      done: '&aSuccessfully deleted the team &b%team%&a!'

    # /team invite messages.
    invite:

      # If given player does not exist.
      not-player: '&cThere is no one called &b%player%&c.'

      # If given player is not online
      not-online: '&b%player% &cis not online.'

      # If player tried to use this command is not the owner of the team he is in.
      not-owner: '&cYou are not the owner of this team. Only team owners can invite people to their team.'

      # When a team invited you to join.
      hey: '&eTeam called &b%team% &esent you an invite to join their team!'

      # When player successfully sends an invite to someone.
      done: '&aSuccessfully sent an invite to &b%player%&a!'

    # /team join messages.
    join:

      # If given request id was invalid.
      invalid-req: '&cThere is no request with id &b%id%&c.'

      # If given request is not a team invite.
      not-team: '&cThis request is not a team invite.'

      # If given request was sent to someone else
      not-got: '&cThis request were sent to you.'

      # When player successfully accepts that request.
      done: '&aSuccessfully joined to the team called &b%team%&a!'

    # /team leave messages.
    leave:

      # If player is the owner of his team.
      owner: '&cYou are the owner of this team, you can''t leave unless you delete the team.'

      # When player successfully leaves his team.
      done: '&aSuccessfully left the team!'

    # /team members messages.
    members:

      # Header of the member list
      header: '&6----------&eTeam Members&6----------'

      # Owner of the team.
      owner: '&f- &4[&cTeam Owner&4] &e%player%'

      # Normal members of the team.
      member: '&f- &e%player%'

      # Footer of the list
      footer: '&6--------------------------------'

    # /team my messages.
    my:

      # Header of the information.
      header: '&5----------&dTeam Information&5----------'

      # Name of the team
      name: '&dName: &b%name%'

      # Owner of the team.
      owner: '&dOwner: &b%owner%'

      # Member list messages.
      members:

        # Header of the list
        header: '&dMembers:'

        # Members of the team.
        format: '&5- &d%player%'

    # /team reject messages.
    reject:

      # When player successfully rejects the team invtite.
      done: '&aSuccessfully rejected the team invite came from &b%team%&a!'

    # /team togglechat messages.
    togglechat:

      # When player disabled team chat.
      disabled: '&eSwitched to general chat!'

      # When player enabled team chat.
      enabled: '&eSwitched to team chat!'

# Menu messages.
menus:

  # Menu buttons.
  buttons:

    # 'Back' button
    back: '&eBack'

    # 'Close' button
    close: '&cClose'

  # 'Particle Options' menu messages.
  particle_options:

    # Menu's title.
    title: 'Particle Options'

    # 'Particle Type' button. Leads to 'Choose a Particle Type' menu.
    type: '&eParticle Type'

    # 'Particle Color' button. Leads to 'Choose a Particle Color' menu.
    color: '&eParticle Color'

    # 'Animation' button. Leads to 'Choose an Animation type' menu.
    animation: '&eAnimation'

    # Displays at the item that the player selected.
    selected: '&6You currently selected this'

    # Displays at the items that the player not selected.
    unselected: '&aClick to select this'

  # 'Choose a Particle Type' menu.
  particle_options_type:

    # Menu's title.
    title: 'Choose a Particle Type'

    # Menu types
    types:

      # 'Totem' type.
      totem: '&eTotem'

      # 'Explosion' type.
      explosion: '&eExplosion'

      # 'Beam' type.
      beam: '&eBeam'

      # 'Fog' type.
      fog: '&eFog'

      # 'Snowball' type.
      snowball: '&eSnowball'

      # 'Block' type.
      block: '&eBlock'

  # 'Choose a Particle Color' menu.
  particle_options_color:

    # Menu's title.
    title: 'Choose a Particle Color'

    # Menu color buttons.
    colors:

      # 'White' color.
      white: '&eWhite'

      # 'Orange' color.
      orange: '&eOrange'

      # 'Magenta' color.
      magenta: '&eMagenta'

      # 'Light Blue' color.
      light_blue: '&eLight Blue'

      # 'Yellow' color.
      yellow: '&eYellow'

      # 'Lime' color.
      lime: '&eLime'

      # 'Pink' color.
      pink: '&ePink'

      # 'Gray' color.
      gray: '&eGray'

      # 'Light Gray' color.
      light_gray: '&eLight Gray'

      # 'Cyan' color.
      cyan: '&eCyan'

      # 'Purple' color.
      purple: '&ePurple'

      # 'Blue' color.
      blue: '&eBlue'

      # 'Brown' color.
      brown: '&eBrown'

      # 'Green' color.
      green: '&eGreen'

      # 'Red' color.
      red: '&eRed'

      # 'Black' color.
      black: '&eBlack'

  # 'Choose an Animation Type' menu.
  animation_type:

    # The menus title.
    title: 'Choose an Animation Type'

    # Animation types.
    types:

      # 'None' type. Which is nothing, only particles.
      none: '&eNone'

      # 'Block' type. Which is an animation where two block appear, and the player breaks out of them.
      block: '&eBlock'

      # 'Thunder' type. Which is an animation where a lot of lightning strikes hit the head to give him a soul, I mean revive player.
      thunder: '&eThunder'

      # 'Beam' type. Which is an animation where a diamond beacon appears, and brings the player back here from the other world.
      beam: '&eBeam'

  # Inventory menu of a friend.
  friend_inventory:

    # Menu's title.
    # %player% - Player whose inventory will appear in this menu
    title: 'Inventory of %player%'

  # Armor menu of a friend.
  friend_armor:

    # Menu's title.
    # %player% - Player whose equipment will appear in this menu
    title: 'Equipment of %player%'

# Reviving messages.
reviving:

  # If head's name is not a player.
  # %player% - Head's name.
  not-player: '&cThere is no one called &b%player%&c.'

  # If the player is not dead.
  # %player% - Player whose not dead.
  not-dead: '&b%player% &cis not dead.'

  # If the player is not online.
  # %player% - Player whose not online.
  not-online: '&b%player% &cis not online.'

  # When the player successfully uses a Revive Head and revives a player.
  # %player% - Player who got revived.
  done: '&aSuccessfully revived &b%player%&a!'

  # When someone revives you.
  # %player% - Player who revived you.
  hey: '&b%player% &arevived you!'

# Item messages.
items:

  # 'Revive Head' messages.
  revive_head:

    # Item's name.
    name: '&eRevive Head'

    # Item's description.
    description: '&6Rename this head with someone''s name to revive him!'

  # 'Tracking Compass' messages.
  tracking_compass:

    # When the compass you try to refresh is expired.
    # %date% - Date that this compass became expired.
    expired: '&cThis compass is expired at &a%date%&c.'

    # If you are not the player who got this compass.
    not-owner: '&cYou are not the owner of this compass.'

    # If the player whose being tracked by this compass is not online.
    # %player% - Player whose being tracked by this compass.
    not-online: '&a%player% &cis not online right now.'

    # Item's name.
    name: '&eTracking Compass'

    # Item's description messages.
    description:

      # Shows the player attached to this compass, meaning this compass will follow the player.
      # %player% - Player whose being tracked by this compass.
      attach: '&6Attached to &d%player%'

      # Shows the player that owns this compass, meaning that /friend compass command is ran by him.
      # %tracker% - Player who got this compass with /friend compass.
      own: '&6Owned by &d%tracker%'

      # Shows the time that this compass will expire and be unable to use.
      # %date% - Date that this compass will be expired.
      expire: '&6Expires at &b%date%'

    # When you refresh the compasses way by right-clicking with the compass.
    refreshed: '&aRefreshed location!'

    # Format to translate dates inside a Tracking Compass.
    # Color codes are not recommended, but you can still use them.
    # %hour% - Hour of the date.
    # %minute% - Minute of the date.
    # %second% - Seconds of the date.
    # %day% - Day of the date.
    # %month% - Month of the date.
    # %year% - Year of the date.
    date-format: '%hour%:%minute%, %day%/%month%/%year%'

# Text buttons.
buttons:

  # 'Toggle' button. Used at /friend modify to toggle boolean values.
  toggle: '&6[&eToggle&6]'

  # 'Accept' button. Used to accept friend/team requests.
  accept: '&2[&aAccept&2]'

  # 'Deny' button. Used to deny friend/team requests.
  deny: '&4[&cDeny&4]'

  # 'Cancel' button. Used to cancel friend/team requests.
  cancel: '&6[&eCancel&6]'

  # 'Modify' button. Used for an alias to /friend modify <player>
  modify: '&9[&bModify&9]'

  remove: '&4[&cRemove&4]'

  inventory: '&6[&eInventory&6]'

  armor: '&6[&eArmor&6]'

  back: '&6[&eBack&6]'

  join: '&2[&aJoin&2]'

  reject: '&4[&cReject&4]'

# Notification messages.
notifications:

  # Header of the list.
  list: '&eYou have &b%count% &enew notifications:'

  # If someone were revived you while you're offline.
  revived: '&b%player% &eRevived you!'

  # Notifications about friend system.
  friend:

    # If someone accepted your friend request while you're offline.
    accepted: '&b%player% &eaccepted your friend request!'

    # If someone denied your friend request while you're offline.
    denied: '&b%player% &edenied your friend request!'

    # If someone sent you a friend request while you're offline.
    requested: '&b%player% &esent you a friend request!'

  # Notifications about team system.
  team:

    # When someone sends a message to team chat.
    chat: '&5[&dTEAM CHAT&5] &e%player%&8: &a%message%'

    # When someone joins the team you are in.
    joined: '&5[&dTEAM&5] &b%player% &ejoined the team!'

    # When someone leaves the team you are in.
    leaved: '&5[&dTEAM&5] &b%player% &eleft the team!'

    # When someone rejects an invitation coming from your team.
    reject: '&5[&dTEAM&5] &b%player% &erejected our team invite!'
````