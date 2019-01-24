package com.myslotmachine.main;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.texturepack.TexturePack;
import org.andengine.util.texturepack.TexturePackLoader;

import android.util.DisplayMetrics;

import com.myslotmachine.util.bg;
import com.myslotmachine.util.game;

public class MainActivity extends BaseGameActivity implements IOnSceneTouchListener{
	
	private static final int LOG_LASTY = 100;
	/**
	 * 按钮横向距离左边或者右边的距离
	 */
	private static final int HORIZONTAL_SPACING = 100;
	/**
	 * 按钮纵向距离顶部或者底部的距离
	 */
	private static final int VERTICAL_SPACING = 80;

	/**
	 * 游戏操作视图
	 */
	private Camera mCamera;
	/**
	 * 游戏场景
	 */
	private Scene mScene;
	/**
	 * 欢迎界面背景纹理
	 */
	private TextureRegion bgtextureRegion;
	/**
	 * 欢迎界面操作按钮的纹理
	 */
	private TextureRegion defaulthelpRegion;
	/**
	 * 欢迎界面点击帮助按钮改变纹理
	 */
	private TextureRegion helpTouchRetion;
	/**
	 * 欢迎界面声音按钮纹理
	 */
	private TextureRegion defaultVedioRegion;
	/**
	 * 欢迎界面声音点击改变纹理
	 */
	private TextureRegion vedioTouchRegion;
	/**
	 * 欢迎界面进入按钮纹理
	 */
	private TextureRegion defaultEnterRegion;
	/**
	 * 欢迎界面点击进入按钮改变纹理
	 */
	private TextureRegion enterTouchRegion;
	
	/**
	 * 中间动画纹理
	 */
	private TextureRegion defaultSlotMachineRgion;
	/**
	 * 屏幕对象
	 */
	private DisplayMetrics dm;
	
	private Sprite helpSprite;
	private Sprite helpTouchSprite;
	private Sprite vedioSprite;
	private Sprite vedioTouchSprite;
	private Sprite startSprite;
	private Sprite startTouchSprite;
	 
	@Override
	public EngineOptions onCreateEngineOptions() {
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mCamera = new Camera(0, 0, dm.widthPixels,dm.heightPixels);
		EngineOptions mEngineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,new FillResolutionPolicy(), mCamera);
		return mEngineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		//加载纹理数据
		TexturePack texturePack = new TexturePackLoader(this.getAssets(), this.getTextureManager()).loadFromAsset("gfx/bg.xml","gfx/");
		//加载背景纹理
		texturePack.loadTexture();
		//从背景纹理对象库中获取背景纹理对象
		bgtextureRegion = texturePack.getTexturePackTextureRegionLibrary().get(bg.OP_BG_ID);
		//拆建
		bgtextureRegion.setTextureY(bgtextureRegion.getHeight()/3);
		bgtextureRegion.setTextureX(bgtextureRegion.getTextureX());
		bgtextureRegion.setTextureHeight(bgtextureRegion.getHeight()*(2f/3f));
		bgtextureRegion.setTextureWidth(bgtextureRegion.getWidth());
		//加载欢迎界面操作按钮的纹理数据
		texturePack = new TexturePackLoader(this.getAssets(), this.getTextureManager()).loadFromAsset("gfx/game.xml","gfx/");
		//加载欢迎界面操作按钮的纹理
		texturePack.loadTexture();
		
		defaulthelpRegion = texturePack.getTexturePackTextureRegionLibrary().get(game.OP_HELP_01_ID);
		defaultVedioRegion = texturePack.getTexturePackTextureRegionLibrary().get(game.OP_SOUND_01_ID);
		defaultEnterRegion = texturePack.getTexturePackTextureRegionLibrary().get(game.OP_START_01_ID);
		defaultSlotMachineRgion = texturePack.getTexturePackTextureRegionLibrary().get(game.SHUIGUO_LOGO_ID);
		
