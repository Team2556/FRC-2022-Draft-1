package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Shooter {
    OI oi = new OI();
    private TalonFX shooterMotor = new TalonFX(6);


    public void shooterMotor(){

        shooterMotor.set(ControlMode.PercentOutput, oi.shooterSpeed());
    }




}
