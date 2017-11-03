package com.gitrip.shooterapp;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.net.Server;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsParticleControl;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.settings.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.EnumSet;
import java.util.Map;

import static com.gitrip.shooterapp.ShooterType.ENEMY;
import static com.gitrip.shooterapp.ShooterType.PLAYER;
import static com.gitrip.shooterapp.ShooterType.PLAYER2;


public class ShooterApp extends GameApplication {

    private GameEntity player;
    private GameEntity player2;
    private GameEntity myPlayer;

    public static void main(String... args){
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings){
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Simple Game App");
        settings.setVersion("0.1");
        settings.setProfilingEnabled(false); //turn off fps
        settings.setCloseConfirmation(false); //turn off exit dialog
        settings.setIntroEnabled(false); //turn off intro
        settings.setMenuEnabled(true); //turn off menus
        settings.setEnabledMenuItems(EnumSet.of(MenuItem.ONLINE));
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("enemies", 0);

    }

    @Override
    protected void initGame(){
        player = Entities.builder()
                .at(300,300)
                .type(PLAYER)
                .with(new CollidableComponent(true))
                .viewFromNode(new Rectangle(25,25, Color.BLUE))
                .buildAndAttach(getGameWorld());

        player2 = Entities.builder()
                .at(100,400)
                .type(PLAYER2)
                .with(new CollidableComponent(true))
                .viewFromNode(new Rectangle(25,25,Color.RED))
                .buildAndAttach(getGameWorld());

        myPlayer = player;

        if (getNet().getConnection().isPresent()) {
            myPlayer = getNet().getConnection().get() instanceof Server ? player : player2;
        }

        getMasterTimer().runAtInterval(() -> {

            int numEnemies = getGameState().getInt("enemies");

            if (numEnemies < 5) {
                getGameWorld().spawn("Enemy",
                        FXGLMath.random(0, getWidth() - 40),
                        FXGLMath.random(0, getHeight() / 2 - 40)
                );

                getGameState().increment("enemies", +1);
            }

        }, Duration.seconds(1));

    }

    @Override
    protected void initInput(){
        Input input = getInput(); // get input service
        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                myPlayer.translateX(5);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                myPlayer.translateX(-5);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                myPlayer.translateY(-5);
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                myPlayer.translateY(5);
            }
        }, KeyCode.S);

        input.addAction(new UserAction("Shoot") {
            @Override
            protected void onActionBegin() {
                getGameWorld().spawn("Bullet",myPlayer.getX(),myPlayer.getY());
            }
        }, KeyCode.C);

    }

    @Override
    protected void initPhysics() {
        PhysicsWorld physicsWorld = getPhysicsWorld();

        physicsWorld.addCollisionHandler(new CollisionHandler(ShooterType.BULLET, ENEMY) {
            @Override
            protected void onCollisionBegin(Entity bullet, Entity enemy) {
                bullet.removeFromWorld();
                enemy.removeFromWorld();
            }
        });
    }
}