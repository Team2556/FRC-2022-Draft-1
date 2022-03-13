package frc.robot;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class Climber {
    OI oi = new OI();
    private DoubleSolenoid yellowLeft  = new DoubleSolenoid(Constants.PCMLPort, PneumaticsModuleType.CTREPCM, Constants.yLForwardChannel, Constants.yLReverseChannel);
    private DoubleSolenoid yellowRight = new DoubleSolenoid(Constants.PCMLPort, PneumaticsModuleType.CTREPCM, Constants.yRForwardChannel, Constants.yRReverseChannel);
    private DoubleSolenoid clampPiston = new DoubleSolenoid(Constants.PCMRPort, PneumaticsModuleType.CTREPCM, Constants.clampForwardChannel, Constants.clampReverseChannel); //double solenoid 4,5
    private TalonSRX winchMotor = new TalonSRX(Constants.winchMotorPort);
    private DutyCycleEncoder winchEncoder = new DutyCycleEncoder(Constants.winchEncoderPort);

    public void climbInit(){
        yellowLeft.set(Value.kReverse);
        yellowRight.set(Value.kReverse);
        clampPiston.set(Value.kReverse);
        winchMotor.configFactoryDefault();
        winchMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        winchMotor.setSelectedSensorPosition(0);
    }



}
