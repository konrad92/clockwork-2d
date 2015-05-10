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

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import vault.clockwork.Game;
import vault.clockwork.scene.Actor;
import vault.clockwork.system.Physics;

/**
 *
 * @author Qiku
 */
public class DustbinActor extends Actor{
	private Body body;
	private Fixture fixture;
        private Fixture fixture2;
        private Fixture fixture3;

	public DustbinActor(int id, float wysokosc, float szerokosc, float pochyl, 
                            float grub, float x, float y){
		super(id);
                
             //   float wysokosc=100.f;
             //   float szerokosc=300.f;
             //   float pochyl=50.f;
             //   float grub=20.f;
		
		float[] vertices = new float[] {
		szerokosc * Physics.SCALE, 0.f * Physics.SCALE,
		0.f * Physics.SCALE, 0.f * Physics.SCALE,
		szerokosc * Physics.SCALE, grub * Physics.SCALE,
		0.f * Physics.SCALE, grub * Physics.SCALE,
		};
                
                float[] vertices2 = new float[] {
		0.f* Physics.SCALE, grub * Physics.SCALE,
		-pochyl * Physics.SCALE, wysokosc * Physics.SCALE,
		grub * Physics.SCALE, grub * Physics.SCALE,
		(grub-pochyl) * Physics.SCALE, wysokosc * Physics.SCALE,
		};
                
                float[] vertices3 = new float[] {
		szerokosc * Physics.SCALE, grub * Physics.SCALE,
		(szerokosc+pochyl) * Physics.SCALE, wysokosc * Physics.SCALE,
		(szerokosc-grub) * Physics.SCALE, grub * Physics.SCALE,
		(szerokosc-grub+pochyl) * Physics.SCALE, wysokosc * Physics.SCALE,
		};
		
		PolygonShape dustbin = new PolygonShape();
		dustbin.set(vertices);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(x * Physics.SCALE, y * Physics.SCALE);
		body = Game.physics.world.createBody(bodyDef);
		
                dustbin.set(vertices);
                fixture = body.createFixture(dustbin, 2.f);
                fixture.setUserData(this);
                
                dustbin.set(vertices2);
                fixture = body.createFixture(dustbin, 2.f);
                
                dustbin.set(vertices3);
                fixture = body.createFixture(dustbin, 2.f);
                
                		
		dustbin.dispose();		
	}	
}
