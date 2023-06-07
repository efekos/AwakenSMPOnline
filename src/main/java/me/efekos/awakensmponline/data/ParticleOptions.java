package me.efekos.awakensmponline.data;

public class ParticleOptions {
    private ParticleType type;
    private ParticleColor color;

    public ParticleOptions(ParticleType type, ParticleColor color) {
        this.type = type;
        this.color = color;
    }

    public ParticleType getType() {
        return type;
    }

    public void setType(ParticleType type) {
        this.type = type;
    }

    public ParticleColor getColor() {
        return color;
    }

    public void setColor(ParticleColor color) {
        this.color = color;
    }
}
