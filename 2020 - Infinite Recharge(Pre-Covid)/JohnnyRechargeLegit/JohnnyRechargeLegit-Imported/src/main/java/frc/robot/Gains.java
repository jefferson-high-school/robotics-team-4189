/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Gains {
    public static final double kPTurn = 0.05;
    public static final double kITurn = 0.01;
    public static final double kDTurn = 0.25;

    public final double kPMM;
	public final double kIMM;
	public final double kDMM;
	public final double kFMM;
	public final int kIzoneMM;
	public final double kPeakOutputMM;
	
	public Gains(double _kP, double _kI, double _kD, double _kF, int _kIzone, double _kPeakOutput){
		kPMM = _kP;
		kIMM = _kI;
		kDMM = _kD;
		kFMM = _kF;
		kIzoneMM = _kIzone;
        kPeakOutputMM = _kPeakOutput;
    }
}
