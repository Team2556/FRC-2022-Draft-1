package frc.robot;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {
    Drive drive;
    Limelight limelight;
    Intake intake;
    Shooter shooter;
    CargoVision cargoVision;
    // int step = 0;
    //int stepv2 = 0;
    int stepv3 = 0;
    int stepv4 = 0;
    int stepE = 0;

    public Auto(Drive drv, Intake in, Shooter shot, Limelight lim, CargoVision cVis) {
        drive = drv;
        intake = in;
        shooter = shot;
        limelight = lim;
        cargoVision = cVis;
    }
   
    public void autoInit(int alliance) {
        cargoVision.visionInit(alliance);
        // step = 0;
        drive.lFEncoder.setPosition(0);
        // stepv2 = 0;
        stepv3 = 0;
        stepv4 = 0;
        shooter.revvedCounter = false;
        shooter.shouldShoot = false;
        // drive.dualDrivebase(false);
    }

    public void autoEncoder() {
        double encoderDistance1 = 32;
        double shooterSpeed = Math.abs(-14500);
        switch(stepE) {
            case 0:
                shooter.shooterMotor(shooterSpeed);
                // if (drive.lFEncoder.getPosition() < encoderDistance1){
                //     // drive.mecanumDrive(0.2, 0, 0);
                // }
                // else if (drive.lFEncoder.getPosition() >= encoderDistance1){
                //     stepE = 1;
                //     // drive.mecanumDrive(0, 0, 0);
                // }
                intake.intakeSolenoid(false);
                intake.intakeMotor(-0.8);
                intake.translateMotor(0);
            break;
            case 1: 
                // drive.mecanumDrive(0, 0, 0);
                intake.intakeMotor(-0.8);
                intake.translateMotor(-0.5);
                shooter.shooterMotor(shooterSpeed);
            break;
        }

           
    }




   
    public void autoLimelightV3() {
        //go forwards and pick up ball, then shoot both balls.
        double currentDistance = limelight.limeLightDistanceInches();
        double targetDistance = 106;
        double targetDistance2 = 90;
        double encoderSafetyVal = 30;
        double encoderDistance1 = 32;
        double shooterSpeed = Math.abs(-14250); //0
        double errorL = limelight.tx.getDouble(0.0);
        SmartDashboard.putNumber("step", stepv3);
        SmartDashboard.putNumber("error", errorL);
        shooter.shooterCounter();
        // SmartDashboard.putNumber("currentDistance", currentDistance);
        // SmartDashboard.putNumber("lfEncoderAuto", drive.lFEncoder.getPosition());
        switch(stepv3) {
            case 0:
                //shooter.hoodMotor(0.1);
                shooter.hoodResetBySwitch();
                intake.intakeSolenoid(false);
                intake.intakeMotor(-1);
                intake.translateMotor(0);
               shooter.shooterMotor(shooterSpeed);
               if( currentDistance < targetDistance || drive.lFEncoder.getPosition() < encoderSafetyVal){
                //if (drive.lFEncoder.getPosition() < encoderDistance1){//currentDistance < targetDistance || drive.lFEncoder.getPosition() > encoderSafetyVal){
                    drive.driveMecanum.driveCartesian(0.2,0, 0);
                    intake.intakeMotor(-1);
                }
                else if(currentDistance >= targetDistance && drive.lFEncoder.getPosition() >= encoderSafetyVal){
                //else if (drive.lFEncoder.getPosition() >= encoderDistance1){//currentDistance >= targetDistance && drive.lFEncoder.getPosition() <= encoderSafetyVal){
                    stepv3 = 1;
                    drive.driveMecanum.driveCartesian(0,0, 0);
                }
            break;
            case 1: 
            drive.driveMecanum.driveCartesian(0, 0, 0);
            stepv3 = 2;
            break;
            case 2: 
                intake.intakeMotor(-0.5);
                drive.driveMecanum.driveCartesian(0, 0, limelight.limeLightTurn());
               if (Math.abs(errorL) <= 2.5){
                    stepv3 = 3;
                }
            break;
            case 3:
                shooter.runIntakeByDiff(shooterSpeed);
                shooter.shooterMotor(shooterSpeed);
                // drive.mecanumDrive(0, 0, 0);
            break;
            default:
                //stepv2++;
            break;
        }

           
    }

    public void autoLimelightV4() {
        //go forwards and pick up ball, then shoot both balls.
        double currentDistance = limelight.limeLightDistanceInches();
        double targetDistance = 106;
        double encoderSafetyVal = 30;
        double shooterSpeed = Math.abs(-14250); //0
        double errorL = limelight.tx.getDouble(0.0);
        SmartDashboard.putNumber("step4", stepv4);
        SmartDashboard.putNumber("error", errorL);
        shooter.shooterCounter();
       
        switch(stepv4) {
            case 0:
                shooter.hoodResetBySwitch();
                intake.intakeSolenoid(false);
                intake.intakeMotor(-1);
                intake.translateMotor(0);
               shooter.shooterMotor(shooterSpeed);
               shooter.timesShot = 0;
               if( currentDistance < targetDistance || drive.lFEncoder.getPosition() < encoderSafetyVal){
                    drive.driveMecanum.driveCartesian(0.2,0, 0);
                    intake.intakeMotor(-1);
                }
                else if(currentDistance >= targetDistance && drive.lFEncoder.getPosition() >= encoderSafetyVal){
                    stepv4 = 1;
                    drive.driveMecanum.driveCartesian(0,0, 0);
                }
            break;
            case 1: 
            drive.driveMecanum.driveCartesian(0, 0, 0);
            stepv4 = 2;
            break;
            case 2: 
                intake.intakeMotor(-0.5);
                drive.driveMecanum.driveCartesian(0, 0, limelight.limeLightTurn());
               if (Math.abs(errorL) <= 2.5){
                    stepv4 = 3;
                }
                shooter.timesShot = 0;
            break;
            case 3:
                shooter.runIntakeByDiff(shooterSpeed);
                shooter.shooterMotor(shooterSpeed);
            //for(int i = 0; i < 50000; i++){
                if(shooter.shooterCounter() == 2 && shooter.shouldShoot == false){
                    stepv4 = 4;
                    drive.rFEncoder.setPosition(0);
                }
            // }
            // stepv4 = 4;
            break;
            case 4:
                intake.translateMotor(-0.2);
                drive.rFEncoder.setPosition(0);
                for(int i = 0; i<5000;i++){
                    intake.translateMotor(-0.2);
                    drive.driveMecanum.driveCartesian(0,0,0);
                }
                stepv4 = 5;
            break;
            case 5:
                shooter.runIntakeByDiff(shooterSpeed);
                SmartDashboard.putNumber("rFEncoder", drive.rFEncoder.getPosition());
                if(drive.rFEncoder.getPosition() < 6){
                    drive.driveMecanum.driveCartesian(0, 0, -0.15);
                }
                else{
                    stepv4 = 6;
                    drive.lFEncoder.setPosition(0);
                }
                intake.intakeSolenoid(false);
            break;
            case 6:
                drive.driveMecanum.driveCartesian(0.2, 0, cargoVision.getRotationValue());
                SmartDashboard.putNumber("lfEncoder", drive.lFEncoder.getPosition());
                SmartDashboard.putNumber("limelightdistance", limelight.limeLightDistanceInches());
                if(intake.translateSwitch.get()){
                    intake.translateMotor(-0.4);
                }
                if(drive.lFEncoder.getPosition() >= 80){
                    if(!intake.translateSwitch.get() || limelight.limeLightDistanceInches() >=245)
                    {
                        stepv4 = 7;
                    }
                }
                shooter.hoodMotorRunToPosManual(shooter.hoodEquation());
            break;
            case 7:
            shooter.shooterMotor(shooter.shooterEquation());
            drive.driveMecanum.driveCartesian(0, 0, limelight.limeLightTurn() * 0.5);
               if (Math.abs(errorL) <= 2.5){
                    drive.lFEncoder.setPosition(0);
                    drive.driveMecanum.driveCartesian(0, 0,0);
                    stepv4 = 8;
                }
            break;
            case 8:
                if(drive.lFEncoder.getPosition()>= -5){
                    drive.driveMecanum.driveCartesian(-0.2,0,0);
                }
                else
                {
                    stepv4 = 9;
                }
            break;
            case 9:
            drive.driveMecanum.driveCartesian(0, 0, limelight.limeLightTurn() * 0.5);
            if (Math.abs(errorL) <= 2.5){
                
                 stepv4 = 10;
             }
            break;
            case 10:
                shooter.shooterMotor(shooter.shooterEquation());
                shooter.hoodMotorRunToPosManual(shooter.hoodEquation()+3);
                drive.driveMecanum.driveCartesian(0, 0, 0);
                if(Math.abs(shooter.hoodEquation()) <10 ){
                shooter.runIntakeByDiff(shooter.shooterEquation());
                }//intake.intakeSolenoid(true);
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
                // drive.differentialDrive.arcadeDrive(forward, 0);
                intake.intakeSolenoid(intakeDown);
                intake.intakeMotor(intakeSpeed);
                // if(currentDistance > tarmacDistanceLimelight && drive.lFEncoder.getPosition() >= encoderSafetyVal){
                //     autoStepComplex = 1;
                //     // drive.differentialDrive.arcadeDrive(0, 0);
                // }
            break;
            case 1:
                // drive.differentialDrive.arcadeDrive(0, limelight.limeLightTurn());
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

