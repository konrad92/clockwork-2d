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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import vault.clockwork.Game;
import vault.clockwork.scene.Actor;

/**
 * Static ground physics actor.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class GroundActor extends ObstacleActor {
	private final Body body;
	private final Fixture fixture;
	
	/**
	 * Ctor.
	 * Create new physic ground body on the world.
	 * @see Actor#Actor(int) 
	 * @param id Turret unique id.
	 */
	public GroundActor(int id) {
		super(id);
		
		// body shape
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(100.f, .2f);
		
		// create physics body
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(0, -2.f);
		body = Game.physics.world.createBody(bodyDef);
		fixture = body.createFixture(shape, 0.f);
		fixture.setRestitution(.5f);
		fixture.setUserData(this);
		
		shape.dispose();
		
		// dodanie dzwiekow do odegrania
		impactSounds.add(
			Game.assets.get("assets/wood-bounce.mp3", Sound.class)
		);
	}
}
