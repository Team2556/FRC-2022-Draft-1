package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Auto {
    Drive drive = new Drive();
    Limelight limelight = new Limelight();
    Intake intake = new Intake();
    Shooter shooter = new Shooter();

    





        int step = 0;
    public void autoDraft1(){
        //go forwards and pick up ball, then shoot both balls. 
        double currentDistance = 0;
        double targetDistance = 0;
        double shooterSpeed = 0; //Math.abs(-13000)
        boolean twoBallsPickedUp = false;
        boolean oneBallPickedUp = false;
        double shooterDeadzone = 100;
        switch(step){
            case 0:
                if (currentDistance < targetDistance){
                    drive.driveMecanum.driveCartesian(-0.5, 0, 0);
                }
                else if (currentDistance >= targetDistance && twoBallsPickedUp)
                {
                    step = 1;
                }
                intake.intakeSolenoid.set(Value.kForward);
                intake.intake.set(0.1);
            break;
            case 1:
                
                shooter.shooterMotor(shooterSpeed);
                if(Math.abs(shooterSpeed) > Math.abs(shooter.talonFXSpeed()) - shooterDeadzone || Math.abs(shooterSpeed) < Math.abs(shooter.talonFXSpeed()) + shooterDeadzone)
                {
                    if(oneBallPickedUp || twoBallsPickedUp){
                        intake.translate.set(0.1);
                    }
                    else{
                        step = 2;
                    }
                }
            break;

        }
      


    }



















}
