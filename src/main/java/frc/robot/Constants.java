package frc.robot;

public class Constants {




    //drive spark ports
    public static int rFMotorPort = 4;
    public static int rRMotorPort = 8;
    public static int lFMotorPort = 3;
    public static int lRMotorPort = 1;
    //drive spark limit switches, not ordered
    public static int rFLimitPort = 0;
    public static int rRLimitPort = 1;
    public static int lFLimitPort = 3;
    public static int lRLimitPort = 2;
    
    //winch talon ports and encoder
    public static int winchMotorPort = 9;
    public static int winchEncoderPort = 9;

    //intake and translate motor ports
    public static int intakeMotorPort = 12;
    public static int translateMotorPort = 2;
    public static int translateSwitch = 8;

    //shooter FX and hood SRX motors
    public static int shooterMotorPort = 7;
    public static int hoodMotorPort = 8;
    public static double shooterSpeed = 14250; 

    //cargo vision PID constants
    public static double visionP = 0.0075;

    //cargo vision pipeline constants
    public static int redAlliance = 1;
    public static int blueAlliance = 2;


    public static double maxYellowEncoderValue = -65.5;
    


    //PCMs
    public static int PCMLPort = 10;
    public static int PCMRPort = 11;
    //front drive pistons forward and reverse channel
    public static int fDPForwardChannel = 0;
    public static int fDPReverseChannel = 1;
    //rear drive pistons forward and reverse
    public static int rDPForwardChannel = 3;
    public static int rDPReverseChannel = 2; 

    //climber yellow pistons left and right and yellow motor
    public static int yLForwardChannel = 3;
    public static int yLReverseChannel = 2;
    public static int yRForwardChannel = 0;
    public static int yRReverseChannel = 1;
    public static int yellowMotorPort = 5;
    //clamp piston forward and reverse channel
    public static int clampForwardChannel = 4;
    public static int clampReverseChannel = 5;

    //intake piston
    public static int intakeForwardChannel = 6;
    public static int intakeReverseChannel = 7;


}
