/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.FlyWheel;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShooterPIDVelo extends PIDCommand {
  /**
   * Creates a new ShooterPIDVelo.
   */
  private final FlyWheel shoot;
  public int endCount;
  public boolean loaded;
  public int c;
  public int x;
  public int timeEnd;

  public ShooterPIDVelo(FlyWheel shooter) {
    super(
        // The controller that the command will use
        new PIDController(0,0,0),
        // This should return the measurement
        () -> shooter.getVelocity(),
        // This should return the setpoint (can also be a constant)
        () -> 16000,
        // This uses the output
        output -> {
          // Use the output here
        shooter.setShooterSpeed(output);
        
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(50);
    shoot = shooter;
    addRequirements(shooter);
    endCount = 0;
    loaded = false;
    c = 0;
    x=0;
    timeEnd = 35;
  }
  
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    SmartDashboard.putString("State", "ShOOOTOTOTOOTOTOTO");
    getController().setPID(SmartDashboard.getNumber("kP", 0), SmartDashboard.getNumber("kI", 0), SmartDashboard.getNumber("kD", 0));
    shoot.setTurretSpeed(0);
    if(getController().atSetpoint() ){
      SmartDashboard.putBoolean("PERFECTish VELO", true);
    //   if(loaded){
    //     shoot.setLoadSpeed(0);
    //     System.out.println(c + "          asdsadsadsdsdadsad         " + endCount);
    //     x++;
    //     if(c>1)
    //       endCount++;
    //     if(x >= 100)
    //       loaded = false;
    //     c=0;
    //   }
    //   else{
    //     shoot.setLoadSpeed(-1);
    //     c++;
    //     if(c >= 8)
    //       loaded = true;
    //     x=0;
    //   }
    }else{
    //   shoot.setLoadSpeed(0);
      SmartDashboard.putBoolean("PERFECTish VELO", false);
    //   loaded = false;
    }
    // if(endCount >=4){
    //   endCount = 0;
    //   loaded = false;
    //   c = 0;
    //   x=0;
    //   timeEnd = 35;
      // getController().reset();
    //   return true;
    
    return false;
  }
}
