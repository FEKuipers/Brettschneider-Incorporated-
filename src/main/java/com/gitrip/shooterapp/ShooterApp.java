package com.gitrip.shooterapp;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.net.Server;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.settings.MenuItem;
import com.gitrip.shooterapp.message.Message;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.EnumSet;


public class ShooterApp extends GameApplication {

    private GameEntity serverPlayer;
    private GameEntity clientPlayer;
    private GameEntity myPlayer;
    private Messenger messenger;
    private ClientStateBroadcaster broadcaster;

    public static void main(String... args){
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings){
        settings.setWidth(600);
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
    protected void preInit() {
        addExitListener(() -> {
            if(messenger != null && broadcaster != null) {
                broadcaster.stopBroadcasting();
                messenger.stopMessenger();
            }
        });
    }

    @Override
    protected void initGame(){
        serverPlayer = Entities.builder()
                .at(300,300)
                .viewFromNode(new Rectangle(25,25, Color.BLUE))
                .buildAndAttach(getGameWorld());

        clientPlayer = Entities.builder()
                .at(100,400)
                .viewFromNode(new Rectangle(25,25,Color.RED))
                .buildAndAttach(getGameWorld());

        if (getNet().getConnection().isPresent()) {
            myPlayer = getNet().getConnection().get() instanceof Server ? serverPlayer : clientPlayer;
            ClientState state = new ClientState(serverPlayer, clientPlayer, myPlayer);
            ClientHandler clientHandler = new ClientHandler(state);
            messenger = new Messenger(getNet().getConnection(), clientHandler, null);
            broadcaster = new ClientStateBroadcaster(state, messenger);
            new Thread(broadcaster).start();
        }else {
            System.out.println("The game is started in single-player mode");
            myPlayer = serverPlayer;
        }
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
}