/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DumperConstants;

public class DumpMechanism extends SubsystemBase {
  /**
   * Creates a new DumpMechanism.
   */
  private final WPI_TalonSRX mRollerTalon;
  private final Solenoid mCellPusher;
  public DumpMechanism() {
    mRollerTalon = new WPI_TalonSRX(DumperConstants.kRollerTalonID);
    mCellPusher = new Solenoid(DumperConstants.kSolenoidPort);
    initRollerTalon(mRollerTalon);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void initRollerTalon(WPI_TalonSRX rollerTalon){
    rollerTalon.configFactoryDefault();
    rollerTalon.setNeutralMode(NeutralMode.Coast);
  }

  public void spinRollersForwards(double rollerSpeed){
    mRollerTalon.set(ControlMode.PercentOutput, rollerSpeed);
  }

  public void spinRollersBackwards(double rollerSpeed){
    mRollerTalon.set(ControlMode.PercentOutput, -rollerSpeed);
  }

  public void spinRollersOLC(double rollerSpeed){
    mRollerTalon.set(ControlMode.PercentOutput, rollerSpeed);
  }

 public void dumpCells(boolean dumping){
   mCellPusher.set(dumping);
 }
}
