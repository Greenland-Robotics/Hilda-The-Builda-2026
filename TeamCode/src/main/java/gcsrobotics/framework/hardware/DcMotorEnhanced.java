package gcsrobotics.framework.hardware;
import static gcsrobotics.framework.Constants.GLOBAL_DEFAULT_MOTOR_POWER;
import static gcsrobotics.framework.Constants.ENCODER_TOLERANCE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import gcsrobotics.framework.OpModeBase;
@SuppressWarnings("unused")
public class DcMotorEnhanced {
    private final DcMotor motor;
    private double DEFAULT_POWER;

    public DcMotorEnhanced(String name, HardwareMap hardwareMap) {
        this(name, GLOBAL_DEFAULT_MOTOR_POWER, hardwareMap);
    }

    public DcMotorEnhanced(String name, double DEFAULT_POWER, HardwareMap hardwareMap){
        this.motor = hardwareMap.get(DcMotor.class, name);
        this.DEFAULT_POWER = DEFAULT_POWER;
    }

    public void setPosAndWait(int targetPosition, OpModeBase opmode){
        setPosAndWait(targetPosition,DEFAULT_POWER,opmode);
    }
    public void setPosAndWait(int targetPosition, double power,OpModeBase opmode){
        setPosition(targetPosition,power);
        while(!isAtTarget()){
            opmode.sleep(10);
        }
    }

    /// Sets the given motor to go to a certain position, at full power.
    /// If you want to vary the power, add another parameter with the power you want
    public void setPosition(int targetPosition){
        this.setPosition(targetPosition,DEFAULT_POWER);
    }
    /// Sets the given motor to go to a certain position at a given power
    public void setPosition(int targetPosition,double power){
        motor.setTargetPosition(targetPosition);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);
    }

    public void setDefaultPower(double DEFAULT_POWER){
        this.DEFAULT_POWER = DEFAULT_POWER;
    }

    public double getDefaultPower(){return DEFAULT_POWER;}

    public void reset(){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public boolean isAtTarget() {
        return Math.abs(getCurrentPosition() - motor.getTargetPosition()) <= ENCODER_TOLERANCE;
    }

    // === Forward motor control with minimal effort ===
    public void setPower(double power) {
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setPower(power);
    }

    public double getPower() {
        return motor.getPower();
    }

    public void setTargetPosition(int position) {
        motor.setTargetPosition(position);
    }

    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }

    public boolean isBusy() {
        return motor.isBusy();
    }

    public void setMode(DcMotor.RunMode mode) {
        motor.setMode(mode);
    }

    public DcMotor.RunMode getMode() {
        return motor.getMode();
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        motor.setZeroPowerBehavior(behavior);
    }

    public DcMotor.ZeroPowerBehavior getZeroPowerBehavior() {
        return motor.getZeroPowerBehavior();
    }

    public void setDirection(DcMotorSimple.Direction direction){
        motor.setDirection(direction);
    }

    public DcMotorSimple.Direction getDirection(){
        return motor.getDirection();
    }

    /// --- Here you can still access motor directly ---
    public DcMotor getBaseMotor() {
        return motor;
    }
}
