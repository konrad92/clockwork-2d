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
import vault.clockwork.scene.Actor;
import vault.clockwork.system.Physics;

/**
 *
 * @author Qiku
 */
public class DustbinActor extends ObstacleActor{
	private Body body;
	protected Fixture fixture;
        private Sprite binspr;
		
	
	public DustbinActor(PropSerialized prop) {
		this(prop.id);
		
		// load position
		setPosition(prop.position);
	}
	
	public DustbinActor(int id){
		super(id);
                float grub=6.f;
                float wysokosc=250.f;
                float szerokosc=150.f;
                float pochyl=25.f;            
                         		
				
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
                
                float[] hitbox = new float[] {
                    (szerokosc-20.f) * Physics.SCALE, (20.f-grub) * Physics.SCALE,
                    20.f * Physics.SCALE, (20.f-grub) * Physics.SCALE,
                    (szerokosc-20.f) * Physics.SCALE, grub * Physics.SCALE,
                    20.f * Physics.SCALE, grub * Physics.SCALE,
		};
		
		PolygonShape dustbin = new PolygonShape();             

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(0 * Physics.SCALE, 0 * Physics.SCALE);
		body = Game.physics.world.createBody(bodyDef);
		
                
                dustbin.set(vertices);
                fixture = body.createFixture(dustbin, 2.f);     
                fixture.setUserData(this);
                
                dustbin.set(vertices2);
                fixture = body.createFixture(dustbin, 2.f);
                fixture.setUserData(this);
                
                dustbin.set(vertices3);
                fixture = body.createFixture(dustbin, 2.f);
                fixture.setUserData(this);
                
                dustbin.set(hitbox);
                fixture = body.createFixture(dustbin, 2.f);
                fixture.setUserData(this);
                	
		dustbin.dispose();
                
                binspr = new Sprite(Game.assets.get("assets/dbin.png", Texture.class));
		binspr.setBounds(1.f, 1.f, 250.f, 300.f);
		binspr.setOriginCenter();  
                             
                impactSounds.addAll(
			Game.assets.get(Vault.SOUND_KOSZ1, Sound.class),
			Game.assets.get(Vault.SOUND_KOSZ2, Sound.class),
			Game.assets.get(Vault.SOUND_KOSZ3, Sound.class),
			Game.assets.get(Vault.SOUND_KOSZ4, Sound.class),
			Game.assets.get(Vault.SOUND_KOSZ5, Sound.class)
                );               
	}
        @Override
	public void draw(SpriteBatch batch) {
		binspr.setCenter(
			(body.getPosition().x * Physics.SCALE_INV)+76.f ,
			(body.getPosition().y * Physics.SCALE_INV)+125.f
		);
		batch.begin();
		binspr.draw(batch);
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
