package org.firstinspires.ftc.teamcode.trailBlazer.util;

import androidx.core.math.MathUtils;
import java.util.Arrays;

public class Angle {
    private double theta;

    public Angle(double theta) {
        this.theta = normalizeRadians(theta);
    }

    public double getRadians() {
        return theta;
    }

    public double getDegrees() {
        return theta * 180 / Math.PI;
    }

    public static double normalizeRadians(double radians) {
        while (radians > 2 * Math.PI) {
            radians -= 2 * Math.PI;
        }

        while (radians < 0) {
            radians += 2 * Math.PI;
        }

        return radians;
    }


}
