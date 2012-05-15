package com.gra.rozgrywka;

import java.util.List;

/**
 * @author Szpada
 * 
 * Boss bossow - jednostka posiadajaca inna mechanike ruchu - zamiast schodzic tylko w dol w kierunku olimpu
 * chodzi na boki oraz teleportuje sie. Glwna jego bronia bedzie atakowanie z dystansu oraz 
 * wskrzeszanie jednostek. Dodatkowo moze sobie odnawiac zycie.
 */
public class BossSprite extends EnemySprite{

	public BossSprite(List<EnemySprite> enemies, GameView gameView,
			enemyType tp, int x, int y, List<EnemyAttack> ea) {
		super(enemies, gameView, tp, x, y, ea);
		// TODO Auto-generated constructor stub
	}
}