		helpTouchRetion = texturePack.getTexturePackTextureRegionLibrary().get(game.OP_HELP_02_ID);
		vedioTouchRegion = texturePack.getTexturePackTextureRegionLibrary().get(game.OP_SOUND_02_ID);
		enterTouchRegion = texturePack.getTexturePackTextureRegionLibrary().get(game.OP_START_02_ID);
		
		 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");  
		 
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		mScene = new Scene();
		// 设置背景
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		Sprite bgSprite = new Sprite(0,0,dm.widthPixels,dm.heightPixels,bgtextureRegion, getVertexBufferObjectManager());
		ParallaxEntity parallaxEntity = new ParallaxEntity(0.0f, bgSprite);
		autoParallaxBackground.attachParallaxEntity(parallaxEntity);
		mScene.setBackground(autoParallaxBackground);
		helpSprite = new Sprite(this.HORIZONTAL_SPACING, this.VERTICAL_SPACING, defaulthelpRegion, getVertexBufferObjectManager());
		helpTouchSprite = new Sprite(this.HORIZONTAL_SPACING, this.VERTICAL_SPACING, helpTouchRetion, getVertexBufferObjectManager());
		vedioSprite = new Sprite(dm.widthPixels-(this.HORIZONTAL_SPACING+defaultVedioRegion.getWidth()), this.VERTICAL_SPACING, defaultVedioRegion, getVertexBufferObjectManager());
		vedioTouchSprite = new Sprite(dm.widthPixels-(this.HORIZONTAL_SPACING+defaultVedioRegion.getWidth()), this.VERTICAL_SPACING, vedioTouchRegion, getVertexBufferObjectManager());
		startSprite = new Sprite((dm.widthPixels/2f)-(defaultEnterRegion.getWidth()/2f),(dm.heightPixels-this.VERTICAL_SPACING-defaultEnterRegion.getHeight()),defaultEnterRegion,getVertexBufferObjectManager());
		startTouchSprite = new Sprite((dm.widthPixels/2f)-(defaultEnterRegion.getWidth()/2f),(dm.heightPixels-this.VERTICAL_SPACING-defaultEnterRegion.getHeight()),enterTouchRegion,getVertexBufferObjectManager());
		Sprite slotmachineSprite = new Sprite((dm.widthPixels/2f)-(defaultSlotMachineRgion.getWidth()/2f),2*this.VERTICAL_SPACING,defaultSlotMachineRgion,getVertexBufferObjectManager());
		
		mScene.attachChild(helpSprite);
		mScene.attachChild(vedioSprite);
		mScene.attachChild(startSprite);
		mScene.attachChild(slotmachineSprite);
		
		helpTouchSprite.setVisible(false);
		vedioTouchSprite.setVisible(false);
		startTouchSprite.setVisible(false);
		
		mScene.attachChild(helpTouchSprite);
		mScene.attachChild(vedioTouchSprite);
		mScene.attachChild(startTouchSprite);
		
		JumpModifier jmStart = new JumpModifier(2,(dm.widthPixels/2f)-(defaultSlotMachineRgion.getWidth()/2f),(dm.widthPixels/2f)-(defaultSlotMachineRgion.getWidth()/2f),-defaultSlotMachineRgion.getHeight(),LOG_LASTY,LOG_LASTY);
		JumpModifier jmTotop = new JumpModifier(1,(dm.widthPixels/2f)-(defaultSlotMachineRgion.getWidth()/2f),(dm.widthPixels/2f)-(defaultSlotMachineRgion.getWidth()/2f),LOG_LASTY,LOG_LASTY-20,20);
		JumpModifier jmTobottom = new JumpModifier(1,(dm.widthPixels/2f)-(defaultSlotMachineRgion.getWidth()/2f),(dm.widthPixels/2f)-(defaultSlotMachineRgion.getWidth()/2f),LOG_LASTY-20,LOG_LASTY,20);
		slotmachineSprite.registerEntityModifier(new SequenceEntityModifier(jmStart,jmTotop,jmTobottom,new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(2, 1f, 1.2f), new ScaleModifier(2, 1.2f, 1f)))));
		mScene.setOnSceneTouchListener(this);
		pOnCreateSceneCallback.onCreateSceneFinished(mScene);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public boolean onSceneTouchEvent(Scene scene, TouchEvent tevent) {
		// 得到屏幕上点击的x和y坐标
		final float x = tevent.getX();
		final float y = tevent.getY();
		// 当手按下的时候
		if (tevent.isActionDown()) {
		   // 在Scene中去掉对象的操作只有在更新线程中进行才安全，
		   // 否则会引发在更新时候丢失Entity对象引发的异常
		   this.runOnUpdateThread(new Runnable() {
			   @Override
			   public void run() {
				// 判断按下的点是否在Sprite上面，如果是则把Sprite从Scene中去掉
				   if (helpSprite.contains(x, y)) {
					   helpSprite.setVisible(false);
					   helpTouchSprite.setVisible(true);
				    }else if(vedioSprite.contains(x, y)){
				    	vedioSprite.setVisible(false);
				    	vedioTouchSprite.setVisible(true);
				    }else if(startSprite.contains(x, y)){
				    	startSprite.setVisible(false);
				    	startTouchSprite.setVisible(true);
				    }
			    }
		    });
		 }else if(tevent.isActionUp()){
			 // 在Scene中去掉对象的操作只有在更新线程中进行才安全，
			   // 否则会引发在更新时候丢失Entity对象引发的异常
			   this.runOnUpdateThread(new Runnable() {
				   @Override
				   public void run() {
					// 判断按下的点是否在Sprite上面，如果是则把Sprite从Scene中去掉
					   if (helpTouchSprite.contains(x, y)) {
						   helpTouchSprite.setVisible(false);
						   helpSprite.setVisible(true);
					    }else if(vedioTouchSprite.contains(x, y)){
					    	vedioTouchSprite.setVisible(false);
					    	vedioSprite.setVisible(true);
					    }else if(startTouchSprite.contains(x, y)){
					    	startTouchSprite.setVisible(false);
					    	startSprite.setVisible(true);
					    }
				    }
			    });
		 }
		return false;
	}

}
