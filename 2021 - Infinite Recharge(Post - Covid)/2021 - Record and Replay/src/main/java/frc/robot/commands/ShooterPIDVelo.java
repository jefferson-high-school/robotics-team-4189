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
import frc.robot.subsystems.Camera2;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShooterPIDVelo extends PIDCommand {
  /**
   * Creates a new ShooterPIDVelo.
   */
  private final Shooter shoot;
  private final Camera2 cam;
  public int endCount;
  public boolean loaded;
  public int c;
  public int x;
  public double timeEnd;
  public int center = 120;
  public double pixels;
  public boolean isDone;
  private double getDisplacement;
  private double targetAngle;

  public ShooterPIDVelo(Shooter shooter, Camera2 cam2) {
    super(
        // The controller that the command will use
        new PIDController(0,0,0),
        // This should return the measurement
        () -> shooter.getShootVelocity(),
        // This should return the setpoint (can also be a constant)
        () -> 16000,
        // This uses the output
        output -> {
          // Use the output here
        shooter.shoot(output);
        
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(25);
    shoot = shooter;
    cam = cam2;
    addRequirements(shooter);
    endCount = 0;
    loaded = false;
    isDone = false;
    c = 0;
    x=0;
    timeEnd = 35;
    // targetAngle = cam.readAngle();
    // targetAngle = (targetAngle - startAngle);
    getDisplacement = 0;
    SmartDashboard.putNumber("targetAngle", 0);
  }
  
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    getController().setPID(.005,.003,0);
    timeEnd = SmartDashboard.getNumber("Timer", 7);
    // {
    //   if((3 >= pixels && -3<= pixels)){
    //     isDone = true;
    //     shoot.swivel(0);
    //   }
    //   else if(3 < pixels){
    //     shoot.swivel(.25);
    //   }
    //   else if(-3 > pixels){
    //     shoot.swivel(-.25);
    //   }
    //   pixels = cam.readOffsetPixels();
    // }
    {
      // targetAngle = (cam.readAngle());
      targetAngle = SmartDashboard.getNumber("targetAngle", 0);
      getDisplacement = shoot.getTiltSpeed();
      if(Math.abs(getDisplacement) > Math.abs(targetAngle)) {
        shoot.tilt(0);
      }
      else if(targetAngle > 0){
        shoot.tilt(-1);
      }
      else{
        shoot.tilt(1);
      }
    }
    if(getController().atSetpoint()){
      SmartDashboard.putBoolean("PERFECTish VELO", true);
      SmartDashboard.putNumber("endCount", endCount);
      SmartDashboard.putNumber("c", c);
      SmartDashboard.putNumber("x", x);
      if(loaded){
        // shoot.load(0);
        x++;
        if(c>1)
          endCount++;
        if(x >= 12)
          loaded = false;
        c=0;
        
      }
      else{
        // shoot.load(-1);
        c++;
        if(c >= timeEnd)
          loaded = true;
        x=0;
      }

    }else{
      // shoot.load(0);

      SmartDashboard.putBoolean("PERFECTish VELO", false);
      loaded = false;
    }
    if(endCount > 3){
      shoot.swivel(0);
      shoot.tilt(0);

      endCount = 0;
      loaded = false;
      isDone = false;
      c = 0;
      // x=0;
      timeEnd = 35;
      getController().reset();
      return true;
    }
    return false;
  }
}
