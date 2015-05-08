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
package vault.clockwork.screens;

import static com.badlogic.gdx.Gdx.gl;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import vault.clockwork.Game;
import vault.clockwork.actors.BlockActor;
import vault.clockwork.actors.DebugScreenActor;
import vault.clockwork.actors.GroundActor;
import vault.clockwork.actors.TurretActor;

/**
 * Playable stage screen.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class StageScreen implements GameScreen {
	Body body;
	Fixture fixture;
	
	@Override
	public void prepare() {
		Game.assets.load("assets/turret.png", Texture.class);
	}

	@Override
	public void show() {
		// prepare scene camera
		Game.mainCamera.setToOrtho(false);
		Game.mainCamera.translate(-400.f, -300.f);
		Game.mainCamera.update();
		
		// create turret actor
		Game.scene.DEBUG.add(new DebugScreenActor());
		Game.scene.ACTION_1.add(new GroundActor(-1));
		Game.scene.ACTION_1.add(new TurretActor(0));
		Game.scene.ACTION_1.add(new TurretActor(1));
		Game.scene.ACTION_1.add(new TurretActor(2));
		Game.scene.ACTION_1.add(new TurretActor(3));
		Game.scene.ACTION_1.add(new TurretActor(4));
		Game.scene.ACTION_1.add(new TurretActor(5));
		
		Game.scene.ACTION_2.add(new BlockActor(0));
	}

	@Override
	public void render(float delta) {
        // clear target buffer
        gl.glClearColor(0.1f, 0.2f, 0.1f, 1.f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		// perform game systems
		Game.performSystems();
	}

	@Override
	public void dispose() {
	}
}
