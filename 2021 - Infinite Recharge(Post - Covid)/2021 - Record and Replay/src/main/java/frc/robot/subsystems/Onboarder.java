/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase; 

public class Onboarder extends SubsystemBase {
  /**
   * Creates a new Onboarder.
   */
  private final TalonSRX talon;

  public Onboarder() {
    talon = new TalonSRX(4);
  }
  
  public void onboard(double speed){
    talon.set(ControlMode.PercentOutput ,speed);
  }

  @Override
  public void periodic() {
    // this.onboard(1);
    // This method will be called once per scheduler run
  }
}
