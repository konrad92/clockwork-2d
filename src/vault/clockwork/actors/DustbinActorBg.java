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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import vault.clockwork.Game;
import vault.clockwork.system.Physics;

/**
 *
 * @author Qiku
 */
public class DustbinActorBg extends ObstacleActor{
    	private Body body;
        private Sprite binsbg;

	public DustbinActorBg(int id, float x, float y){
		super(id);        
                         		
		PolygonShape dustbin = new PolygonShape();             

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(x * Physics.SCALE, y * Physics.SCALE);
		body = Game.physics.world.createBody(bodyDef);
		
                
                binsbg = new Sprite(Game.assets.get("assets/dbinbg.png", Texture.class));
		binsbg.setBounds(1.f, 1.f, 250.f, 300.f);
		binsbg.setOriginCenter();  
                             
	}
        @Override
	public void draw(SpriteBatch batch) {
                binsbg.setCenter(
			(body.getPosition().x * Physics.SCALE_INV)+76.f ,
			(body.getPosition().y * Physics.SCALE_INV)+125.f
		);
		batch.begin();
                binsbg.draw(batch);
		batch.end();
	}
        @Override
        public void dispose() {
            Game.physics.world.destroyBody(body);
        }
}
