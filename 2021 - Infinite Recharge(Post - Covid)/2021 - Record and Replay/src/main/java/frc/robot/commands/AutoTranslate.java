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
import frc.robot.subsystems.DriveTrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoTranslate extends PIDCommand {
  /**
   * Creates a new AutoTranslate.
   */
  public AutoTranslate(DriveTrain driveTrain) {
    super(
        // The controller that the command will use
        new PIDController(0, 0, 0),
        // This should return the measurement
        () -> driveTrain.getEncoderLeft() * 11822.6,
        // This should return the setpoint (can also be a constant)
        () -> 5,
        // This uses the output
        output -> {
          // Use the output here
          
          driveTrain.drive(-output * .6, 0);
        });
        driveTrain.resetEncoder();
        addRequirements(driveTrain);
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(50);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    getController().setPID(SmartDashboard.getNumber("kPDrive", 0), SmartDashboard.getNumber("kIDrive", 0), SmartDashboard.getNumber("kDDrive", 0));
    if(getController().atSetpoint())
      return true;
    return false;
  }
}
