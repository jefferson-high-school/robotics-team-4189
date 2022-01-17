/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Onboarder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReplayJoy extends CommandBase {
  /**
   * Creates a new RecordJoy.
   */
  private final DriveTrain mDriveTrain;
  private final Onboarder onboarder;
  private BufferedReader buffReader;
  private String row;
  private boolean end = false;
  private double xVal;
  private double yVal;
  private String file;
  private String name;
  private String suck;
  // private boolean flag;

  public ReplayJoy(DriveTrain driveTrain, Onboarder onboarding) {
    mDriveTrain = driveTrain;
    onboarder = onboarding;
    addRequirements(mDriveTrain, onboarder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    name = SmartDashboard.getString("GamePieceAuto", "Output");
    file = "/home/lvuser/" + name + ".csv";
    end = false;
    
    try {
      buffReader = new BufferedReader(new FileReader(file));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    try {
      row = buffReader.readLine();
      row = buffReader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      
    }
    suck = "false";
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putBoolean("Replaying", true);
        try {
          row = buffReader.readLine();
        } catch (IOException e) {
          e.printStackTrace();
        }
        
        if(row == null)
          end = true;
        else{
          
          String[] data = row.split(",");
          xVal = Double.valueOf(data[0]);
          yVal = Double.valueOf(data[1]);
          // suck = data[2];
          
          if(suck.equals("true"))
            onboarder.onboard(1);
          else
            onboarder.onboard(0);

          mDriveTrain.drive(xVal, yVal);
        }
      }
    

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putBoolean("Replaying", false);
    onboarder.onboard(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return end;
  }
}
