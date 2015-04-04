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
import com.badlogic.gdx.graphics.g2d.Batch;
import vault.clockwork.Game;
import vault.clockwork.scene.Actor;

/**
 *
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class TrActor extends Actor {
    private Texture angel;
    private float frame = 0.f;
    
    public TrActor(int id) {
        super(id);
    }
    
    @Override
    public void create() {
        System.out.println("TrActor (" + String.valueOf(this.id) + ") - create");
        this.angel = Game.assets.get("assets/steamangel.png", Texture.class);
    }

    @Override
    public void update(float delta) {
        this.rotate.setFromAxis(0.f, 0.f, 1.f, 55.f*frame);
        
        frame += delta;
    }

    @Override
    public void render(Batch batch) {
        batch.begin();
        batch.setTransformMatrix(this.world());
        batch.draw(this.angel, -angel.getWidth()/2, -angel.getHeight()/2);
        batch.end();
    }
}
