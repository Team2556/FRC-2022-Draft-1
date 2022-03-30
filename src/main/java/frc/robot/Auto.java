package frc.robot;

public class Auto {
    Drive drive;
    Limelight limelight;
    Intake intake;
    Shooter shooter;
    // CargoVision cargoVision;
    int step = 0;

    public Auto(Drive drv, Intake in, Shooter shot, Limelight lim, CargoVision cVis) {
        drive = drv;
        intake = in;
        shooter = shot;
        limelight = lim;
        // cargoVision = cVis;
    }
   
    public void autoInit(int alliance) {
        // cargoVision.visionInit(alliance);
        step = 0;
        drive.lFEncoder.setPosition(0);
        // drive.dualDrivebase(false);
    }

    public void autoEncoder() {
        double encoderDistance1 = 32;
        double shooterSpeed = Math.abs(-14500);
        switch(step) {
            case 0:
                shooter.shooterMotor(shooterSpeed);
                if (drive.lFEncoder.getPosition() < encoderDistance1){
                    drive.mecanumDrive(0.2, 0, 0);
                }
                else if (drive.lFEncoder.getPosition() >= encoderDistance1){
                    step = 1;
                    drive.mecanumDrive(0, 0, 0);
                }
                intake.intakeSolenoid(false);
                intake.intakeMotor(-0.8);
                intake.translateMotor(0);
            break;
            case 1: 
                drive.mecanumDrive(0, 0, 0);
                intake.intakeMotor(-0.8);
                intake.translateMotor(-0.5);
                shooter.shooterMotor(shooterSpeed);
            break;
        }

           
    }


    // public void autoEncoderV2() {
    //     double encoderDistance1 = 32;
    //     double shooterSpeed = Math.abs(-14500);
    //     SmartDashboard.putNumber("autoEncoder step", step);
    //     switch(step) {
    //         case 0:
    //             shooter.shooterMotor(shooterSpeed);
    //             if (drive.lFEncoder.getPosition() < encoderDistance1){//currentDistance < targetDistance || drive.lFEncoder.getPosition() > encoderSafetyVal){
    //                 drive.mecanumDrive(0.2, 0, 0);
    //             }
    //             else if (drive.lFEncoder.getPosition() >= encoderDistance1){//currentDistance >= targetDistance && drive.lFEncoder.getPosition() <= encoderSafetyVal){
    //                 step = 1;
    //                 drive.mecanumDrive(0, 0, 0);
    //             }
    //             intake.intakeSolenoid(false);
    //             intake.intakeMotor(-0.8);
    //             intake.translateMotor(0);
    //         break;
    //         case 1: 
    //             drive.mecanumDrive(0, 0, 0);
    //             intake.intakeMotor(-0.8);
    //             intake.translateMotor(-0.5);
    //             shooter.shooterMotor(shooterSpeed);
    //         break;
    //     }

           
    // }


    
    public void autoLimelight() {
        //go forwards and pick up ball, then shoot both balls.
        double currentDistance = limelight.limeLightDistanceInches();
        double targetDistance = 135;
        double targetDistance2 = 120;
        double encoderSafetyVal = -20;
        double encoderDistance1 = 32
        ;
        double shooterSpeed = Math.abs(-14250); //0
        // boolean twoBallsPickedUp = false;
        // boolean oneBallPickedUp = false;
        // double shooterDeadzone = 100;
        switch(step) {
            case 0:
                shooter.shooterMotor(shooterSpeed);
               if( currentDistance < targetDistance || drive.lFEncoder.getPosition() > encoderSafetyVal){
                //if (drive.lFEncoder.getPosition() < encoderDistance1){//currentDistance < targetDistance || drive.lFEncoder.getPosition() > encoderSafetyVal){
                    drive.mecanumDrive(0.2, 0, 0);
                }
                else if(currentDistance >= targetDistance && drive.lFEncoder.getPosition() <= encoderSafetyVal){
                //else if (drive.lFEncoder.getPosition() >= encoderDistance1){//currentDistance >= targetDistance && drive.lFEncoder.getPosition() <= encoderSafetyVal){
                    step = 1;
                    drive.mecanumDrive(0, 0, 0);
                }
      
                intake.intakeSolenoid(false);
                intake.intakeMotor(-0.8);
                intake.translateMotor(0);
            break;
            case 1: 
                if (currentDistance > targetDistance2){
                    drive.mecanumDrive(-0.1, 0, 0);
                }
                else{
                    drive.mecanumDrive(0, 0, 0);
                }
                intake.intakeMotor(-0.75);
                intake.translateMotor(0);
                shooter.shooterMotor(shooterSpeed);
                // SmartDashboard.putNumber("Target Speed", Math.abs(shooterSpeed));
                // SmartDashboard.putNumber("Current Speed", Math.abs(shooter.talonFXSpeed()));
                if (Math.abs(shooterSpeed) -50 <= Math.abs(shooter.talonFXSpeed()) && currentDistance <= targetDistance2){
                    step = 2;
                }
                step = 2;
            break;
            case 2:
                drive.mecanumDrive(0, 0, 0);
                intake.intakeMotor(0);
                intake.translateMotor(-0.5);
                shooter.shooterMotor(shooterSpeed);
            break;
        }

           
    }
}
