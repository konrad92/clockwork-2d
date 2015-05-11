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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import vault.clockwork.scene.Actor;

/**
 * Aktor posiadajacy dzwieki do odegrania w czasie uderzenia.
 * @author Agnieszka Makowska https://github.com/Migemiley
 */
public abstract class ObstacleActor extends Actor {
	/**
	 * Tablica dzwiekow do odegrania w czasie kolizji.
	 */
	protected final Array<Sound> impactSounds = new Array<>();
	
	/**
	 * Ctor.
	 * @param id Unikalny identyfikator aktora. 
	 */
	public ObstacleActor(int id){
		super(id);
	}
	
	/**
	 * Odegranie losowego dzwieku uderzenia z tablicy.
	 */
	public void playImpactSound(){
		Sound impactSnd = impactSounds.random();
		
		// odegranie dzwieki jezeli istnieje
		if(impactSnd != null) {
			impactSnd.play();
		}
	}
}
