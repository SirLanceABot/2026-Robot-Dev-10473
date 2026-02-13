package frc.robot.subsystems;

import java.lang.invoke.MethodHandles;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.controls.DriverBindings;
import frc.robot.sensors.Camera;

/**
 * Creates a new PoseEstimator
 * @author Jackson D.
 */
public class PoseEstimator extends SubsystemBase
{
    // This string gets the full name of the class, including the package name
    private static final String fullClassName = MethodHandles.lookup().lookupClass().getCanonicalName();

    // *** STATIC INITIALIZATION BLOCK ***
    // This block of code is run first when the class is loaded
    static
    {
        System.out.println("Loading: " + fullClassName);
    }

    // *** CLASS VARIABLES & INSTANCE VARIABLES ***
    // Put all class variables and instance variables here

    //Subsystems
    private final Pigeon2 gyro;
    private final Drivetrain drivetrain;
    private final Camera camera;

    private final SwerveDrivePoseEstimator poseEstimator;
    
    private Pose2d estimatedPose = new Pose2d();

    //Hub Positions
    Pose2d redHubPose = new Pose2d(new Translation2d(11.92, 4.030), new Rotation2d(0));     //where are these values from?
    Pose2d blueHubPose = new Pose2d(new Translation2d(4.62, 4.030), new Rotation2d(0));

    //AdvantageScope
    private final NetworkTable ASTable;
    private final double fieldXDimension = 16.540988;       //where are these values from?
    private final double fieldYDimension = 8.069326;
    private final double[] defaultPosition = {0.0, 0.0, 0.0};

