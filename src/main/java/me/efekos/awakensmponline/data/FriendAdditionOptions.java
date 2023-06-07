package me.efekos.awakensmponline.data;

public class FriendAdditionOptions {
    private boolean everyoneAllowed;
    private boolean friendsFriendAllowed;
    private boolean teammatesAllowed;

    public FriendAdditionOptions(boolean everyoneAllowed, boolean friendsFriendAllowed, boolean teammatesAllowed) {
        this.everyoneAllowed = everyoneAllowed;
        this.friendsFriendAllowed = friendsFriendAllowed;
        this.teammatesAllowed = teammatesAllowed;
    }

    public boolean isEveryoneAllowed() {
        return everyoneAllowed;
    }

    public void setEveryoneAllowed(boolean everyoneAllowed) {
        this.everyoneAllowed = everyoneAllowed;
    }

    public boolean isFriendsFriendAllowed() {
        return friendsFriendAllowed;
    }

    public void setFriendsFriendAllowed(boolean friendsFriendAllowed) {
        this.friendsFriendAllowed = friendsFriendAllowed;
    }

    public boolean isTeammatesAllowed() {
        return teammatesAllowed;
    }

    public void setTeammatesAllowed(boolean teammatesAllowed) {
        this.teammatesAllowed = teammatesAllowed;
    }
}
