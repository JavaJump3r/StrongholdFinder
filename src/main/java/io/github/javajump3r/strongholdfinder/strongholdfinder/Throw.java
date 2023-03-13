package io.github.javajump3r.strongholdfinder.strongholdfinder;

import net.minecraft.util.math.Vec3d;

public class Throw {
    public Throw(Vec3d pos,double angle) {
        this.pos = pos;
        while(angle>180)
            angle-=360;
        while(angle<-180)
            angle+=360;
        this.angle = angle;
    }
    Vec3d pos;
    double angle;

    @Override
    public String toString() {
        return String.format("x:%d z:%d angle:%d",(int)pos.getX(),(int)pos.getZ(),(int)angle);
    }
}
