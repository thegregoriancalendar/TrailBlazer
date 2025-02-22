package org.firstinspires.ftc.teamcode.trailBlazer.util;

import androidx.core.math.MathUtils;

import org.firstinspires.ftc.teamcode.trailBlazer.util.Angle;

import java.util.Arrays;

public class Pose2D extends Vector2D {
    private Angle angle;

    public Pose2D(double x, double y, double theta) {
        super(x, y);
        this.angle = new Angle(theta);
    }

    public Vector2D toVector2D() {
        return new Vector2D(super.getX(), super.getY());
    }

    public Angle getAngle() {
        return angle;
    }


}
