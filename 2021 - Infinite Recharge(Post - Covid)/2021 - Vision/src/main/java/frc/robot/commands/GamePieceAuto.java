/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.DriveTrain;

public class GamePieceAuto extends CommandBase {
  /**
   * Creates a new GamePieceAuto.
   */
  private final DriveTrain driveTrain;
  private final Camera cam;
  private boolean direction;
  private boolean done;
  public GamePieceAuto(DriveTrain train, Camera camera) {
    // Use addRequirements() here to declare subsystem dependencies.
    driveTrain = train;
    cam = camera;
    direction = false;
    done = false;
    addRequirements(driveTrain, cam);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    done = false;
    direction = cam.direction();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (direction){
      driveTrain.drive(.5, 0);
      done = true;
    }
    if (!direction){
      driveTrain.drive(-.5, 0);
      done = true;
    }
      

  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putBoolean("GamePieceAuto Data From Camera", direction);
    driveTrain.drive(0, 0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
