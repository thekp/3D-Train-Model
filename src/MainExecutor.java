
/**
 * Description of the Class:
 * 
 * This is the main executable class, that creates the universe and adds the 3D models to it.
 * It also includes the collision.
 *
 * Created By Khoa Phan
 */

import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import javax.swing.JFrame;
import com.sun.j3d.utils.geometry.Sphere;

public class MainExecutor extends JFrame {

	public MainExecutor() {

		// Creating a container and canvas
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		cp.add("Center", canvas);

		// Creating a universe
		BranchGroup scene = createSceneGraph();
		SimpleUniverse simpleUniverse = new SimpleUniverse(canvas);
		simpleUniverse.getViewingPlatform().setNominalViewingTransform();
		simpleUniverse.addBranchGraph(scene);

		// *** create a viewing platform
		TransformGroup cameraTG = simpleUniverse.getViewingPlatform().getViewPlatformTransform();
		// starting postion of the viewing platform
		Transform3D view3D = new Transform3D();
		view3D.setTranslation(new Vector3f(-3.0f, 0.0f, 4.5f));
		Transform3D yAxis = new Transform3D();
		yAxis.rotY(-Math.PI / 6);
		view3D.mul(yAxis);
		cameraTG.setTransform(view3D);

		// Additional information for the universe
		addLight(simpleUniverse);
		add("South", new Label("You are able to rotate the image(with left click), translate (with right click) and zoom in (with mouse-wheel)."));
		setTitle("CO2016 - Train Model 3D");
		setSize(700, 700);
		setVisible(true);
	}

	public static void main(String[] args) {
		//Starts the application
		new MainExecutor();

	}

	// Some light is added to the scene here.
	public void addLight(SimpleUniverse su) {

		BranchGroup bgLight = new BranchGroup();
		// Set Bounds
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		// Global light
		Color3f alColor = new Color3f(0.2f, 0.2f, 0.2f);
		AmbientLight aLgt = new AmbientLight(alColor);
		aLgt.setInfluencingBounds(bounds);
		bgLight.addChild(aLgt);

		// Light from direction 1
		Color3f lightColour1 = new Color3f(0.2f, 0.2f, 0.2f);
		Vector3f lightDir1 = new Vector3f(0.0f, 1.0f, 0.0f);
		DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
		light1.setInfluencingBounds(bounds);
		bgLight.addChild(light1);

		// Light from direction 2
		Vector3f lightDir2 = new Vector3f(1.0f, -1.0f, 0.5f);
		DirectionalLight light2 = new DirectionalLight(lightColour1, lightDir2);
		light2.setInfluencingBounds(bounds);
		bgLight.addChild(light2);

		su.addBranchGraph(bgLight);
	}

	// Method that creates the scene with all the models
	public BranchGroup createSceneGraph() {

		// set bounds
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100);

		// creating branch groups
		BranchGroup objRoot = new BranchGroup();
		BranchGroup trainBG = new BranchGroup();
		BranchGroup backgroundBG = new BranchGroup();
		BranchGroup blockBG = new BranchGroup();

		// creating a main transform group (inherits mouse behaviour)
		TransformGroup mainTG = new TransformGroup();
		// set the capability to TRANSFORM the main TG (e.g mouse MOVEMENT)
		mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		// top-level transform groups for bgA and bgB
		TransformGroup trainTG1 = new TransformGroup();
		TransformGroup backgroundTG1 = new TransformGroup();

		objRoot.addChild(mainTG);
		mainTG.addChild(trainBG);
		trainBG.addChild(trainTG1);
		mainTG.addChild(backgroundBG);
		backgroundBG.addChild(backgroundTG1);
		mainTG.addChild(blockBG);
		/*
		 * *****************Adding Train Model
		 */
		TrainModel train = new TrainModel();
		// Moving train and train positioning
		TransformGroup moveTrain = new TransformGroup();
		Transform3D xAxis = new Transform3D();
		float maxLeft = -2.3f;
		// -1 = move infinite
		Alpha trainAlpha = new Alpha(-1, 5000);
		// Moving the Train
		PositionInterpolator trainMover = new PositionInterpolator(trainAlpha, moveTrain, xAxis, 2.3f, maxLeft);
		trainMover.setSchedulingBounds(bounds);
		moveTrain.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		// Adding train to branch
		moveTrain.addChild(trainMover);
		trainTG1.addChild(moveTrain);
		moveTrain.addChild(train.createTrain());

		/*
		 * *****************End of Train Model
		 */

		/*
		 * *****************Adding Background Model
		 */
		BackgroundModel background = new BackgroundModel();
		Transform3D backgroundTM = new Transform3D();
		// Adjust position of Background
		backgroundTM.setTranslation(new Vector3d(0.0, -0.2, 0.0));
		TransformGroup backgroundTG2 = new TransformGroup(backgroundTM);
		// Adding background to model
		backgroundTG1.addChild(backgroundTG2);
		backgroundTG2.addChild(background.createTracks());
		/*
		 * *****************End of Background
		 */

