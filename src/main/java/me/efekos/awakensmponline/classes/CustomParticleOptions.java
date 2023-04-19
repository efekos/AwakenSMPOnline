package me.efekos.awakensmponline.classes;

public class CustomParticleOptions {
    private String color;
    private String type;

    public CustomParticleOptions(ParticleColor color, ParticleType type) {
        this.color = color.toString();
        this.type = type.toString();
    }

    public ParticleColor getColor() {
        return ParticleColor.valueOf(color);
    }

    public ParticleType getType() {
        return ParticleType.valueOf(type);
    }

    public void setColor(ParticleColor color){this.color = color.toString();}

    public void setType(ParticleType type){this.type = type.toString();}
}

