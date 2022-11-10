package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase {
    private static Elevator instance;

    private DoubleSolenoid yellowLeft  = new DoubleSolenoid(Constants.PCMLPort, PneumaticsModuleType.CTREPCM, Constants.yLForwardChannel, Constants.yLReverseChannel);
    private DoubleSolenoid yellowRight = new DoubleSolenoid(Constants.PCMLPort, PneumaticsModuleType.CTREPCM, Constants.yRForwardChannel, Constants.yRReverseChannel);
    private DoubleSolenoid clampPiston = new DoubleSolenoid(Constants.PCMRPort, PneumaticsModuleType.CTREPCM, Constants.clampForwardChannel, Constants.clampReverseChannel); //double solenoid 4,5
    private CANSparkMax yellowMotor = new CANSparkMax(Constants.yellowMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
    RelativeEncoder yellowEncoder = yellowMotor.getEncoder();
    SparkMaxPIDController yellowPID = yellowMotor.getPIDController();
    DigitalInput topWinchSwitch = new DigitalInput(7);

    Elevator() {
        clampPistonRelease();
        winchPistonsDown();
        yellowMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        yellowPID.setP(0.05);
        yellowPID.setI(0);
        yellowPID.setD(0.0005);
        yellowPID.setIZone(0);
        yellowPID.setFF(0);
        yellowPID.setOutputRange(-1,1);
    }

    public void setYellowMotorSpeed(double speed) {
//        yellowMotor.set((topWinchSwitch.get() || speed < 0) ? speed : 0);
        yellowMotor.set(speed);
    }

    public void clampPistonHold() {
        clampPiston.set(DoubleSolenoid.Value.kForward);
    }

    public void clampPistonRelease() {
        clampPiston.set(DoubleSolenoid.Value.kReverse);
    }

    public void winchPistonsUp() {
        yellowLeft.set(DoubleSolenoid.Value.kForward);
        yellowRight.set(DoubleSolenoid.Value.kForward);
    }

    public void winchPistonsDown() {
        yellowLeft.set(DoubleSolenoid.Value.kReverse);
        yellowRight.set(DoubleSolenoid.Value.kReverse);
    }

    public static Elevator getInstance() {
        if(instance == null)
            instance = new Elevator();
        return instance;
    }
}
