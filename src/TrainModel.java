/**
 * Description of the Class:
 * 
 * This class returns a transform group which has 3D shapes added to it to make a 3D train model.
 * Includes wheels spinning
 * 
 * Created By Khoa Phan
 */

import java.awt.Container;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;

public class TrainModel {

	public TransformGroup createTrain() {

		// set bounds
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE);

		/*
		 * Train Appearances
		 */
		// creating an appearance (for a train base)
		Color3f ambientColourCube1 = new Color3f(0.0f, 0.1f, 0.5f);
		Color3f emissiveColourCube1 = new Color3f(0.0f, 0.3f, 0.5f);
		Color3f diffuseColourCube1 = new Color3f(1.0f, 1.0f, 1.0f);
		Color3f specularColourCube1 = new Color3f(0.0f, 0.0f, 0.0f);
		float shininessCube1 = 128.0f;
		// Generate the appearance and apply material to cube
		Appearance blueAPP = new Appearance();
		blueAPP.setMaterial(new Material(ambientColourCube1, emissiveColourCube1, diffuseColourCube1,
				specularColourCube1, shininessCube1));

		Color3f ambientColour = new Color3f(0.1f, 0.1f, 0.1f);
		Color3f emissiveColour = new Color3f(0.1f, 0.1f, 0.1f);
		Color3f diffuseColour = new Color3f(0.5f, 0.5f, 0.5f);
		Color3f specularColour = new Color3f(0.3f, 0.3f, 0.3f);
		float shininess = 128.0f;
		// Generate the appearance and apply material to cube
		Appearance greyAPP = new Appearance();
		greyAPP.setMaterial(new Material(ambientColour, emissiveColour, diffuseColour, specularColour, shininess));

