package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Shooter {
    OI oi = new OI();
    Limelight limelight = new Limelight();
    Intake intake; 
    private TalonFX shooterMotor;
    private CANSparkMax hoodMotor;
    private RelativeEncoder hoodEncoder;
    private SparkMaxLimitSwitch hoodSwitch;
    private double percentOutput;
    private double LastOutput; 
    private double Kp;
    public boolean hoodReset = true;
    double difference = 0;

    public Shooter(Intake in){
        shooterMotor = new TalonFX(Constants.shooterMotorPort);
        hoodMotor = new CANSparkMax(Constants.hoodMotorPort, MotorType.kBrushless);
        hoodEncoder = hoodMotor.getEncoder();
        hoodSwitch = hoodMotor.getForwardLimitSwitch(Type.kNormallyOpen);
        percentOutput = 0;
        LastOutput = 0;
        Kp = 0.00000125;
        intake = in;
        hoodReset = false;
    }

    public void shooterInit(){
        hoodMotor.restoreFactoryDefaults();
        hoodMotor.setIdleMode(IdleMode.kBrake);
        shooterMotor.configFactoryDefault();
        shooterMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
        shooterMotor.setNeutralMode(NeutralMode.Coast);

    }

    public void hoodResetBySwitch() {
        hoodMotor(0.1);
        SmartDashboard.putBoolean("hoodSwitch", hoodSwitch.isPressed());
        if (hoodSwitch.isPressed()) {
            hoodMotor(0);
            resetHoodEncoder();
            hoodReset = true;
        }
    }

    public void shooterIntakeTeleop(){
        SmartDashboard.putNumber("Actual Shooter Speed", shooterMotor.getSelectedSensorVelocity());
        oi.shooterTeleopConfigSwitch();
        intake.intakeSolenoid(oi.intakeSolenoid());
        intake.intakeMotor(oi.intakeSpeed());
        if(hoodReset){
        hoodMotorRunToPosManual(hoodEquation());
        }
        else{
        hoodResetBySwitch();
        }
        shooterConstantRev(shooterEquation());
        // if (oi.limeLightTurn()) {
        //     shooterMotorLimelight(oi.shootConfigsNoCheck(), limelight.limelightCentered());
        // }
        // else if (intake.translateSwitch.get() == true) { //runs with intake
        //     intake.translateMotor(-0.2);
        //     shooterMotor(0);
        // }
        // else if (intake.translateSwitch.get() == false) {
        //     intake.translateMotor(0);
        //     shooterMotor(oi.shootAutomatedSpeed);   
        // }
        // else { // runs manually
        //     intake.translateMotor(oi.translateSpeed());
        //     if (intake.translateSwitch.get() == false) {
        //         intake.translateMotor(0);
        //         shooterMotor(oi.shootAutomatedSpeed);
        //     }
        //     else{
        //         shooterMotor(0);
        //     }
        // }
    }

    public void shooterConstantRev(double shootingSpeed) {
        double shootSpeed = 0;
        if (oi.limeLightTurn()) {
            shootSpeed = shootingSpeed;
            if (limelight.limelightCentered() && shouldShoot) {
                intake.translateMotor(-0.4);
            }
            else {
                intake.translateMotor(0);
            }
        }
        else if (intake.translateSwitch.get() == false) {
            shootSpeed = shootingSpeed;
            intake.translateMotor(0);
        }
        else if(intake.translateSwitch.get())
        {
            intake.translateMotor(-0.05);
        }
        else{
            intake.translateMotor(0);
        }
        
        
        shooterMotor(shootSpeed);
    }



    
    boolean shouldShoot;
    
    public void shooterMotor(double targetSpeed){
        SmartDashboard.putNumber("shooterMotor targetSpeed", targetSpeed);
        double fxspd = shooterMotor.getSelectedSensorVelocity();
        difference  = Math.abs(targetSpeed) - Math.abs(fxspd);
        double error = difference*Kp;
        LastOutput = LastOutput + error;
        percentOutput = LastOutput;
        if(percentOutput > 1){
            percentOutput = 1;
        }
        if (targetSpeed == 0){
            percentOutput = 0;
            difference = 0;
            error = 0;
            LastOutput = 0;
        }  
        if(Math.abs(difference)<400){
            if(targetSpeed != 0){
                shouldShoot=true;                
            }
        }
        else {
            shouldShoot = false;       
        }
           
        shooterMotor.set(ControlMode.PercentOutput, -percentOutput);
    }
   
    public void runIntakeByDiff(double targetSpeed){
        difference  = Math.abs(targetSpeed) - Math.abs(shooterMotor.getSelectedSensorVelocity());
        if(Math.abs(difference)<250){
            if(targetSpeed != 0){
                shouldShoot=true;                
                intake.translateMotor(oi.translateRunSpeed);
            }
            else{
                intake.translateMotor(0);
            }
        }
        else{
            intake.translateMotor(0);
        }
    }

    public boolean shooterMotorAuto(double targetSpeed){
        SmartDashboard.putNumber("shooterMotor targetSpeed", targetSpeed);
        double fxspd = shooterMotor.getSelectedSensorVelocity();
        difference  = Math.abs(targetSpeed) - Math.abs(fxspd);
        double error = difference*Kp;
        LastOutput = LastOutput + error;
        percentOutput = LastOutput;
        if(percentOutput > 1){
            percentOutput = 1;
        }
        if (targetSpeed == 0){
            percentOutput = 0;
            difference = 0;
            error = 0;
            LastOutput = 0;
        }  
        if(Math.abs(difference)<50){
            if(targetSpeed != 0){
                return true;   }             
        }
        else{return false;}   
        shooterMotor.set(ControlMode.PercentOutput, -percentOutput);
        return false;
    }


    public void shooterMotorLimelight(double targetSpeed, boolean limelightCentered){
        SmartDashboard.putNumber("shooterLimelight", targetSpeed);
        double fxspd = shooterMotor.getSelectedSensorVelocity();
        difference  = Math.abs(targetSpeed) - Math.abs(fxspd);
        double error = difference*Kp;
        LastOutput = LastOutput + error;
        percentOutput = LastOutput;
        if(percentOutput > 1){
            percentOutput = 1;
        }
        if (targetSpeed == 0){
            percentOutput = 0;
            difference = 0;
            error = 0;
            LastOutput = 0;
        }  
        if(Math.abs(difference)<50 && limelightCentered){
            if(targetSpeed != 0){
                shouldShoot=true;                
                intake.translateMotor(oi.translateRunSpeed);}
        }
        else{shouldShoot=false;}   
        shooterMotor.set(ControlMode.PercentOutput, -percentOutput);
        SmartDashboard.putBoolean("shouldShoot", shouldShoot);
    }

    public void hoodMotor(double hoodSpeed){
        if(hoodEncoder()<-50){
            hoodMotor.set(0);
        }
        if(hoodSpeed!=0){
            hoodMotor.set(hoodSpeed);
        }
        else{
            hoodMotor.set(0);
        }
        }

    public void hoodMotorRunToPos(double targetPos){
        if (hoodEncoder() < targetPos) {
            hoodMotor(1);
        }
        else {
            hoodMotor(0);
        }
    }

    public void hoodMotorRunToPosManual(double targetPos){
        double currentPos = hoodEncoder.getPosition();
        double difference = currentPos - targetPos;
        double k = 0.005;
        double sign = -1;
        double hoodSoftLimit = -50;
        if (targetPos < hoodSoftLimit || hoodEncoder() < hoodSoftLimit) {
            hoodMotor(0);
            targetPos = hoodSoftLimit;
        }
        else {
            hoodMotor(difference * k *  sign);
        }
        // SmartDashboard.putNumber("hoodSpeed", difference * k * sign);
        // SmartDashboard.putNumber("difference", difference);
    }

    public double hoodEncoder(){
        double hoodPos = hoodEncoder.getPosition();
        return hoodPos;
    }

    public void resetHoodEncoder(){
        hoodEncoder.setPosition(0);
    }
    
    public double talonFXSpeed(){
        return shooterMotor.getSelectedSensorVelocity();
    }
    
    public double shooterEquation(){ //x=31.6513 c=10628.3234
        double sofX = 30 * limelight.limeLightDistanceInches() + 10628.3234;
        return sofX;
    }

    public double hoodEquation(){
        double hofX = -0.2773 * limelight.limeLightDistanceInches() + 22.9709;
        return hofX;
    }

    // public void autoShoot() {
    //     if (oi.Xbox1.getAButton()) {
    //         hoodMotorRunToPos(hoodEquation());
    //         shooterConstantRev(shooterEquation());
    //     }
    //     else {
    //         hoodMotorRunToPos(oi.hoodAutomatedPos);
    //         shooterConstantRev(oi.shootAutomatedSpeed);
    //     }
    // }



    
}


