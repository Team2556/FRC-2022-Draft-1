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
    
    private TalonFX shooterMotor;
    private TalonSRX hoodMotor;
    private double percentOutput;
    private double LastOutput; 
    private double Kp;

    public Shooter(){
        shooterMotor = new TalonFX(Constants.shooterMotorPort);
        hoodMotor = new TalonSRX(Constants.hoodMotorPort);
        percentOutput = 0;
        LastOutput = 0;
        Kp = 0.000002;
    }

    public void shooterInit(){
        hoodMotor.configFactoryDefault();
        hoodMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        hoodMotor.setSelectedSensorPosition(0);
        shooterMotor.configFactoryDefault();
        shooterMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
    }

    public void shooterTeleop(){
        shooterMotor(Math.abs(oi.targetSpeed()));
        hoodMotor(oi.hoodSpeed());
    }


    
    public void shooterMotor(double targetSpeed){
        shooterMotor.setNeutralMode(NeutralMode.Coast);
        double fxspd = shooterMotor.getSelectedSensorVelocity();
        double difference  = targetSpeed - Math.abs(fxspd);
        double error = difference*Kp;
        LastOutput = LastOutput + error;
        percentOutput = LastOutput;
        shooterMotor.set(ControlMode.PercentOutput, -percentOutput);
   
        // SmartDashboard.putNumber("difference", difference);
        // SmartDashboard.putNumber("FX speed", fxspd);
        // SmartDashboard.putNumber("oitargetSpeed", oi.targetSpeed());
        // SmartDashboard.putNumber("percentOutput", percentOutput);
        // SmartDashboard.putNumber("error", error);
    }

    public void hoodMotor(double hoodSpeed){
        hoodMotor.set(ControlMode.PercentOutput, hoodSpeed);
        }

    public double hoodEncoder(){
        double hoodPos = hoodMotor.getSelectedSensorPosition();
        SmartDashboard.putNumber("HoodPosition", hoodPos);
        return hoodPos;
    }
    
    
    
    
}


