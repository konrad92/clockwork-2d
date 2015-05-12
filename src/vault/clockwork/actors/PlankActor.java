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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import vault.clockwork.Game;
import vault.clockwork.system.Physics;

/**
 *
 * @author Agnieszka Makowska https://github.com/Migemiley
 */
public class PlankActor extends ObstacleActor{
	private final Body body;
	private final Fixture fixture;
	boolean raising;
	float timer = 0.f;
	String name = "wood-bounce.ogg";
	
	private Vector2 position = new Vector2(1.f, 0.f);
	
	/**
	 * Ctor.
	 * @param id 
	 */
	public PlankActor(int id, int velocity, int x, int y){
		super(id);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(30.f * Physics.SCALE, 80.f * Physics.SCALE);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.KinematicBody;
		bodyDef.linearVelocity.set(0, velocity);
		bodyDef.position.set(200 * Physics.SCALE, 100 * Physics.SCALE);
		body = Game.physics.world.createBody(bodyDef);
		fixture = body.createFixture(shape, 2.f);
		fixture.setUserData(this);
		
		shape.dispose();
		
		// dodanie dzwiekow do odegrania
		impactSounds.addAll(
			Game.assets.get("assets/sounds/wood-bounce.ogg", Sound.class),
			Game.assets.get("assets/sounds/hit1.ogg", Sound.class),
			Game.assets.get("assets/sounds/hit2.ogg", Sound.class)
		);
	}
	
	@Override
	public void update(float delta) {
		timer += delta;
		
		body.setTransform(
			position.x,
			position.y + 80.f * Physics.SCALE * (float)Math.sin(timer * Math.PI),
			0.f
		);
	}
}
	

