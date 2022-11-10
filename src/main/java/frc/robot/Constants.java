// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public class Constants {

    public enum Alliance {
        BLUE,
        RED,
        NONE
    }
    //drive spark ports
    public static final int rFMotorPort = 4;
    public static final int rRMotorPort = 6;
    public static final int lFMotorPort =3;
    public static final int lRMotorPort = 2;

    //winch talon ports and encoder
    public static final int winchMotorPort = 8;
    public static final int winchEncoderPort = 9;

    //intake and translate motor ports
    public static final int intakeMotorPort = 5;
    public static final int translateMotorPort = 1;
    //ToDo Reverse motor so speed is positive
    public static final double intakeSpeed = -1.0;

    //shooter FX and hood SRX motors
    public static final int shooterMotorPort = 9;
    public static final int hoodMotorPort = 7;
    //ToDo Reverse translate motor so speeds are positive
    public static final double translateSpeed = -0.3;
    public static final double translateShootSpeed = -1.0;

    //cargo vision PID constants
    public static final double visionP = 0.0075;

    //cargo vision pipeline constants
    public static final int redAlliance = 1;
    public static final int blueAlliance = 2;

    public static final double maxYellowEncoderValue = -65.5;

    //PCMs
    public static final int PCMLPort = 10;
    public static final int PCMRPort = 11;
    //front drive pistons forward and reverse channel
    public static final int fDPForwardChannel = 0; //dropped is forward
    public static final int fDPReverseChannel = 1;
    //rear drive pistons forward and reverse
    public static final int rDPForwardChannel = 3;
    public static final int rDPReverseChannel = 2;

    //climber yellow pistons left and right and yellow motor
    public static final int yLForwardChannel = 3;
    public static final int yLReverseChannel = 2;
    public static final int yRForwardChannel = 0; //kforward is winch diagonal
    public static final int yRReverseChannel = 1;
    public static final int yellowMotorPort = 8;
    //clamp piston forward and reverse channel
    public static final int clampForwardChannel = 4;
    public static final int clampReverseChannel = 5; //clamp off is reverse

    //intake piston
    public static final int intakeForwardChannel = 6;
    public static final int intakeReverseChannel = 7; //kReverse is intake out

    public static final double potMax = 360;
}
