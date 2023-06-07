package me.efekos.awakensmponline.data;

public class FriendModifications {
    private boolean worldAllowed;
    private boolean locationAllowed;
    private boolean compassAllowed;
    private boolean inventoryAllowed;
    private boolean armorAllowed;
    private boolean healthAllowed;
    private boolean expAllowed;
    private boolean foodAllowed;
    private boolean teleportAllowed;

    public FriendModifications(boolean worldAllowed, boolean locationAllowed, boolean compassAllowed, boolean inventoryAllowed, boolean armorAllowed, boolean healthAllowed, boolean expAllowed, boolean foodAllowed, boolean teleportAllowed) {
        this.worldAllowed = worldAllowed;
        this.locationAllowed = locationAllowed;
        this.compassAllowed = compassAllowed;
        this.inventoryAllowed = inventoryAllowed;
        this.armorAllowed = armorAllowed;
        this.healthAllowed = healthAllowed;
        this.expAllowed = expAllowed;
        this.foodAllowed = foodAllowed;
        this.teleportAllowed = teleportAllowed;
    }

    public boolean isTeleportAllowed() {
        return teleportAllowed;
    }

    public void setTeleportAllowed(boolean teleportAllowed) {
        this.teleportAllowed = teleportAllowed;
    }

    public boolean isHealthAllowed() {
        return healthAllowed;
    }

    public void setHealthAllowed(boolean healthAllowed) {
        this.healthAllowed = healthAllowed;
    }

    public boolean isExpAllowed() {
        return expAllowed;
    }

    public void setExpAllowed(boolean expAllowed) {
        this.expAllowed = expAllowed;
    }

    public boolean isFoodAllowed() {
        return foodAllowed;
    }

    public void setFoodAllowed(boolean foodAllowed) {
        this.foodAllowed = foodAllowed;
    }

    public boolean isWorldAllowed() {
        return worldAllowed;
    }

    public void setWorldAllowed(boolean worldAllowed) {
        this.worldAllowed = worldAllowed;
    }

    public boolean isLocationAllowed() {
        return locationAllowed;
    }

    public void setLocationAllowed(boolean locationAllowed) {
        this.locationAllowed = locationAllowed;
    }

    public boolean isCompassAllowed() {
        return compassAllowed;
    }

    public void setCompassAllowed(boolean compassAllowed) {
        this.compassAllowed = compassAllowed;
    }

    public boolean isInventoryAllowed() {
        return inventoryAllowed;
    }

    public void setInventoryAllowed(boolean inventoryAllowed) {
        this.inventoryAllowed = inventoryAllowed;
    }

    public boolean isArmorAllowed() {
        return armorAllowed;
    }

    public void setArmorAllowed(boolean armorAllowed) {
        this.armorAllowed = armorAllowed;
    }
}
