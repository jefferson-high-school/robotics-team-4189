/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveWithController extends CommandBase {
  /**
   * Creates a new DriveWithController.
   */
  private final DriveTrain driveTrain;
  private final XboxController controller;
  private double drive;
  private double rotation;
  public DriveWithController(DriveTrain imput1, XboxController imput2) {
    // Use addRequirements() here to declare subsystem dependencies.
    driveTrain = imput1;
    controller = imput2;
    addRequirements(driveTrain); 
  
  drive = 0;
  rotation = 0;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive = controller.getY(Hand.kLeft);
    rotation = controller.getX(Hand.kRight) * .7;



    driveTrain.drive(drive * -.7, rotation * .9);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
