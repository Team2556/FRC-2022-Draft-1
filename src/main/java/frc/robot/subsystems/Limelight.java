package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {

    private static final Limelight limelight = new Limelight();
    private static final PIDController limelightPID = new PIDController(0.025, 0.0, 0.0);
    private final NetworkTable LimelightTable = NetworkTableInstance.getDefault().getTable("limelight");

    private static double tx = 0;
    private static double ty = 0;
    private static double tv = 0;
    private static double ta = 0;

    private Limelight() {
        //ToDo log that limelight is null
        if(LimelightTable == null);

        LimelightTable.getEntry("pipeline").setNumber(0);
    }

    public static void nothing() {}

    @Override
    public void periodic() {
        tx = LimelightTable.getEntry("tx").getDouble(0);
        ty = LimelightTable.getEntry("ty").getDouble(0);
        tv = LimelightTable.getEntry("tv").getDouble(0);
        ta = LimelightTable.getEntry("ta").getDouble(0);
        SmartDashboard.putNumber("Limelight tx", tx);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);

        builder.addDoubleProperty("tx", () -> tx, null);
        builder.addDoubleProperty("ty", () -> ty, null);
        builder.addDoubleProperty("tv", () -> tv, null);
        builder.addDoubleProperty("ta", () -> ta, null);
    }

    public double getDistance() {
        double targetOffsetAngle_Vertical = ty;
        // how many degrees back is your limelight rotated from perfectly vertical?
        double limelightMountAngleDegrees = 30.0 ;
        // distance from the center of the Limelight lens to the floor
        double limelightLensHeightInches = 30.0;
        // distance from the target to the floor
        double goalHeightInches = 104.0;
        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
        // Distance from limelight to goal, inches
        SmartDashboard.putNumber("Limelight Dist", (goalHeightInches - limelightLensHeightInches)/Math.tan(angleToGoalRadians));
       return (goalHeightInches - limelightLensHeightInches)/Math.tan(angleToGoalRadians);
    }

    public double getCalculatedTurn() {
        double turn;
        turn = -limelightPID.calculate(tx);
        return turn;
    }

    public static double getTx() {
        return tx;
    }

    public static double getTy() {
        return ty;
    }

    public static int getTv() {
        return (int) Math.round(tv);
    }

    public static double getTa() {
        return ta;
    }

    public static void enableLEDs() {
        limelight.LimelightTable.getEntry("ledMode").setNumber(0);
    }

    public static void disableLEDs() {
        limelight.LimelightTable.getEntry("ledMode").setNumber(1);
    }

    public static void blinkLEDs() {
        limelight.LimelightTable.getEntry("ledMode").setNumber(2);
    }

    public static Limelight getLimelight() {
        return limelight;
    }
}