    private final AprilTagFieldLayout aprilTagFieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded);

    //STD values. These changes how much to trust the vision vs. robot odometry
    private Matrix<N3, N1> visionStdDevs;
    private Matrix<N3, N1> stateStdDevs;

    //output
    private StructPublisher<Pose2d> poseEstimatorEntry;

    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    /** 
     * Creates a new PoseEstimator. 
     */
    public PoseEstimator(Drivetrain drivetrain, Camera camera)
    {
        super("PoseEstimator");
        System.out.println("  Constructor Started:  " + fullClassName);

        this.drivetrain = drivetrain;
        this.gyro = drivetrain.getPigeon2();
        this.camera = camera;

        

        ASTable = NetworkTableInstance.getDefault().getTable(Constants.NetworkTableLance.ADVANTAGE_SCOPE_TABLE);
        //AdvantageScope starting position
        poseEstimatorEntry = ASTable.getStructTopic("PoseEstimator", Pose2d.struct).publish();

        double[] doubleArray = {0.0, 0.0, 0.0};

        visionStdDevs = new Matrix<N3, N1>(Nat.N3(), Nat.N1(), doubleArray);
        stateStdDevs = new Matrix<N3, N1>(Nat.N3(), Nat.N1(), doubleArray);
        configStdDevs();

        if(drivetrain != null && gyro != null)
        {
            poseEstimator = new SwerveDrivePoseEstimator(
                drivetrain.getKinematics(), 
                gyro.getRotation2d(), 
                drivetrain.getState().ModulePositions,
                drivetrain.getState().Pose,
                stateStdDevs,
                visionStdDevs);

            drivetrain.setVisionMeasurementStdDevs(visionStdDevs);
            drivetrain.setStateStdDevs(stateStdDevs);
        }
        else
        {
            poseEstimator = null;
        }

        System.out.println("  Constructor Finished: " + fullClassName);
    }


    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here

    /**
     * Determines how much PoseEstimator should trust vision vs. odometry. Higher values mean less trust.
     * Values to be adjusted later
     */
     public void configStdDevs()
    {
        stateStdDevs.set(0, 0, 0.1); // x in meters
        stateStdDevs.set(1, 0, 0.1); // y in meters
        stateStdDevs.set(2, 0, 0.05); // heading in radians

        visionStdDevs.set(0, 0, 0.1); // x in meters 
        visionStdDevs.set(1, 0, 0.1); // y in meters
        visionStdDevs.set(2, 0, 0.15); // heading in radians
    }

    public void resetPose(Pose2d pose)
    {
        poseEstimator.resetPose(pose);
    }

    public Pose2d getEstimatedPose()
    {
        if(poseEstimator != null)
        {
            return estimatedPose;
        }
        else
        {
            return new Pose2d();
        }
    }

    /**
     * Returns the current pose, as estimated by odometry and vision.
     * @return Current (estimated) position
     */
    public Supplier<Pose2d> getEstimatedPoseSupplier()
    {
        if(poseEstimator != null)
        {
            return () -> estimatedPose;
        }
        else
        {
            return () -> new Pose2d();
        }
    }

    /**
     * Checks if the given pose is within the field
     * @param pose
     * @return Pose is inside field
     */
    public boolean isPoseInsideField(Pose2d pose)
    {
        //tolerance of 1.0 both ways
        return ((pose.getX() > -1.0 && pose.getX() < fieldXDimension + 1.0) &&
                (pose.getY() > -1.0 && pose.getY() < fieldYDimension + 1.0));
    }

    /**
     * Returns the location of an AprilTag with a given ID.
     * NOT TESTED(!!!!!!!!!!!!)
     * @param ID AprilTag ID
     * @return AprilTag 2D location
     */
    public Pose2d getAprilTagLocation(int ID)
    {
        return aprilTagFieldLayout.getTagPose(ID).get().toPose2d();
    }

    public Pose2d getRedHubPose()
    {
        return redHubPose;
    }

    public Pose2d getBlueHubPose()
    {
        return blueHubPose;
    }

    /**
     * Gets the position of your alliance's hub
     * NOT TESTED(!!!!!!!!!!!)
     * @return Alliance hub position
     */
    public Pose2d getAllianceHubPose()
    {
        if(drivetrain.isRedAllianceSupplier().getAsBoolean())
            return redHubPose;
        else
            return blueHubPose;
    }

    /**
     * Gets the distance from the current robot pose to the given target.
     * NOT TESTED(!!!!!!!!)
     * @param robotPose Current robot position
     * @param target Target position
     * @return Distance from target
     */
    public DoubleSupplier getDistanceToTarget(Pose2d robotPose, Pose2d target)
    {
        DoubleSupplier deltay = () -> (target.getY() - robotPose.getY());
        DoubleSupplier deltax = () -> (target.getX() - robotPose.getX());
        return () -> Math.hypot(deltax.getAsDouble(), deltay.getAsDouble());
    }

    public DoubleSupplier getDistancetoRedHub()
    {
        return getDistanceToTarget(estimatedPose, redHubPose);
    }

    public DoubleSupplier getDistanceToBlueHub()
    {
        return getDistanceToTarget(estimatedPose, blueHubPose);
    }

    /**
     * Gets the distance from the current robot pose to your alliance's hub.
     * NOT TESTED(!!!!!!!!!)
     * @return Distance from hub
     */
    public DoubleSupplier getDistanceToAllianceHub()
    {
        if(drivetrain.isRedAllianceSupplier().getAsBoolean())
            return getDistancetoRedHub();
        else
            return getDistanceToBlueHub();
    }

    public DoubleSupplier getAngleToRedTarget(Pose2d robotPose, Pose2d target)
    {
        DoubleSupplier deltay = () -> (target.getY() - robotPose.getY());
        DoubleSupplier deltax = () -> (target.getX() - robotPose.getX());
        return () -> Math.atan2(deltay.getAsDouble(), deltax.getAsDouble());        //why are these inverted?
    }

    public DoubleSupplier getAngleToBlueTarget(Pose2d robotPose, Pose2d target)
    {
        DoubleSupplier deltay = () -> (target.getY() - robotPose.getY());
        DoubleSupplier deltax = () -> (target.getX() - robotPose.getX());
        return () -> Math.atan2(-deltay.getAsDouble(), -deltax.getAsDouble());      //why are these inverted?
    }

    /**
     * Gets the angle to face a given target, based on your alliance.
     * NOT TESTED(!!!!!!!!!!)
     * @param robotPose Current robot position
     * @param target Target position
     * @return Angle to target, in radians
     */
    public DoubleSupplier getAngleToTarget(Pose2d robotPose, Pose2d target)
    {
        if(drivetrain.isRedAllianceSupplier().getAsBoolean())
            return getAngleToRedTarget(robotPose, target);
        else
            return getAngleToBlueTarget(robotPose, target);
    }

    public DoubleSupplier getAngleToRedHub()
    {
        return getAngleToTarget(estimatedPose, redHubPose);
    }

    public DoubleSupplier getAngleToBlueHub()
    {
        return getAngleToTarget(estimatedPose, blueHubPose);
    }

    /**
     * Gets the angle from the current robot pose to your current alliance's hub
     * NOT TESTED(!!!!!!!!!)
     * @return Angle to hub, in radians
     */
    public DoubleSupplier getAngleToAllianceHub()
    {
        if(drivetrain.isRedAllianceSupplier().getAsBoolean())
            return getAngleToRedHub();
        else
            return getAngleToBlueHub();
    }



    // *** OVERRIDEN METHODS ***
    // Put all methods that are Overridden here

    @Override
    public void periodic()
    {
        if(camera != null)
            {
                if(gyro != null)
                {
                    LimelightHelpers.SetRobotOrientation(camera.getCameraName(), drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
                }

                // Only update if tags are visible
                if(camera.getTagCount() > 0)
                {
                    Pose2d visionPose = camera.getPose();
                    double robotVelo = Math.hypot(drivetrain.getState().Speeds.vxMetersPerSecond, drivetrain.getState().Speeds.vyMetersPerSecond);
                    double robotRotation = Math.toDegrees(drivetrain.getState().Speeds.omegaRadiansPerSecond);
                    boolean rejectUpdate = false;

                    if(visionPose == null)
                    {
                        rejectUpdate = true;
                    }

                    if(robotVelo > 3.5)
                    {
                        rejectUpdate = true;
                    }

                    //not sure on this value
                    if(robotRotation > 360.0)
                    {
                        rejectUpdate = true;
                    }

                    if(!rejectUpdate)
                    {
                        drivetrain.addVisionMeasurement(
                                    visionPose,
                                    camera.getTimestamp(),
                                    visionStdDevs);
                    }
                }
            }

        //OUTPUTS
        if(drivetrain != null && gyro != null && poseEstimator != null)
        {
            //Sets estimatedPose to newest estimated pose
            estimatedPose = drivetrain.getState().Pose;
            //Sets estimated pose in AdvantageScore
            poseEstimatorEntry.set(estimatedPose);
        }
    }

    @Override
    public String toString()
    {
        return "Estimated Pose: " + estimatedPose;
    }
}
