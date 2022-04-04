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

        intake.intakeSolenoid(oi.intakeSolenoid());

        if(oi.limeLightTurn()){
            shooterMotor(Math.abs(-13750));
                if(Math.abs(limelight.x) <= 5 && shouldShoot){ // runs with limelight turning and shoot
                    intake.translateMotor(oi.translateRunSpeed);
                }
                else
                {
                    intake.translateMotor(0);
                } 
        }
        else if(oi.Xbox1.getRightTriggerAxis() >=0.9 && intake.translateSwitch.get() == true){ //runs with intake
                intake.translateMotor(-0.1);
            }
        else{ // runs manually
                intake.translateMotor(oi.translateSpeed());
                shooterMotor(oi.targetSpeedManual());
            }
        // if(oi.limeLightTurn()){
        //     shooterMotor(Math.abs(-13250));
        // }
        // shooterMotor(Math.abs(oi.targetSpeedManual()));
        
        //hoodMotor(oi.hoodSpeed());


        // shooterMotor(oi.shootConfigs());
        //hoodMotor(oi.hoodConfigs());
        
        // if(oi.limeLightTurn()){
        //     shooterMotor(Math.abs(-13750));
        //   }
        
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
        if(Math.abs(difference)<50){
            if(targetSpeed != 0){
                shouldShoot=true;                
                intake.translateMotor(oi.translateRunSpeed);}
        }
        else{shouldShoot=false;}   
        shooterMotor.set(ControlMode.PercentOutput, -percentOutput);
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

    public double hoodEncoder(){
        double hoodPos = hoodMotor.getSelectedSensorPosition();
        return hoodPos;
    }
    
    public double talonFXSpeed(){
        return shooterMotor.getSelectedSensorVelocity();
    }
    
    
    






    
}


