/*
 * The MIT License
 *
 * Copyright 2015 Konrad Nowakowski https://github.com/konrad92.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package vault.clockwork.actors;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import vault.clockwork.Game;
import vault.clockwork.Vault;
import vault.clockwork.editor.PropSerialized;
import vault.clockwork.editor.props.PlankProp;
import vault.clockwork.scene.Actor;
import vault.clockwork.system.Physics;

/**
 * Przeszkadzajka(deska) - Kinematic
 * @author Agnieszka Makowska https://github.com/Migemiley
 */
public class PlankActor extends ObstacleActor{
	private final Body body;
	private final Fixture fixture;
	boolean raising;
	float timer = 0.f;
        
	private final Sprite sprPlank;
	
	public final Vector2 moveDirection = Vector2.Y.cpy();
	public float height = 280.f;
	public float rotationSpeed = 0.f;
	
	private float margin = 0.f;
	
	// ten konsturktor wywoluje edytor
	// przekierowywujemy konstruktor, na inny poprzez konwersje typu danych
	// w tym wypadku z PropSerialized do PlankProp, ponieważ PlankProp
	// dziedziczy po klasie z PropSerialized.
	public PlankActor(PropSerialized prop) {
		this((PlankProp)prop);
	}
	
	public PlankActor(PlankProp prop) {
		this(prop.id, prop.width, prop.height);
		
		// zmieniamy parametry ciala
		setPosition(prop.position);
		setRotation(prop.angle);
		
		// wysoksoc prosuzania sie deski
		height = prop.move_height;
		
		// zmieniamy kierunek poruszania sie deseczki
		// wektor MUSI byc ZNORMALIZOWANY (metoda `.nor()`)
		moveDirection.set(
			prop.move_direction_x,
			prop.move_direction_y
		).nor(); // normalizujemy wektor
		
		// szybkosc obrotu aktora
		rotationSpeed = prop.rotate_speed;
	}
	
	/**
	 * Ctor.
	 * @param id 
	 */
	public PlankActor(int id, float x, float y){
		super(id);
                		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(x * Physics.SCALE, y * Physics.SCALE);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.KinematicBody;
		body = Game.physics.world.createBody(bodyDef);
		fixture = body.createFixture(shape, 2.f);
		fixture.setUserData(this);
		
		shape.dispose();
		
		// calculate the margin
		margin = (2*x)*.32f;
		
		// create the plank sprite
		sprPlank = new Sprite(Game.assets.get("assets/longtrunk.png", Texture.class));
		sprPlank.setBounds(0.f, 0.f, 2*x + margin, 2*y);
		sprPlank.setOrigin(x, y);
		
		// dodanie dzwiekow do odegrania
		impactSounds.add(
			Game.assets.get(Vault.SOUND_WOODBOUNCE, Sound.class)
		);
	}

	@Override
	public void update(float delta) {
		timer += delta;
		body.setAngularVelocity(rotationSpeed);
		
		body.setLinearVelocity(
			height * Physics.SCALE * moveDirection.x * (float)Math.sin(timer * Math.PI),
			height * Physics.SCALE * moveDirection.y * (float)Math.sin(timer * Math.PI)
		);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		/*sprPlank.setCenter(
			body.getPosition().x * Physics.SCALE_INV,
			body.getPosition().y * Physics.SCALE_INV
		);*/
		sprPlank.setPosition(
			body.getPosition().x * Physics.SCALE_INV - sprPlank.getOriginX(),
			body.getPosition().y * Physics.SCALE_INV - sprPlank.getOriginY()
		);
		
		// tutaj zmieniamy obrot sprite
		// dlaczego? a no przed samym rysowaniem samego sprite
		// mnozymy przez MathUtils.radiansToDegrees, bo setROtation przyjmuje
		//	obracanie sie w degrees, a getAngle pobiera wartosc w radians
		sprPlank.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		
		batch.begin();
		sprPlank.draw(batch);
		batch.end();
	}
	
	/**
	 * @see Actor#getPosition() 
	 * @return 
	 */
	@Override
	public Vector2 getPosition() {
		return body.getTransform().getPosition().scl(Physics.SCALE_INV);
	}
	
	/**
	 * @see Actor#setPosition(com.badlogic.gdx.math.Vector2) 
	 * @param newPosition 
	 */
	@Override
	public void setPosition(Vector2 newPosition) {
		body.setTransform(newPosition.cpy().scl(Physics.SCALE), body.getTransform().getRotation());
	}
	
	/**
	 * @see Actor#getRotation() 
	 * @return 
	 */
	@Override
	public float getRotation() {
		return body.getTransform().getRotation();
	}
	
	/**
	 * @see Actor#setRotation(float) 
	 * @param newAngle
	 */
	@Override
	public void setRotation(float newAngle) {
		body.setTransform(body.getPosition(), newAngle * MathUtils.degreesToRadians);
	}
	
	@Override
	public void dispose() {
		Game.physics.world.destroyBody(body);
	}
}
