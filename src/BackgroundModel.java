
/**
 * Description of the Class:
 * 
 * This class returns a transform group which has 3D shapes added to it to make a 3D background model.
 * Includes rotating sun
 * 
 * Created By Khoa Phan
 */

import java.awt.Container;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

public class BackgroundModel {

	public TransformGroup createTracks() {

		// set bounds
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE);

		// Creating transformation groups
		TransformGroup backgroundTG = new TransformGroup();
		TransformGroup blocakgeTG = new TransformGroup();
		backgroundTG.addChild(blocakgeTG);

		/*
		 * Train Colours
		 */
		Color3f ambientColour = new Color3f(0.0f, 0.8f, 0.1f);
		Color3f emissiveColour = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f diffuseColour = new Color3f(0.5f, 0.5f, 0.5f);
		Color3f specularColour = new Color3f(0.8f, 0.8f, 0.8f);
		float shininess = 128.0f;
		// Generate the appearance and apply material to cube
		Appearance darkGreenAPP = new Appearance();
		darkGreenAPP.setMaterial(new Material(ambientColour, emissiveColour, diffuseColour, specularColour, shininess));

		Color3f ambientColour2 = new Color3f(0.1f, 0.2f, 0.2f);
		Color3f emissiveColour2 = new Color3f(0.35f, 0.15f, 0.05f);
		Color3f diffuseColour2 = new Color3f(0.8f, 0.8f, 0.8f);
		Color3f specularColour2 = new Color3f(0.0f, 0.0f, 0.0f);
		float shininess2 = 128.0f;
		// Generate the appearance and apply material to cube
		Appearance brownAPP = new Appearance();
		brownAPP.setMaterial(
				new Material(ambientColour2, emissiveColour2, diffuseColour2, specularColour2, shininess2));

		Color3f ambientColour3 = new Color3f(0.5f, 0.5f, 0.5f);
		Color3f emissiveColour3 = new Color3f(0.65f, 0.65f, 0.65f);
		Color3f diffuseColour3 = new Color3f(0.8f, 0.8f, 0.8f);
		Color3f specularColour3 = new Color3f(0.8f, 0.8f, 0.8f);
		float shininess3 = 128.0f;
		// Generate the appearance and apply material to cube
		Appearance greyAPP = new Appearance();
		greyAPP.setMaterial(new Material(ambientColour3, emissiveColour3, diffuseColour3, specularColour3, shininess3));

		Color3f ambientColour4 = new Color3f(0.6f, 0.6f, 0.0f);
		Color3f emissiveColour4 = new Color3f(0.8f, 0.4f, 0.0f);
		Color3f diffuseColour4 = new Color3f(0.8f, 0.8f, 0.8f);
		Color3f specularColour4 = new Color3f(0.2f, 0.2f, 0.2f);
		float shininess4 = 1.0f;
		Appearance yellowAPP = new Appearance();
		yellowAPP.setMaterial(
				new Material(ambientColour4, emissiveColour4, diffuseColour4, specularColour4, shininess4));

