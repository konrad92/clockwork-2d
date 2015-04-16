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

import static vault.clockwork.systems.PhysicsWorldSystem.SCALE_FACTOR;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import vault.clockwork.Game;
import vault.clockwork.scene.PhysicsActor;

/**
 *
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class GroundActor extends PhysicsActor {
    public GroundActor(int id) {
        super(id, Game.physics.world);
        
        this.position.set(400.f, 400.f, 0.f);
        
        this.shape = new CircleShape();
        this.shape.setRadius(32.f * SCALE_FACTOR);
        this.bodyDef.type = BodyDef.BodyType.StaticBody;
        this.fixtureDef.shape = this.shape;
        this.fixtureDef.density = 0.5f;
        this.fixtureDef.friction = 0.4f;
        this.fixtureDef.restitution = 0.6f;
    }
    
    @Override
    public void render(Batch batch) {
    }
}
