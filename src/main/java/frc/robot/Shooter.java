package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    OI oi = new OI();
    private TalonFX shooterMotor = new TalonFX(7);
    private TalonSRX hoodMotor = new TalonSRX(8);

    public void shooterMotor(){
        //shooter motor runs as of 3/7/22
        shooterMotor.set(ControlMode.PercentOutput, -oi.shooterSpeed());
        
    }

    public void hoodMotor(){
        //hood motor runs as of 3/7/22
        hoodMotor.set(ControlMode.PercentOutput, oi.hoodSpeed());
            
        }
    
    
    
    
    }


