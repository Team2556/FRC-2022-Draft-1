package frc.robot;
//import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
    
    
    //Xbox object
    XboxController Xbox1 = new XboxController(0);
    XboxController Xbox2 = new XboxController(1);

    double deadzone = 0.2;



   
   
   
   
   
   
   
   
   
    //drive piston drops
    boolean dropped = false;
    boolean dropped(){
       
        if(Xbox1.getBButtonReleased() && Xbox1.getRightTriggerAxis() >= 0.5){
            dropped = !dropped;
        }
        return dropped;
       
    }
    //mecanum drive values
    double mForwardSpeed = 0;
    double mForward(){
        if (Math.abs(Xbox1.getLeftY()) <= deadzone){
            mForwardSpeed = 0;
        }
        else{
            mForwardSpeed = Xbox1.getLeftY();
        }
        return mForwardSpeed;
    }
    double mStrafeSpeed = 0;
    double mStrafe(){
        if (Math.abs(Xbox1.getLeftX()) <= deadzone){
            mStrafeSpeed = 0;
        }
        else{
            mStrafeSpeed = Xbox1.getLeftX();
        }
        return mStrafeSpeed;
    }
    double mRotateSpeed = 0;
    double mRotate(){
        if (Math.abs(Xbox1.getRightX()) <= deadzone){
            mRotateSpeed = 0;
        }
        else{
            mRotateSpeed = Xbox1.getRightX();
        }
        return mRotateSpeed;
    }
    //tank drive values
    double aForward(){
        return Xbox1.getLeftY();
    }
    double aRotate(){
        return Xbox1.getRightX();
    }
















    double winchSpeed(){
        if(Xbox1.getRightBumper()){
            return -0.5;
        }
        else if(Xbox1.getLeftBumper()){
            return 0.5;
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





   
   
   
   
   
   
   
   
    double intakeRunSpeed = 0.75;
    double intakeSpeed(){
        if(Xbox1.getLeftBumper()){
            return intakeRunSpeed;
        }
        // else if(Xbox1.getRightBumper()){
        //     return 0.5;
        // }
        else{
            return 0;
        }
      }

    double translateRunSpeed = -0.75;
    double translateSpeed(){
        if(Xbox2.getLeftBumper()){
            return translateRunSpeed;
        }   
        // else if(Xbox1.getRightBumper()){
        //     return 1;
        // }
        else{
            return 0;
        }
      //  return 0;
    }
    boolean intakeOut = true;
    boolean intakeSolenoid(){
        if(Xbox1.getLeftBumper()){
            intakeOut = false;
        }
        else{
            intakeOut = true;
        }
        return intakeOut;
        // return Xbox1.getAButton();
    }

    double speed = -13000;
    double targetSpeed(){
     SmartDashboard.putNumber("Target Shooter Speed", speed);
        if(Xbox2.getBackButtonReleased()){
            speed += 250;
        }
        if(Xbox2.getStartButtonReleased()){
            speed += -250;
        }
        if(speed > 0){
            speed = 0;
        }
        if(Xbox2.getRightBumper()){
              return speed;
        }
        else{
               return 0;
        }
      }

    double hoodSpeed(){
        if (Xbox1.getLeftTriggerAxis() == 1){
            return 0.15;
        }
        else if (Xbox1.getRightTriggerAxis() == 1){
            return -0.15;
        }
        else{
            return 0;
        }
        //return 0;
    }










    // double shit(){
    // return Xbox1.getLeftY();  
    // }


    boolean limeLightTurn = false;
    boolean limeLightTurn(){
    if(Xbox1.getAButton()){
        limeLightTurn = true;
    }    
    else{
        limeLightTurn = false;
    }
    return limeLightTurn;
    
    }





 





    

}

    


















