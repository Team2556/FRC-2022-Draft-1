package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Shooter {
    OI oi = new OI();
    Limelight limelight = new Limelight();
    Intake intake; 
    private TalonFX shooterMotor;
    private TalonSRX hoodMotor;
    private double percentOutput;
    private double LastOutput; 
    private double Kp;
    double difference = 0;

    public Shooter(Intake in){
        shooterMotor = new TalonFX(Constants.shooterMotorPort);
        hoodMotor = new TalonSRX(Constants.hoodMotorPort);
        percentOutput = 0;
        LastOutput = 0;
        Kp = 0.00000105;
        intake = in;
        
    }

    public void shooterInit(){
        hoodMotor.configFactoryDefault();
        hoodMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        hoodMotor.setSelectedSensorPosition(0);
        shooterMotor.configFactoryDefault();
        shooterMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
        shooterMotor.setNeutralMode(NeutralMode.Coast);
    }

    public void shooterIntakeTeleop(){
        SmartDashboard.putNumber("talonfX", shooterMotor.getSelectedSensorVelocity());
        oi.shooterTeleopConfigSwitch();
        intake.intakeSolenoid(oi.intakeSolenoid());
        intake.intakeMotor(oi.intakeSpeed());
        hoodMotorRunToPosManual(oi.hoodConfigs());
        if (oi.limeLightTurn()) {
            shooterMotorLimelight(oi.shootConfigsNoCheck(), limelight.limelightCentered());
        }
        else if (intake.translateSwitch.get() == true) { //runs with intake
            intake.translateMotor(-0.2);
            shooterMotor(0);
        }
        else if (intake.translateSwitch.get() == false) {
            intake.translateMotor(0);
            shooterMotor(oi.shootAutomatedSpeed + 1000);   
        }
        else { // runs manually
            intake.translateMotor(oi.translateSpeed());
            if (intake.translateSwitch.get() == false) {
                intake.translateMotor(0);
                shooterMotor(oi.shootAutomatedSpeed + 1000);
            }
            else{
                shooterMotor(0);
            }
        }
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
           
        shooterMotor.set(ControlMode.PercentOutput, -percentOutput);
    }
   
    public void runIntakeByDiff(double targetSpeed){
        difference  = Math.abs(targetSpeed) - Math.abs(shooterMotor.getSelectedSensorVelocity());
        if(Math.abs(difference)<50){
            if(targetSpeed != 0){
                shouldShoot=true;                
                intake.translateMotor(oi.translateRunSpeed);
            }
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
        if(hoodSpeed!=0){
        hoodMotor.set(ControlMode.PercentOutput, hoodSpeed);
        }
        else{
            hoodMotor.set(ControlMode.PercentOutput, 0);
        }
        }

    public void hoodMotorRunToPos(double targetPos){
        hoodMotor.set(ControlMode.Position, targetPos);
    }

    public void hoodMotorRunToPosManual(double targetPos){
        double currentPos = hoodMotor.getSelectedSensorPosition();
        double difference = currentPos - targetPos;
        double k = 0.001;
        double sign = -1;
        hoodMotor(difference * k *  sign);
        // SmartDashboard.putNumber("hoodSpeed", difference * k * sign);
        // SmartDashboard.putNumber("difference", difference);
    }

    public double hoodEncoder(){
        double hoodPos = hoodMotor.getSelectedSensorPosition();
        return hoodPos;
    }
    
    public double talonFXSpeed(){
        return shooterMotor.getSelectedSensorVelocity();
    }
    
    public double shooterEquation(){
        double sofX = -41.00625 * limelight.limeLightDistanceInches() - 10406.56305;
        return sofX;
    }

    public double hoodEquation(){
        double hofX = 19.63731 * limelight.limeLightDistanceInches() - 1787.57969;
        return hofX;
    }





    
}


