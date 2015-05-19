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
import vault.clockwork.Vault;
import vault.clockwork.system.Physics;

/**
 *
 * @author Agnieszka Makowska https://github.com/Migemiley
 */
public class WielokatActor extends ObstacleActor{
	private Body body;
	private Fixture fixture;

	public WielokatActor(int id){
		super(id);
		
		float[] vertices = new float[] {
		20.f * Physics.SCALE, 0.f * Physics.SCALE,
		70.f * Physics.SCALE, 0.f * Physics.SCALE,
		75.f * Physics.SCALE, 20.f * Physics.SCALE,
		75.f * Physics.SCALE, 40.f * Physics.SCALE,
		55.f * Physics.SCALE, 65.f * Physics.SCALE,
		30.f * Physics.SCALE, 65.f * Physics.SCALE,
		10.f * Physics.SCALE, 50.f * Physics.SCALE,
		5.f * Physics.SCALE, 20.f * Physics.SCALE,
		};
		
		PolygonShape wielokat = new PolygonShape();
		wielokat.set(vertices);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(0.f * Physics.SCALE, -180.f * Physics.SCALE);
		body = Game.physics.world.createBody(bodyDef);
		fixture = body.createFixture(wielokat, 2.f);
		fixture.setUserData(this);
		
		wielokat.dispose();
		
		// dodanie dzwiekow do odegrania
		impactSounds.add(
			Game.assets.get(Vault.SOUND_WOODBOUNCE, Sound.class)
		);
	}
	
	@Override
	public void dispose() {
		Game.physics.world.destroyBody(body);
	}
	
}
