/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Onboarder;
import java.io.PrintWriter;

public class UnplayJoy extends CommandBase {
  /**
   * Creates a new UnplayJoy.
   */
  private final DriveTrain driveTrain;
  private final Onboarder onboarder;
  private BufferedReader buffReader;
  private String row;
  private boolean end;
  private ArrayList<String> encoderValues;
  private PrintWriter writer;
  private boolean isDone;
  private int see = 0;
  private int arraySize = 0;

  public UnplayJoy(DriveTrain mDriveTrain, Onboarder onboarding) {
    driveTrain = mDriveTrain;
    onboarder = onboarding;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveTrain, onboarder);
  
  }



  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    String name = SmartDashboard.getString("GamePieceAuto", "Output");
    SmartDashboard.putString("GamePieceAuto", name + "reversed");

    String mName = "/home/lvuser/"+ name + "reversed" + ".csv";
    
    String file = "/home/lvuser/" + name + ".csv";
    new File(mName);
    try {
      writer = driveTrain.initWriter(writer, mName);
    } catch (final Exception e) {
      e.addSuppressed(e);
    }
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
  end = false;
  isDone = false;
  driveTrain.resetEncoder();
  see = 0;
  arraySize = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  if(isDone == false){
    try {
      row = buffReader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if(row == null){
      isDone = true;
    }
    else{
      System.out.println(row);
      try {
        encoderValues.add(row);
      } catch (Exception e) {
        
      }
      
      
      
    }
  }
  else{
    
    try {
      arraySize =encoderValues.size();
    } catch (Exception e) {
      
    }
    if(arraySize <= see){
    see++;
    SmartDashboard.putNumber("see", see);
    SmartDashboard.putNumber("array size", arraySize);
    try {
      writer.append(encoderValues.get(see));
    } catch (Exception e) {
    }
      
    }
    else{
      end = true;
    }
  }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
   
   writer.flush();
   writer.close();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return end;
  }
}
