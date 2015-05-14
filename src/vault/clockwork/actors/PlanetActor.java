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
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import vault.clockwork.Game;
import vault.clockwork.Vault;
import vault.clockwork.scene.Actor;
import vault.clockwork.system.Physics;

/**
 * Przeszkadzajka(deska) - Kinematic
 * @author Agnieszka Makowska https://github.com/Migemiley
 */
public class PlanetActor extends ObstacleActor{
	private final Body body;
	private final Fixture fixture;
	boolean raising;
	float timer = 0.f;
        float velocity = 100.f;
	private final Sprite sprPlanet;
	
	private Vector2 position = new Vector2(10.f, 3.f);
	
	/**
	 * Ctor.
	 * @param id 
	 */
	public PlanetActor(int id){
		super(id);
		
		PolygonShape shape = new PolygonShape();
		shape.setRadius(32.f * Physics.SCALE);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.KinematicBody;
		
		
		bodyDef.position.set(1.f, 1.f);
		body = Game.physics.world.createBody(bodyDef);
		fixture = body.createFixture(shape, 2.f);
		fixture.setUserData(this);
		
		shape.dispose();
		
		// create the plank sprite
		sprPlanet = new Sprite(Game.assets.get("assets/planet.png", Texture.class));
		sprPlanet.setBounds(-42.f, -42.f, 150.f, 150.f);
		sprPlanet.setOriginCenter();
		sprPlanet.setRotation(0.f);		
	}

	@Override
	public void update(float delta) {
		timer += delta;
		body.setTransform(
			position.x - velocity * Physics.SCALE * (float)Math.sin(timer*0.01f * Math.PI)*100,
			position.y + velocity * Physics.SCALE * (float)Math.sin(timer * 0.1f * Math.PI),
                        0.f
		);                            
	}
	
	
	@Override
	public void draw(SpriteBatch batch) {
		sprPlanet.setCenter(
			body.getPosition().x * Physics.SCALE_INV,
			body.getPosition().y * Physics.SCALE_INV
		);
		
		batch.begin();
		sprPlanet.draw(batch);
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
}
