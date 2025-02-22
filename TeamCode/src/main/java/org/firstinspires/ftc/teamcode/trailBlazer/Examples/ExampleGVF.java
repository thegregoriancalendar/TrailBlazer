package org.firstinspires.ftc.teamcode.trailBlazer.Examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.trailBlazer.gvf.PathGVF;
import org.firstinspires.ftc.teamcode.trailBlazer.util.Pose2D;
import org.firstinspires.ftc.teamcode.trailBlazer.util.Vector2D;

@TeleOp
public class ExampleGVF extends LinearOpMode {

    private Vector2D[] setPoints = {new Vector2D(0, 0), new Vector2D(0, 10), new Vector2D(10, 10)};

    private PathGVF path = new PathGVF(setPoints);

    private double volt = 12.0;

    private double strafeCompensation = 1.1;

    private double loopTime = 0.0;
    private double hz = 0.0;


    @Override
    public void runOpMode() {

        DcMotor frontLeftMotor = new DcMotor(hardwareMap.get("fl"));
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("bl");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("fr");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("br");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            Vector2D position = new Vector2D(); // replace with localizer output
            Pose2D d = path.getPowers(position, volt, hz);

            // inv kinem
            double frontLeftPower = (d.getY() + d.getX() + d.getAngle().getRadians());
            double backLeftPower = (d.getY() - d.getX() + d.getAngle().getRadians());
            double frontRightPower = (d.getY() - d.getX() - d.getAngle().getRadians());
            double backRightPower = (d.getY() + d.getX() - d.getAngle().getRadians());

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            double loop = System.nanoTime();
            hz = 1000000000 / (loop - loopTime);
            loopTime = loop;
        }
    }
}