		// Set up the texture map
		TextureLoader load1 = new TextureLoader("grass_tex.jpg", "LUMINANCE", new Container());
		Texture texture = load1.getTexture();
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);
		texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));
		// Set up the texture attributes
		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.BLEND);
		Appearance grassTexture = new Appearance();
		grassTexture.setTexture(texture);
		grassTexture.setTextureAttributes(texAttr);
		// set up the material
		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
		Color3f green = new Color3f(0.0f, 0.6f, 0.0f);
		grassTexture.setMaterial(new Material(white, green, green, green, 0.8f));
		int primflags1 = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

		// Set up the texture map
		TextureLoader load2 = new TextureLoader("tree_tex.jpg", "LUMINANCE", new Container());
		Texture text = load2.getTexture();
		text.setBoundaryModeS(Texture.WRAP);
		text.setBoundaryModeT(Texture.WRAP);
		text.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));
		// Set up the texture attributes
		TextureAttributes texA = new TextureAttributes();
		texA.setTextureMode(TextureAttributes.MODULATE);
		Appearance treeTexture = new Appearance();
		treeTexture.setTexture(text);
		treeTexture.setTextureAttributes(texA);
		// set up the material
		Color3f brown = new Color3f(0.25f, 0.065f, 0.025f);
		treeTexture.setMaterial(new Material(white, brown, brown, brown, 0.8f));
		int primflags2 = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

		/*
		 * Shapes for Background Model
		 * 
		 */
		// Grass floor
		Box floor = new Box(2.5f, 0.15f, 0.5f, primflags1, grassTexture);
		Transform3D floorTM = new Transform3D();
		floorTM.setTranslation(new Vector3d(-0.1, -0.18, -0.25));
		TransformGroup floorTG1 = new TransformGroup(floorTM);
		backgroundTG.addChild(floorTG1);
		floorTG1.addChild(floor);

		// track1
		Box track1 = new Box(2.4f, 0.02f, 0.02f, greyAPP);
		Transform3D trackTM1 = new Transform3D();
		trackTM1.setTranslation(new Vector3d(-0.1, 0.0, -0.1));
		TransformGroup trackTG1 = new TransformGroup(trackTM1);
		backgroundTG.addChild(trackTG1);
		trackTG1.addChild(track1);
		// track2
		Box track2 = new Box(2.4f, 0.02f, 0.02f, greyAPP);
		Transform3D trackTM2 = new Transform3D();
		trackTM2.setTranslation(new Vector3d(-0.1, 0.0, 0.1));
		TransformGroup trackTG2 = new TransformGroup(trackTM2);
		backgroundTG.addChild(trackTG2);
		trackTG2.addChild(track2);
		// Make wood pieces
		for (float i = -2.5f; i < 2.3f; i += 0.1f) {

			Box detail1 = new Box(0.02f, 0.015f, 0.15f, brownAPP);
			// Translate down on xAxis
			Transform3D temp1 = new Transform3D();
			temp1.setTranslation(new Vector3d(i, -0.01, 0.0));
			TransformGroup tempTG1 = new TransformGroup(temp1);

			backgroundTG.addChild(tempTG1);
			tempTG1.addChild(detail1);

		}

		// Making Background Trees
		for (float i = -1.5f; i < 1.5f; i += 0.3f) {
			Cylinder treePart1 = new Cylinder(0.05f, 0.25f, primflags2, treeTexture);
			Transform3D treeTM1 = new Transform3D();
			treeTM1.setTranslation(new Vector3d(i, 0.1, -0.5));
			TransformGroup treeTG1 = new TransformGroup(treeTM1);

			Cone treePart2 = new Cone(0.1f, 0.15f, darkGreenAPP);
			Transform3D treeTM2 = new Transform3D();
			treeTM2.setTranslation(new Vector3d(i, 0.28, -0.5));
			TransformGroup treeTG2 = new TransformGroup(treeTM2);

			backgroundTG.addChild(treeTG1);
			backgroundTG.addChild(treeTG2);
			treeTG1.addChild(treePart1);
			treeTG2.addChild(treePart2);
		}
		// Creating a sun
		Transform3D sunTM1 = new Transform3D();
		sunTM1.setTranslation(new Vector3d(1.0, 0.1, -1.2));
		// sunTM.setScale(new Vector3d(6.0, 1.1, 0.2));
		TransformGroup sunTG = new TransformGroup(sunTM1);

		// creating a rotation interpolator for cubeTG0
		TransformGroup rotatingSunTG = new TransformGroup();
		// allows rotation to work
		rotatingSunTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha rotationAlpha0 = new Alpha(-1, 8000);
		// Setting up rotation transform group
		Transform3D sunTM2 = new Transform3D();
		sunTM2.setTranslation(new Vector3d(-1.5, 0.0, .0));
		Transform3D rotatingTM = new Transform3D();
		rotatingTM.rotX(-Math.PI / 2);
		sunTM2.mul(rotatingTM);
		RotationInterpolator rotator0 = new RotationInterpolator(rotationAlpha0, rotatingSunTG, sunTM2,
				(float) -Math.PI * (2.0f), 0.0f);
		rotator0.setSchedulingBounds(bounds);

		Sphere sun = new Sphere(0.5f, yellowAPP);
		backgroundTG.addChild(sunTG);
		sunTG.addChild(rotatingSunTG);
		rotatingSunTG.addChild(rotator0);
		rotatingSunTG.addChild(sun);

		// Creating a moon
		Transform3D moonTM1 = new Transform3D();
		moonTM1.setTranslation(new Vector3d(-2.0, 0.1, -1.2));
		// sunTM.setScale(new Vector3d(6.0, 1.1, 0.2));
		TransformGroup moonTG = new TransformGroup(moonTM1);

		// creating a rotation interpolator for cubeTG0
		TransformGroup rotatingMoonTG = new TransformGroup();
		// allows rotation to work
		rotatingMoonTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha rotationAlpha2 = new Alpha(-1, 8000);
		// Setting up rotation transform group
		Transform3D moonTM2 = new Transform3D();
		moonTM2.setTranslation(new Vector3d(1.5, 0.0, .0));
		Transform3D rotateMoonTM = new Transform3D();
		rotateMoonTM.rotX(Math.PI / 2);
		moonTM2.mul(rotateMoonTM);
		RotationInterpolator rotator2 = new RotationInterpolator(rotationAlpha2, rotatingMoonTG, moonTM2,
				(float) Math.PI * (2.0f), 0.0f);
		rotator2.setSchedulingBounds(bounds);

		Sphere moon = new Sphere(0.2f, greyAPP);
		backgroundTG.addChild(moonTG);
		moonTG.addChild(rotatingMoonTG);
		rotatingMoonTG.addChild(rotator2);
		rotatingMoonTG.addChild(moon);

		// Return the background model
		return backgroundTG;
	}
}
