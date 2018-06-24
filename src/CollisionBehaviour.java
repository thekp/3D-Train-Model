import java.util.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;

public class CollisionBehaviour extends Behavior {

	// The children of Switch are the red/green spheres
	public Switch sphereSwitch;
	public Primitive shape3D;

	// declare initial and process stimulus (arrays of) criteria
	public WakeupCriterion[] initialCriteria;
	public WakeupCriterion[] procStimCriteria;

	// API: public WakeupOr(WakeupCriterion[] criteria) - logical OR of the
	// (objects in the) criteria
	public WakeupOr initial_wakeUpCondition;
	public WakeupOr procStim_wakeUpCondition;

	/* BEGIN THE CONSTRUCTOR FOR THIS CLASS --------------------------------- */
	// this is called within Example3D.java in the collision detection section
	public CollisionBehaviour(Primitive shape, Switch theSwitch, Bounds theBounds) {
		shape3D = shape;
		sphereSwitch = theSwitch;
		setSchedulingBounds(theBounds);
	}
	/* END THE CONSTRUCTOR FOR THIS CLASS --------------------------------- */

	/* BEGIN INITIALISE BEHAVIOR --------------------------------- */
	public void initialize() {
		initialCriteria = new WakeupCriterion[2];
		initialCriteria[0] = new WakeupOnCollisionEntry(shape3D);
		initialCriteria[1] = new WakeupOnCollisionExit(shape3D);
		// API: WakeupOr(WakeupCriterion[] criteria)
		initial_wakeUpCondition = new WakeupOr(initialCriteria);
		// API: wakeupOn(WakeUpCondition wakeupcondition)
		// this method ensures that processStimulus is called when the
		// [initial_]wakeUpCondition is satisfied ...
		// ... and [initial]Criteria is passed to processStimulus as an
		// enumeration
		wakeupOn(initial_wakeUpCondition);

		// NOTE: process Stimulus wake up criteria may well be different to
		// initial criteria
		procStimCriteria = new WakeupCriterion[2];
		procStimCriteria[0] = new WakeupOnCollisionEntry(sphereSwitch);
		procStimCriteria[1] = new WakeupOnCollisionExit(sphereSwitch);

		System.out.println();
		if (((WakeupCondition) initial_wakeUpCondition) instanceof WakeupOr) {
			System.out.println("*initial new wakeupOn cylinder collision ENTRY OR EXIT *");
		} else {
			System.out.println("*initial new wakeupOn cylinder collison ENTRY AND EXIT *");
		}
	}

	public void processStimulus(Enumeration criteria) {
		// Here we define what happens when a collision occurs.
		System.out.println("process stimulus called");

		///basic green switch to red
		if (sphereSwitch.getWhichChild() == 0) {
			sphereSwitch.setWhichChild(1);
			System.out.println("*red --> green*");
		} else {
			sphereSwitch.setWhichChild(0);
			System.out.println("*green --> red*");
		}

		procStim_wakeUpCondition = new WakeupOr(procStimCriteria);
		wakeupOn(procStim_wakeUpCondition);

		System.out.println();
		if (((WakeupCondition) procStim_wakeUpCondition) instanceof WakeupOr) {
			System.out.println("*procStim new wakeupOn collision sphere ENTRY OR EXIT *");
		} else {
			System.out.println("*procStim new wakeupOn collision sphere ENTRY AND EXIT *");
		}

	} // end process stimulus

	/* END PROCESS STIMULUS BEHAVIOR --------------------------------- */

}