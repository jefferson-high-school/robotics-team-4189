/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.StringTokenizer;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
//import frc.robot.commands.Autofix;

/**
 * Add your docs here.
 */
public class Camera3 extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public void doStuff(String input){
    String[] numbs = parseString(input);
    ArrayList<Double> toaster = convertDouble(numbs);
    String direction = getDirection(toaster);
    move(direction);
  }
  private String[] parseString(String input){
    StringTokenizer tokens = new StringTokenizer(input, "\n");
    // System.out.println(numbs);
    String line = tokens.nextToken().trim();
    String[] result = line.split(",");
    System.out.println(line);
    return result;
  }
  private ArrayList<Double> convertDouble(String[] input){
    ArrayList<Double> result = new ArrayList<Double>();
    if(input.length >= 3 && input.length % 3 == 0){
      for(int a = 0; a < input.length; a++){
          try{result.add(Double.parseDouble(input[a]));
          }catch(NumberFormatException exception){
            System.out.println("no");
            Robot.driveSubsystem.manualDrive(0, 0, 0);
            break;
            //blink like crazy
          }
       }
      }
      return result;
  }
  private String getDirection(ArrayList<Double> input){
    String result ="";
    try{
    if(input.get(0) < 1){

    }
    else if(input.get(0) == 1){
      if(input.get(1) > 135){
        result ="right";
      }
      else if(input.get(1) < 115){
        result = "left";
      }
      else if(input.get(1) < 135 && input.get(1) > 115){
        result = "center";
      }
    }
  }catch(IndexOutOfBoundsException exception){
    System.out.println("bad"); 
    Robot.driveSubsystem.manualDrive(0, 0, 0);
  }
    // if(input.size()==3 && input.get(2) > 1.5){
    //   result = "right";
    // }
    // else if(input.size() == 3 && input.get(2) < 1.5){
    //   result = "left";
    // }
    // else if(input.size() == 6){
    //   double midpoint = findMidpoint(input.get(1), input.get(4));
    //   if(midpoint > 80){
    //     if(midpoint < 120){
    //       result ="right";
    //     }else {
    //       result ="little right";
    //     }
    //   }
    //   else if(midpoint < 60){
    //     if(midpoint > 20){
    //       result ="left";
    //     }else{
    //       result = "little left";
    //   }
    //   }
    //   else if(midpoint < 80 && midpoint > 60){
    //     result = "center";
    //   }
    // }
    return result;
  }
  private void move(String direction){
    if(direction == "left"){
      Robot.driveSubsystem.manualDrive(-1, 0, 0);
    }
    else if(direction == "little left"){
      Robot.driveSubsystem.manualDrive(.35, 0, 0);
    }
    else if(direction == "little right"){
      Robot.driveSubsystem.manualDrive(-.35, 0, 0);
    }
    else if(direction == "right"){
      Robot.driveSubsystem.manualDrive(1, 0, 0);
    }
    else if(direction == "center"){
      Robot.driveSubsystem.manualDrive(0, 0, 0);
    }
    else if(direction == ""){
      Robot.driveSubsystem.manualDrive(0, 0, 0);
    }

    // if(direction == "left"){
    //   Robot.driveSubsystem.manualDrive(.35, 0, -OI.ahrs.getAngle()*Robot.driveSubsystem.kP);
    // }
    // else if(direction == "little left"){
    //   Robot.driveSubsystem.manualDrive(.35, 0, -OI.ahrs.getAngle()*Robot.driveSubsystem.kP);
    // }
    // else if(direction == "little right"){
    //   Robot.driveSubsystem.manualDrive(-.35, 0, -OI.ahrs.getAngle()*Robot.driveSubsystem.kP);
    // }
    // else if(direction == "right"){
    //   Robot.driveSubsystem.manualDrive(-.35, 0, -OI.ahrs.getAngle()*Robot.driveSubsystem.kP);
    // }
    // else if(direction == "center"){
    //   Robot.driveSubsystem.manualDrive(0, 0, -OI.ahrs.getAngle()*Robot.driveSubsystem.kP);
    // }
    // else if(direction == ""){
    //   Robot.driveSubsystem.manualDrive(0, 0, 0);
    // }
  }
  
//feel bad for the suckers that got to read this code -Bryan
//birds are good! - Austin 2.0 retro
//
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    //setDefaultCommand(new Autofix());
  }
}
