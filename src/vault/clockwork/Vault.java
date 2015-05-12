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
package vault.clockwork;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import vault.clockwork.actors.TurretActor;
import vault.clockwork.system.ConsoleAction;

/**
 * Vault of globally accessible game members.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public abstract class Vault {
	/**
	 * Globally accessible shader programs.
	 */
	static public ShaderProgram comicShader;
	
	/**
	 * Preload vault assets.
	 */
	static public void preload() {
		// load shader
		comicShader = new ShaderProgram(
			Gdx.files.internal("assets/shaders/comic.vert"),
			Gdx.files.internal("assets/shaders/comic.frag")
		);
		
		// add scene spam command
		Game.console.commands.put("spam", new ConsoleAction() {
			@Override
			public String perform(String[] params) {
				for(int i = 0; i < 10*10; i++) {
					float x = (float)(i % 10 - 5),
						y = (float)(i / 10 - 5);
					
					Game.scene.ACTION_1.add(new TurretActor(i))
						.setPosition(new Vector2(
							x * 64.f + Game.mainCamera.position.x,
							y * 64.f + Game.mainCamera.position.y
						)
					);
				}
				
				return "Spamming scene by 100 instances...";
			}
		});
	}
	
	/**
	 * Unload vault assets.
	 */
	static public void unload() {
		comicShader.dispose();
	}
}