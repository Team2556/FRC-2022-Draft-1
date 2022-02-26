package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber {
    Drive drive = new Drive();
    OI oi = new OI();

    private DoubleSolenoid winchPistons = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0,1);
    private TalonSRX winchMotor = new TalonSRX(5);







    public void winchMotor(){
        winchMotor.set(ControlMode.PercentOutput, oi.winchSpeed());
    }

    public void winchPistons(){
        boolean winchUp = oi.winchUp();
        if (winchPistons.get() == Value.kForward){
            SmartDashboard.putBoolean("Winch Pistons Down", true);
        }
        else if (winchPistons.get() == Value.kReverse){
            SmartDashboard.putBoolean("Winch Pistons Down", false);
        }

        
        if (winchUp){
            winchPistons.set(Value.kForward);
            }
        else {
            winchPistons.set(Value.kReverse);
            }
        

        SmartDashboard.putBoolean("winchUp", winchUp);
    }

}
