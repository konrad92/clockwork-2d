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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import vault.clockwork.Game;
import vault.clockwork.scene.Actor;

/**
 * Turret actor.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class TurretActor extends Actor {
    public TurretActor(int id) {
        super(id);
    }
    
    @Override
    public void create() {
    }

    @Override
    public void update(float delta) {
        float diffx = ((float)Gdx.input.getX() - 400.f)*0.5f,
              diffy = ((float)Gdx.input.getY() - 300.f)*0.5f;
        
        Game.mainCamera.position.x = this.position.x + diffx;
        Game.mainCamera.position.y = this.position.y + diffy;
    }

    @Override
    public void render(Batch batch) {
    }

    @Override
    public void debug(ShapeRenderer gizmos) {
        gizmos.setTransformMatrix(this.local());
        gizmos.begin(ShapeRenderer.ShapeType.Line);
        gizmos.setColor(Color.YELLOW);
        gizmos.rect(-5.f, -5.f, 10.f, 10.f);
        gizmos.end();
    }
}
