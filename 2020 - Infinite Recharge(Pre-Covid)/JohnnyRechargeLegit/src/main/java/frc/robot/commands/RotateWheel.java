/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ColorWheelSpinner;


public class RotateWheel extends CommandBase {
  /**
   * Creates a new AlignColor.
   */
  private int colorCounter = 0;

  private final ColorWheelSpinner spinner = new ColorWheelSpinner();

  private String targetColor = spinner.detectColors();

  private boolean isDone = false;
  public RotateWheel(ColorWheelSpinner input) {
    
    addRequirements(spinner);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //targetColor = DriverStation.getInstance().getGameSpecificMessage();
    //spinner.spinOLC(1);
    spinner.spinOLC(1);
    Timer.delay(4.5);
    isDone = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (spinner.detectColors() == targetColor)
      colorCounter++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
    spinner.spinOLC(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //if(colorCounter >= 7){
    //  return true;
    //}
    //return false;
      return isDone;
  }
}
