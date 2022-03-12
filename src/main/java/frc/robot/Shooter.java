package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    OI oi = new OI();
    
    private TalonFX shooterMotor = new TalonFX(Constants.shooterMotorPort);
    private TalonSRX hoodMotor = new TalonSRX(Constants.hoodMotorPort);



    public void shooterInit(){
        shooterMotor.configFactoryDefault();
        shooterMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
    }
    public void shooterMotor(){
        //shooter motor runs as of 3/7/22
        double motorSpeed = -oi.shooterSpeed();
        //double motorSpeed = oi.Xbox1.getLeftY();
        shooterMotor.set(ControlMode.PercentOutput, motorSpeed);
        double fxspd = shooterMotor.getSelectedSensorVelocity();
        SmartDashboard.putNumber("FX speed", fxspd);
    }

    public void hoodMotor(){
        //hood motor runs as of 3/7/22
        hoodMotor.set(ControlMode.PercentOutput, oi.hoodSpeed());
            
        }
    
    
    
    
    }


