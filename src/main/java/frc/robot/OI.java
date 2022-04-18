package frc.robot;
//import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
    
    
    //Xbox object
    XboxController Xbox1 = new XboxController(0);
    XboxController Xbox2 = new XboxController(1);

    double deadzone = 0.2; //originally 0.2
    
    double shootAutomatedSpeed = 0;
    double hoodAutomatedPos = 0;
    boolean shootNow = false;
    

   
   
   
   
   
   
   
   
   
    //drive piston drops
    boolean dropped = false;
    boolean dropped(){
        if(Xbox1.getBButtonReleased()){
            dropped = !dropped;
        }
        return dropped;
    }
    //mecanum drive values
    // double mForwardSpeed = 0;
    // double mForward(){
    //     if(Math.abs(Xbox1.getLeftY()) > 0.0000001 ){
    //         mForwardSpeed = Xbox1.getLeftY();
    //     }
    //     else{
    //         mForwardSpeed = 0;
    //     }
    //     return mForwardSpeed;
        
    // }
    // double mStrafeSpeed = 0;
    // double mStrafe(){
    //     return Xbox1.getLeftX();
    // }
    // double mRotateSpeed = 0;
    // double mRotate(){
    //     return Xbox1.getRightX();
    // }
    //arcade drive values
    // double aForward(){
    //     return Xbox1.getLeftY();
    // }
    // double aRotate(){
    //     return Xbox1.getRightX();
    // }

    public double getForward() {
        return -Xbox1.getLeftY();
    }
    public double getStrafe() {
        return Xbox1.getLeftX();
    }
    public double getTurn() {
        return Xbox1.getRightX();
    }

    boolean climb = Xbox2.getBButton();
















    double winchSpeed(){
        if(Xbox2.getRightTriggerAxis() >=0.5){
            return -0.5;
        }
        else if(Xbox2.getLeftTriggerAxis() >=0.5){
            return 1;
        }
        else{
            return 0;
        }
        // return 0;
    }
    boolean winchUp = false;
    boolean winchUp(){
       
        if(Xbox1.getXButtonReleased()){
            winchUp = !winchUp;
        }
        return winchUp;
    }
    boolean clampOut = false;
    boolean clampOut(){
        if(Xbox1.getAButtonReleased()){
            clampOut = !clampOut;
        }
        return clampOut;
    }

    double yellowMotorSpeed(){
        if(Xbox2.getPOV() == 180)
        {
            return 1;
        }
        else if (Xbox2.getPOV() == 0)
        {
            return -1;
        }
        else{
            return 0; 
        }
    }
    boolean climbStepBool1 = false;
    boolean climbStepBool2 = false;
    boolean climbStepBool3 = false;
    public boolean climbStep(){
        return Xbox2.getBButtonReleased();
    }

    boolean winchPosBool = false;
    double winchPos(){
        if(Xbox2.getBButtonReleased()){
            winchPosBool = !winchPosBool;
        }
        if(winchPosBool){
            return -80;
        }
        else{
            return 0;
        }
    }

    boolean resetClimber(){
        return Xbox2.getPOV()==180;
    }
   
   
   
   
   
   
   
   
    double intakeRunSpeed = -0.9;
    double intakeSpeed(){
        if(Xbox1.getRightTriggerAxis() >=0.9){
            return intakeRunSpeed;
        }
        else if(Xbox1.getYButton()) {
            return 0.5;
        }
        else {
            return 0;
        }
      }

    double translateRunSpeed = -0.4;
    double translateSpeed(){
        if(Xbox2.getLeftBumper()){
            return translateRunSpeed;
        }  
        else{
            return 0;
        }
      //  return 0;
    }
    boolean intakeOut = true;
    boolean intakeSolenoid(){
        if(Xbox1.getRightTriggerAxis() >=0.9){
            intakeOut = false;
        }
        // else if (Xbox1.getYButton()){
        //     intakeOut = false;
        // }
        else{
            intakeOut = true;
        }
        return intakeOut;
        // return Xbox1.getAButton();
    }

    double speed = -13250;
    double targetSpeedManual(){
     SmartDashboard.putNumber("Manual Target Shooter Speed", speed);
        if(Xbox2.getBackButtonReleased()){
            speed += 250;
        }
        if(Xbox2.getStartButtonReleased()){
            speed += -250;
        }
        if(speed > 0){
            speed = 0;
        }
        // if(Xbox2.getRightBumper()){
        //       return speed;
        // }
        // else{
        //        return 0;
        // }
        return speed;
      }
      
    double hoodSpeed(){
        if (Xbox1.getLeftBumper()){
            return 0.1;
        }
        else if (Xbox1.getRightBumper()){
            return -0.1;
        }
        else{
            return 0;
        }
        //return 0;
    }

    
    int shooterConfigInt = 1;
    public void shooterTeleopConfigSwitch(){
        switch (Xbox1.getPOV()){
            case 270:
            shooterConfigInt = 1;
            break;
            case 0:
            shooterConfigInt = 2;
            break;
            case 90:
            shooterConfigInt = 3;
            break;
        }
        switch (shooterConfigInt){
            case 1: //at the edge of tarmac
                hoodAutomatedPos = 0;
                shootAutomatedSpeed = -13250;
               // SmartDashboard.putString("Shooter Configuration", "Edge of Tarmac");
            break;
            case 2: //from launchpad
                hoodAutomatedPos = 1322;
                shootAutomatedSpeed = -15250;
              //  SmartDashboard.putString("Shooter Configuration", "Launchpad");
            break;
            case 3: //from back wall
                hoodAutomatedPos = 1830;
                shootAutomatedSpeed = -19500;            
                //SmartDashboard.putString("Shooter Configuration", "Back Wall");

            break;
        }
    }

    public double hoodConfigs(){
    return hoodAutomatedPos;

    }
    public double shootConfigsManual(){
        if(Xbox2.getRightBumper()){
            return shootAutomatedSpeed;
            }
            else{
                return 0;
            }
    }

    public double shootConfigsNoCheck(){
        return shootAutomatedSpeed;
    }






    boolean limeLightTurn = false;
    boolean limeLightTurn(){
    if(Xbox1.getLeftTriggerAxis() >=0.8){
        limeLightTurn = true;
    }    
    else{
        limeLightTurn = false;
    }
    return limeLightTurn;
    
    }



    boolean cargoTurn = false;
    boolean cargoTurn(){
    if(Xbox1.getYButton()){
        cargoTurn = true;
    }    
    else{
        cargoTurn = false;
    }
    return cargoTurn;
    
    }

 





    

}

    


















