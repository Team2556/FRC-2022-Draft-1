package frc.robot;
//import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.wpilibj.XboxController;

public class OI {
    
    
    //Xbox object
    XboxController Xbox1 = new XboxController(0);
    XboxController Xbox2 = new XboxController(1);





   
   
   
   
   
   
   
   
   
    //drive piston drops
    boolean dropped = true;
    boolean dropped(){
       
        if(Xbox1.getBButtonReleased()){
            dropped = !dropped;
        }
        return dropped;
    }
    //mecanum drive values
    double mForward(){
        return Xbox1.getLeftY();
    }
    double mStrafe(){
        return Xbox1.getLeftX();
    }
    double mRotate(){
        return Xbox1.getRightX();
    }
    //tank drive values
    double tLeft(){
        return Xbox1.getLeftY();
    }
    double tRight(){
        return Xbox1.getRightY();
    }
















    double winchSpeed(){
        if(Xbox1.getYButton()){
            return -0.3;
        }
        else if(Xbox1.getBButton()){
            return 0.3;
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





   
   
   
   
   
   
   
   
   
    double intakeSpeed(){
        if(Xbox1.getLeftBumper()){
            return -0.1;
        }
        else if(Xbox1.getRightBumper()){
            return 0.1;
        }
        else{
            return 0;
        }
      }
    double translateSpeed(){
        // if(Xbox1.getLeftBumper()){
        //     return -0.1;
        // }   
        // else if(Xbox1.getRightBumper()){
        //     return 0.1;
        // }
        // else{
        //     return 0;
        // }
        return 0;
    }
    boolean intakeOut = true;
    boolean intakeSolenoid(){
        if(Xbox2.getAButtonReleased()){
            intakeOut = !intakeOut;
        }
        return intakeOut;
        // return Xbox1.getAButton();
    }










    double shooterSpeed(){
        if(Xbox1.getYButton()){
              return 1;
        }
        else{
               return 0;
        }
      }
    double hoodSpeed(){
        // if (Xbox1.getAButton()){
        //     return 0.1;
        // }
        // else if (Xbox1.getBButton()){
        //     return -0.1;
        // }
        // else{
        //     return 0;
        // }
        return 0;
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

    


















