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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import vault.clockwork.Game;
import vault.clockwork.Vault;
import vault.clockwork.editor.PropSerialized;
import vault.clockwork.system.Physics;

/**
 * Poduszka z większą zdolnością odbijania - Static
 * @author Agnieszka Makowska https://github.com/Migemiley
 */
public class PillowActor extends ObstacleActor{
	private final Body body;
	private final Fixture fixture;
	private final Sprite sprPillow;
	
	private Vector2 position = new Vector2(1.f, 0.f);
	
	public PillowActor(PropSerialized prop) {
		this(prop.id);
		
		// load position
		setPosition(prop.position);
	}
	
	/**
	 * Ctor.
	 * @param id 
	 */
	public PillowActor(int id){
		super(id);
		
		float[] vertices = new float[] {
		0.f * Physics.SCALE, 10.f * Physics.SCALE,
		30.f * Physics.SCALE, 0.f * Physics.SCALE,
		90.f * Physics.SCALE, 0.f * Physics.SCALE,
		120.f * Physics.SCALE, 10.f * Physics.SCALE,
		120.f * Physics.SCALE, 30.f * Physics.SCALE,
		90.f * Physics.SCALE, 55.f * Physics.SCALE,
		30.f * Physics.SCALE, 55.f * Physics.SCALE,
		0.f * Physics.SCALE, 30.f * Physics.SCALE,
		};
		
		PolygonShape shape = new PolygonShape();
		shape.set(vertices);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(200 * Physics.SCALE, -180 * Physics.SCALE);
		body = Game.physics.world.createBody(bodyDef);
		fixture = body.createFixture(shape, 2.f);
		fixture.setUserData(this);
		
		shape.dispose();
		
		// create the plank sprite
		sprPillow = new Sprite(Game.assets.get("assets/poducha.png", Texture.class));
		sprPillow.setBounds(-42.f, -42.f, 120.f, 60.f);
		sprPillow.setOriginCenter();
		
		// dodanie dzwiekow do odegrania
		impactSounds.add(
			Game.assets.get(Vault.SOUND_WOODBOUNCE, Sound.class)
		);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
//		sprPillow.setCenter(
//			body.getPosition().x * Physics.SCALE_INV,
//			body.getPosition().y * Physics.SCALE_INV
//		);
		
		sprPillow.setPosition(
			body.getPosition().x * Physics.SCALE_INV,
			body.getPosition().y * Physics.SCALE_INV
		);
		
		batch.begin();
		sprPillow.draw(batch);
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
		body.getTransform().setRotation(newAngle);
	}
	
	@Override
	public void dispose() {
		Game.physics.world.destroyBody(body);
	}
}

	