		/*
		 * *****************Mouse Interactions
		 */

		// Create the rotate behavior node
		MouseRotate behavior = new MouseRotate();
		behavior.setTransformGroup(mainTG);
		objRoot.addChild(behavior);
		behavior.setSchedulingBounds(bounds);
		
		// Create the translate behaviour node
		MouseTranslate behaviour1 = new MouseTranslate();
		behaviour1.setTransformGroup(mainTG);
		objRoot.addChild(behaviour1);
		behaviour1.setSchedulingBounds(bounds);

		// Create the wheel-zoom behavioUr node
		MouseWheelZoom behaviour2 = new MouseWheelZoom();
		behaviour2.setTransformGroup(mainTG);
		objRoot.addChild(behaviour2);
		behaviour2.setSchedulingBounds(bounds);

		/*
		 * *****************Collision Behaviour
		 * 
		 */
		// An Appearance for the green sphere.
		Color3f ambientColourGSphere = new Color3f(0.0f, 0.8f, 0.0f);
		Color3f emissiveColourGSphere = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f diffuseColourGSphere = new Color3f(0.8f, 0.8f, 0.8f);
		Color3f specularColourGSphere = new Color3f(0.8f, 0.8f, 0.8f);
		float shininessGSphere = 1.0f;
		Appearance greenSphereApp = new Appearance();
		greenSphereApp.setMaterial(new Material(ambientColourGSphere, emissiveColourGSphere, diffuseColourGSphere,
				specularColourGSphere, shininessGSphere));
		// Generate the green sphere.
		Sphere boxGreen = new Sphere(0.25f, greenSphereApp);

		// The same for the red sphere.
		Color3f ambientColourRSphere = new Color3f(0.9f, 0.0f, 0.0f);
		Color3f emissiveColourRSphere = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f diffuseColourRSphere = new Color3f(0.8f, 0.8f, 0.8f);
		Color3f specularColourRSphere = new Color3f(0.8f, 0.8f, 0.8f);
		float shininessRSphere = 1.0f;
		Appearance redSphereApp = new Appearance();
		redSphereApp.setMaterial(new Material(ambientColourRSphere, emissiveColourRSphere, diffuseColourRSphere,
				specularColourRSphere, shininessRSphere));
		Sphere boxRed = new Sphere(0.25f, redSphereApp);
		// *** A Switch for the green and the red shapes
		Switch colourSwitch = new Switch();
		colourSwitch.setCapability(Switch.ALLOW_SWITCH_WRITE);

		// The Switch node controls which of its children will be rendered.
		// Add the spheres to the Switch.
		colourSwitch.addChild(boxRed); // child 0
		colourSwitch.addChild(boxGreen); // child 1

		// The green sphere should be visible in the beginning.
		colourSwitch.setWhichChild(0);

		// A transformation group for the Switch (the spheres).
		Transform3D tfSphere = new Transform3D();
		tfSphere.setTranslation(new Vector3f(-0.5f, 0.0f, 0.0f));
		TransformGroup tgSphere = new TransformGroup(tfSphere);
		tgSphere.addChild(colourSwitch);
		blockBG.addChild(tgSphere);

		colourSwitch.setCollisionBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 0.15f));
		// Enabled for collision purposes
		backgroundTG1.setCollidable(false);

		// CollisionBehaviour1 class takes care of changing the colour of the
		CollisionBehaviour scb1 = new CollisionBehaviour(boxGreen, colourSwitch, bounds);
		mainTG.addChild(scb1);

		/*
		 * *****************3D Text and textTransform
		 */
		// Font style
		Font my2DFont = new Font("Arial", Font.PLAIN, 1);
		FontExtrusion myExtrude = new FontExtrusion();
		Font3D my3DFont = new Font3D(my2DFont, myExtrude);
		// Creating text
		Text3D text = new Text3D();
		text.setFont3D(my3DFont);
		text.setString("Created By Khoa Phan");
		Shape3D text3D = new Shape3D(text);
		// Adjusting the position
		Transform3D textTM = new Transform3D();
		textTM.setScale(new Vector3d(0.20, 0.20, 0.20));
		textTM.setTranslation(new Vector3d(-1.0, -0.8, 0.3));
		Transform3D rotateText = new Transform3D();
		rotateText.rotY(-Math.PI / 5);
		textTM.mul(rotateText);
		TransformGroup textTG = new TransformGroup(textTM);
		// Adding to mainTG
		mainTG.addChild(textTG);
		textTG.addChild(text3D);

		// Return the scene
		objRoot.compile();
		return objRoot;
	}

}
