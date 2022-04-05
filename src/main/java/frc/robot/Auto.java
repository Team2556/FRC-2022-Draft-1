package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {
    Drive drive;
    Limelight limelight;
    Intake intake;
    Shooter shooter;
    // CargoVision cargoVision;
    // int step = 0;
    int stepv2 = 0;
    int stepE = 0;

    public Auto(Drive drv, Intake in, Shooter shot, Limelight lim, CargoVision cVis) {
        drive = drv;
        intake = in;
        shooter = shot;
        limelight = lim;
        // cargoVision = cVis;
    }
   
    public void autoInit(int alliance) {
        // cargoVision.visionInit(alliance);
        // step = 0;
        drive.lFEncoder.setPosition(0);
        stepv2 = 0;
        // drive.dualDrivebase(false);
    }

    public void autoEncoder() {
        double encoderDistance1 = 32;
        double shooterSpeed = Math.abs(-14500);
        switch(stepE) {
            case 0:
                shooter.shooterMotor(shooterSpeed);
                if (drive.lFEncoder.getPosition() < encoderDistance1){
                    drive.mecanumDrive(0.2, 0, 0);
                }
                else if (drive.lFEncoder.getPosition() >= encoderDistance1){
                    stepE = 1;
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


    
    // public void autoLimelight() {
    //     //go forwards and pick up ball, then shoot both balls.
    //     double currentDistance = limelight.limeLightDistanceInches();
    //     double targetDistance = 135;
    //     double targetDistance2 = 120;
    //     double encoderSafetyVal = -20;
    //     double encoderDistance1 = 32
    //     ;
    //     double shooterSpeed = Math.abs(-14250); //0
    //     // boolean twoBallsPickedUp = false;
    //     // boolean oneBallPickedUp = false;
    //     // double shooterDeadzone = 100;
    //     switch(step) {
    //         case 0:
    //             shooter.shooterMotor(shooterSpeed);
    //            if( currentDistance < targetDistance || drive.lFEncoder.getPosition() > encoderSafetyVal){
    //             //if (drive.lFEncoder.getPosition() < encoderDistance1){//currentDistance < targetDistance || drive.lFEncoder.getPosition() > encoderSafetyVal){
    //                 drive.mecanumDrive(0.2, 0, 0);
    //             }
    //             else if(currentDistance >= targetDistance && drive.lFEncoder.getPosition() <= encoderSafetyVal){
    //             //else if (drive.lFEncoder.getPosition() >= encoderDistance1){//currentDistance >= targetDistance && drive.lFEncoder.getPosition() <= encoderSafetyVal){
    //                 step = 1;
    //                 drive.mecanumDrive(0, 0, 0);
    //             }
      
    //             intake.intakeSolenoid(false);
    //             intake.intakeMotor(-0.8);
    //             intake.translateMotor(0);
    //         break;
    //         case 1: 
    //             if (currentDistance > targetDistance2){
    //                 drive.mecanumDrive(-0.1, 0, 0);
    //             }
    //             else{
    //                 drive.mecanumDrive(0, 0, 0);
    //             }
    //             intake.intakeMotor(-0.75);
    //             intake.translateMotor(0);
    //             shooter.shooterMotor(shooterSpeed);
    //             // SmartDashboard.putNumber("Target Speed", Math.abs(shooterSpeed));
    //             // SmartDashboard.putNumber("Current Speed", Math.abs(shooter.talonFXSpeed()));
    //             if (Math.abs(shooterSpeed) -50 <= Math.abs(shooter.talonFXSpeed()) && currentDistance <= targetDistance2){
    //                 step = 2;
    //             }
    //             step = 2;
    //         break;
    //         case 2:
    //             drive.mecanumDrive(0, 0, 0);
    //             intake.intakeMotor(0);
    //             intake.translateMotor(-0.5);
    //             shooter.shooterMotor(shooterSpeed);
    //         break;
    //     }

           
    // }

    public void autoLimelightV2() {
        //go forwards and pick up ball, then shoot both balls.
        double currentDistance = limelight.limeLightDistanceInches();
        double targetDistance = 106;
        double targetDistance2 = 90;
        double encoderSafetyVal = 30;
        double encoderDistance1 = 32;
        double shooterSpeed = Math.abs(-14250); //0
        SmartDashboard.putNumber("step", stepv2);
        SmartDashboard.putNumber("currentDistance", currentDistance);
        SmartDashboard.putNumber("lfEncoderAuto", drive.lFEncoder.getPosition());
        // boolean twoBallsPickedUp = false;
        // boolean oneBallPickedUp = false;
        // double shooterDeadzone = 100;
        switch(stepv2) {
            case 0:
                intake.intakeSolenoid(false);
                intake.intakeMotor(-1);
                intake.translateMotor(0);
                shooter.shooterMotor(shooterSpeed);
               if( currentDistance < targetDistance || drive.lFEncoder.getPosition() < encoderSafetyVal){
                //if (drive.lFEncoder.getPosition() < encoderDistance1){//currentDistance < targetDistance || drive.lFEncoder.getPosition() > encoderSafetyVal){
                    drive.differentialDrive.arcadeDrive(0.4, 0);
                    intake.intakeMotor(-1);
                }
                else if(currentDistance >= targetDistance && drive.lFEncoder.getPosition() >= encoderSafetyVal){
                //else if (drive.lFEncoder.getPosition() >= encoderDistance1){//currentDistance >= targetDistance && drive.lFEncoder.getPosition() <= encoderSafetyVal){
                    stepv2 = 1;
                    drive.differentialDrive.arcadeDrive(0, 0);
                }
            break;
            case 1: 
                if (currentDistance > targetDistance2){
                    drive.differentialDrive.arcadeDrive(-0.1, 0);
                }
                else{
                    drive.differentialDrive.arcadeDrive(0, 0);
                }
                intake.intakeMotor(-0.5);
                intake.translateMotor(0);
                shooter.shooterMotor(shooterSpeed);
                // SmartDashboard.putNumber("Target Speed", Math.abs(shooterSpeed));
                // SmartDashboard.putNumber("Current Speed", Math.abs(shooter.talonFXSpeed()));
                if (Math.abs(shooterSpeed) -50 <= Math.abs(shooter.talonFXSpeed()) && currentDistance <= targetDistance2){
                    //stepv2 = 2;
                }
            break;
            case 2:
                drive.mecanumDrive(0, 0, 0);
                intake.intakeMotor(0);
                intake.translateMotor(-0.5);
                shooter.shooterMotor(shooterSpeed);
            break;
            default:
                //stepv2++;
            break;
        }

           
    }


    int autoStepComplex = 0;
    public void complexAutoDraft(){
        double tarmacDistanceLimelight = 0;
        double encoderSafetyVal = 0;
        double shooterSpeed = 0;
        double forward = -0.2;
        double backward = -forward;
        double currentDistance = limelight.limeLightDistanceInches();
        boolean intakeUp = true;
        boolean intakeDown = !intakeUp;
        double intakeSpeed = -0.8;
        double translateSpeed = -0.5;
        boolean switched = false;
        boolean notSwitched = !switched;
        switch(autoStepComplex){
            case 0:
                drive.differentialDrive.arcadeDrive(forward, 0);
                intake.intakeSolenoid(intakeDown);
                intake.intakeMotor(intakeSpeed);
                if(currentDistance > tarmacDistanceLimelight && drive.lFEncoder.getPosition() >= encoderSafetyVal){
                    autoStepComplex = 1;
                    drive.differentialDrive.arcadeDrive(0, 0);
                }
            break;
            case 1:
                drive.differentialDrive.arcadeDrive(0, limelight.limeLightTurn());
                shooter.shooterMotor(Math.abs(shooterSpeed));
                    if(Math.abs(limelight.x) <= 5 && shooter.shouldShoot){ 
                        intake.translateMotor(translateSpeed);
                    }
                    else
                    {
                        intake.translateMotor(0);
                    } 
                

                    //limit switch returns true if no ball
            break;
        }

    }







}

