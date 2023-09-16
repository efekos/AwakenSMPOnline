package me.efekos.awakensmponline.data;

import me.efekos.simpler.config.Storable;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerData extends Storable {
    private final UUID uuid;
    private String name;
    private boolean alive;
    private boolean revived;
    private ParticleOptions particleOptions;
    private ArrayList<Friend> friends;
    private FriendModifications defaultFriendModifications;
    private ArrayList<ParticleType> particlesBought;
    private ArrayList<AnimationType> animationsBought;
    private AnimationType selectedAnimation;
    private ArrayList<WaitingNotification> notifications;
    private UUID currentTeam;

    public PlayerData(UUID id,String name, boolean alive, boolean revived, ParticleOptions particleOptions) {
        this.uuid = id;
        this.name = name;
        this.alive = alive;
        this.revived = revived;
        this.particleOptions = particleOptions;
        this.defaultFriendModifications = new FriendModifications(true,true,false,false,true,true,true,true,false);
        this.friends = new ArrayList<>();
        this.particlesBought = new ArrayList<>();
        this.animationsBought = new ArrayList<>();
        this.selectedAnimation = AnimationType.NONE;
        this.notifications = new ArrayList<>();
        this.currentTeam = null;
    }

    public UUID getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(UUID currentTeam) {
        this.currentTeam = currentTeam;
    }

    public AnimationType getSelectedAnimation() {
        return selectedAnimation;
    }

    public void setSelectedAnimation(AnimationType selectedAnimation) {
        this.selectedAnimation = selectedAnimation;
    }

    public ArrayList<WaitingNotification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<WaitingNotification> notifications) {
        this.notifications = notifications;
    }

    public void addNotification(WaitingNotification notification){
        this.notifications.add(notification);
    }

    public ArrayList<AnimationType> getAnimationsBought() {
        return animationsBought;
    }

    public void setAnimationsBought(ArrayList<AnimationType> animationsBought) {
        this.animationsBought = animationsBought;
    }

    public void addBoughtAnimation(AnimationType type){
        this.animationsBought.add(type);
    }

    public ArrayList<ParticleType> getParticlesBought() {
        return particlesBought;
    }

    public void setParticlesBought(ArrayList<ParticleType> particlesBought) {
        this.particlesBought = particlesBought;
    }

    public void addBoughtParticle(ParticleType type){
        this.particlesBought.add(type);
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void updateFriend(UUID friendUuid,Friend newFriend){
        for(Friend friend:getFriends()){
            if(friend.getPlayerId().equals(friendUuid)){
                friend.setModifications(newFriend.getModifications());
                friend.setLastName(newFriend.getLastName());
            }
        }
    }

    public boolean friendsWith(UUID uuid){
        return getFriend(uuid)!=null;
    }

    public boolean friendsWith(String name){
        return getFriend(name)!=null;
    }

    public Friend getFriend(String name){
        for (Friend friend:getFriends()){
            if(friend.getLastName().equals(name)) return friend;
        }
        return null;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    public void addFriend(Friend friend){
        this.friends.add(friend);
    }

    public FriendModifications getDefaultFriendModifications() {
        return defaultFriendModifications;
    }

    public void setDefaultFriendModifications(FriendModifications defaultFriendModifications) {
        this.defaultFriendModifications = defaultFriendModifications;
    }

    public Friend getFriend(UUID uuid){
        for (Friend friend:getFriends()){
            if(friend.getPlayerId().equals(uuid)) return friend;
        }
        return null;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isRevived() {
        return this.revived;
    }

    public void setRevived(boolean revived) {
        this.revived = revived;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParticleOptions getParticleOptions() {
        return particleOptions;
    }

    public void setParticleOptions(ParticleOptions particleOptions) {
        this.particleOptions = particleOptions;
    }
}
