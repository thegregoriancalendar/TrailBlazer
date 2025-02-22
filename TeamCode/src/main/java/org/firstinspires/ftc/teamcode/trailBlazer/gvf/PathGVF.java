package org.firstinspires.ftc.teamcode.trailBlazer.gvf;

import androidx.core.math.MathUtils;

import org.firstinspires.ftc.teamcode.trailBlazer.util.Vector2D;
import org.firstinspires.ftc.teamcode.trailBlazer.util.Pose2D;

public class PathGVF {
    private Vector2D[] bezierPoints;
    private final double feedForward;
    private double scalingFactor;
    private double acuteness;
    private double tolerance;

    private final static double max_power = 1;

    public final static double max_heading = 1;

    private Vector2D[] pathPoints;

    public PathGVF(Vector2D[] bezierPoints) {
        this.bezierPoints = bezierPoints;
        feedForward = 0;
        scalingFactor = 0.1;
        acuteness = 0.5;
        tolerance = 0.1;
        pathPoints = new Vector2D[100];
        initializePathPoints();
    }

    public PathGVF(Vector2D[] bezierPoints, double scalingFactor, double acuteness, double tolerance, double feedForward, int resolution) {
        this.bezierPoints = bezierPoints;
        this.feedForward = feedForward;
        this.scalingFactor = scalingFactor;
        this.acuteness = acuteness;
        this.tolerance = tolerance;

        pathPoints = new Vector2D[resolution];
        initializePathPoints();

    }

    private void initializePathPoints() {
        for (int i = 0; i < pathPoints.length; i++) {
            pathPoints[i] = getBezierPose(bezierPoints, i / (double)pathPoints.length).toVector2D();
        }
    }
    private double sqrDistance(double initX, double initY, double finX, double finY) {
        return Math.pow(finX - initX, 2) + Math.pow(finY - initY, 2);
    }
    private double getClosest(Vector2D vec) {
        double[] distances = new double[pathPoints.length];

        for (int i = 0; i < pathPoints.length; i++) {
            distances[i] = sqrDistance(vec.getX(), vec.getY(), pathPoints[i].getX(), pathPoints[i].getY());
        }

        return findMinIndex(distances) / (double)distances.length;
    }
    private int findMinIndex(double[] arr) {
        int minIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                minIndex = i;
            }
        }

        return minIndex;
    }
    private Pose2D getBezierPose(Vector2D[] points, double t) {
        Vector2D[] newPoints = new Vector2D[points.length - 1];

        for (int i = 0; i < newPoints.length; i++) {
            double a = points[i].getX();
            double b = points[i].getY();
            double c = points[i + 1].getX();
            double d = points[i + 1].getY();
            newPoints[i] = new Vector2D(a + (c - a) * t, b + (d - b) * t);
        }

        if (newPoints.length == 2) {
            double a = newPoints[0].getX();
            double b = newPoints[0].getY();
            double c = newPoints[1].getX();
            double d = newPoints[1].getY();

            return new Pose2D(a + (c - a) * t, b + (d - b) * t, Math.atan2(d - b, c - a));
        }

        return getBezierPose(newPoints, t);
    }

    public Vector2D getPushVector(Vector2D position) {
        double t = getClosest(position);
        Pose2D proj = getBezierPose(bezierPoints, t);
        Vector2D go = new Vector2D(Math.cos(proj.getAngle().getRadians()) * scalingFactor * (1 - t + feedForward) * (1 - acuteness), Math.sin(proj.getAngle().getRadians()) * scalingFactor * (1 - t + feedForward) * (1 - acuteness));
        double distance = Math.sqrt(sqrDistance(position.getX(), position.getY(), proj.getX(), proj.getY()));
        Vector2D correction = new Vector2D((proj.getX() - position.getX()) * scalingFactor * acuteness * distance, (proj.getY() - position.getY()) * scalingFactor * acuteness * distance);
        return go.add(correction);
    }

    public Pose2D getPowers(Vector2D position, double v, double hz) {

        Vector2D powers = getPushVector(position);
        double magnitude = powers.getMagnitude();

        double x_power = powers.getX();
        double y_power = powers.getY();

        if (magnitude > 1) {
            x_power /= magnitude;
            y_power /= magnitude;
        }
        double heading_power = 0;

        if (Math.sqrt(sqrDistance(position.getX(), position.getY(), bezierPoints[bezierPoints.length - 1].getX(), bezierPoints[bezierPoints.length - 1].getY())) < tolerance) {
            x_power = 0;
            y_power = 0;
        }

        return new Pose2D((-x_power / v * 12), (y_power / v * 12), (heading_power / v * 12));
    }

    public void updateParameters(double s, double t, double a) {
        scalingFactor = s;
        tolerance = t;
        acuteness = a;
    }


}