		// Set up the texture map
		TextureLoader loader = new TextureLoader("blue_tex.jpg", "LUMINANCE", new Container());
		Texture texture = loader.getTexture();
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);
		texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));
		// Set up the texture attributes
		// could be REPLACE, BLEND or DECAL instead of MODULATE
		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.BLEND);
		Appearance metalTexture = new Appearance();
		metalTexture.setTexture(texture);
		metalTexture.setTextureAttributes(texAttr);
		// set up the material
		Color3f grey = new Color3f(0.15f, 0.15f, 0.15f);
		Color3f red = new Color3f(0.9f, 0.15f, 0.15f);
		metalTexture.setMaterial(new Material(red, red, grey, grey, 1.0f));
		int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

		// Setting up Transformation Group
		TransformGroup trainModelTG = new TransformGroup();
		TransformGroup baseTG = new TransformGroup();
		TransformGroup wheelsTG = new TransformGroup();

		// Adding main parts
		trainModelTG.addChild(wheelsTG);
		trainModelTG.addChild(baseTG);

		// Making transform group for wheel to spin
		TransformGroup rotation1 = new TransformGroup();
		// set capability for wheel1(by ROTATION)
		rotation1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D yAxis = new Transform3D();
		Transform3D zAxis = new Transform3D();
		yAxis.rotY(Math.PI / 2);
		zAxis = yAxis;
		Alpha wheel1Alpha = new Alpha(-1, 2000);
		RotationInterpolator rotationTG1 = new RotationInterpolator(wheel1Alpha, rotation1, zAxis, 0.0f,
				(float) Math.PI * (2.0f));
		rotationTG1.setSchedulingBounds(bounds);

		// rotate for front wheel
		TransformGroup rotation2 = new TransformGroup();
		// set capability for wheel2(by ROTATION)
		rotation2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		RotationInterpolator rotationTG2 = new RotationInterpolator(wheel1Alpha, rotation2, zAxis, 0.0f,
				(float) Math.PI * (2.0f));
		rotationTG2.setSchedulingBounds(bounds);

		/*
		 * Setting up the parts of the Train
		 */
		// Main base of train

		// Front Wheel
		Transform3D wheelTM1 = new Transform3D();
		wheelTM1.setTranslation(new Vector3d(-0.12, -0.1, 0.0));
		Transform3D rotateWheel1 = new Transform3D();
		rotateWheel1.rotX(Math.PI / 2);
		wheelTM1.mul(rotateWheel1);
		TransformGroup cylinderTG1 = new TransformGroup(wheelTM1);
		Cylinder frontWheel = new Cylinder(0.08f, 0.3f, greyAPP);
		Box wheelDetail1 = new Box(0.01f, 0.16f, 0.05f, primflags, metalTexture);
		Box wheelDetail2 = new Box(0.05f, 0.16f, 0.01f, primflags, metalTexture);
		// Adding to wheel group
		wheelsTG.addChild(cylinderTG1);
		cylinderTG1.addChild(frontWheel);
		cylinderTG1.addChild(rotation1);
		rotation1.addChild(rotationTG1);
		rotation1.addChild(wheelDetail1);
		rotation1.addChild(wheelDetail2);

		// Back Wheel
		Transform3D wheelTM2 = new Transform3D();
		wheelTM2.setTranslation(new Vector3d(0.32, -0.1, 0.0));
		Transform3D rotateWheel2 = new Transform3D();
		rotateWheel2.rotX(Math.PI / 2);
		wheelTM2.mul(rotateWheel2);
		TransformGroup cylinderTG2 = new TransformGroup(wheelTM2);
		Cylinder backWheel = new Cylinder(0.08f, 0.3f, greyAPP);
		Box wheelDetail3 = new Box(0.01f, 0.16f, 0.05f, primflags, metalTexture);
		Box wheelDetail4 = new Box(0.05f, 0.16f, 0.01f, primflags, metalTexture);
		// Adding to wheel group
		wheelsTG.addChild(cylinderTG2);
		cylinderTG2.addChild(backWheel);
		cylinderTG2.addChild(rotation2);
		rotation2.addChild(rotationTG2);
		rotation2.addChild(wheelDetail3);
		rotation2.addChild(wheelDetail4);

		// Main train base;
		Box mainBase = new Box(0.25f, 0.1f, 0.12f, blueAPP);
		trainModelTG.addChild(mainBase);

		// Bottom train base
		Transform3D botBaseTM = new Transform3D();
		botBaseTM.setTranslation(new Vector3d(0.1, -0.11, 0.0));
		TransformGroup botBaseTG = new TransformGroup(botBaseTM);
		Box bottomBase = new Box(0.35f, 0.01f, 0.12f, primflags, metalTexture);
		trainModelTG.addChild(botBaseTG);
		botBaseTG.addChild(bottomBase);

		// Chimney base
		Transform3D pipeTM = new Transform3D();
		pipeTM.setTranslation(new Vector3d(-0.1, 0.1, 0.0));
		TransformGroup pipeTG = new TransformGroup(pipeTM);
		Box pipe = new Box(0.05f, 0.15f, 0.05f, greyAPP);
		trainModelTG.addChild(pipeTG);
		pipeTG.addChild(pipe);

		// Back base
		Transform3D backTM = new Transform3D();
		backTM.setTranslation(new Vector3d(0.35, 0.05, 0.0));
		TransformGroup backTG = new TransformGroup(backTM);
		Box backBase = new Box(0.1f, 0.15f, 0.12f, blueAPP);
		trainModelTG.addChild(backTG);
		backTG.addChild(backBase);

		// Face of base
		Transform3D faceTM = new Transform3D();
		faceTM.setTranslation(new Vector3d(-0.18, 0.0, 0.0));
		Transform3D rotateFace = new Transform3D();
		rotateFace.rotZ(Math.PI / 2);
		faceTM.mul(rotateFace);
		;
		TransformGroup faceTG = new TransformGroup(faceTM);
		Cylinder face = new Cylinder(0.08f, 0.2f, greyAPP);
		trainModelTG.addChild(faceTG);
		faceTG.addChild(face);

		// Making my own shapes
		Point3f e = new Point3f(1.0f, 0.0f, 0.0f); // right
		Point3f s = new Point3f(0.0f, 0.0f, 1.0f); // bottom
		Point3f w = new Point3f(-1.0f, 0.0f, 0.0f); // left
		Point3f n = new Point3f(0.0f, 0.0f, -1.0f); // top
		Point3f t = new Point3f(0.0f, 0.5f, 0.0f); // up
		// Setting coordinates of my shape
		TriangleArray pyramidGeometry = new TriangleArray(15, TriangleArray.COORDINATES);
		pyramidGeometry.setCoordinate(0, e);
		pyramidGeometry.setCoordinate(1, t);
		pyramidGeometry.setCoordinate(2, s);
		pyramidGeometry.setCoordinate(3, s);
		pyramidGeometry.setCoordinate(4, t);
		pyramidGeometry.setCoordinate(5, w);
		pyramidGeometry.setCoordinate(6, w);
		pyramidGeometry.setCoordinate(7, t);
		pyramidGeometry.setCoordinate(8, n);
		pyramidGeometry.setCoordinate(9, n);
		pyramidGeometry.setCoordinate(10, t);
		pyramidGeometry.setCoordinate(11, e);
		pyramidGeometry.setCoordinate(12, e);
		pyramidGeometry.setCoordinate(13, s);
		pyramidGeometry.setCoordinate(14, w);

		GeometryInfo geometryInfo = new GeometryInfo(pyramidGeometry);
		NormalGenerator ng = new NormalGenerator();
		ng.generateNormals(geometryInfo);
		GeometryArray result = geometryInfo.getGeometryArray();
		// Making a 3D pyramid
		Shape3D pyramid3D = new Shape3D(result, greyAPP);
		Transform3D pyramidTM = new Transform3D();
		// rotates pyramid
		pyramidTM.rotY(Math.PI / 4);
		// scales it to size
		pyramidTM.setScale(new Vector3d(0.15, 0.1, 0.15));
		// positions it to the right part
		pyramidTM.setTranslation(new Vector3d(0.35, 0.20, 0.0));
		TransformGroup roofTG = new TransformGroup(pyramidTM);
		trainModelTG.addChild(roofTG);
		roofTG.addChild(pyramid3D);

		// Return the model of the train
		return trainModelTG;
	}
}
