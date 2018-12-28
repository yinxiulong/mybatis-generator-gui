package com.zzg.mybatis.generator.view;

import com.zzg.mybatis.generator.util.TimeDialog;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Owen on 6/21/16.
 */
public class AlertUtil {

    public static void showInfoAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setContentText(message);
//        alert.show();
        TimeDialog d = new TimeDialog();
        // TimerTest是程序主窗口类，弹出的对话框10秒后消失
        d.showDialog(new JFrame(), Color.red, message, 3);
    }

    public static void showWarnAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setContentText(message);
//        alert.show();
        TimeDialog d = new TimeDialog();
        // TimerTest是程序主窗口类，弹出的对话框10秒后消失
        d.showDialog(new JFrame(), Color.red, message, 3);
    }

    public static void showErrorAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setContentText(message);
//        alert.show();
        // 弹框提示成功消息
        TimeDialog d = new TimeDialog();
        // TimerTest是程序主窗口类，弹出的对话框10秒后消失
        d.showDialog(new JFrame(), Color.red, message, 3);
    }

    public static void showSuccessAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setContentText(message);
//        alert.show();
        // 弹框提示成功消息
        TimeDialog d = new TimeDialog();
        // TimerTest是程序主窗口类，弹出的对话框10秒后消失
        d.showDialog(new JFrame(), Color.green, message, 3);
    }

    /**
     * build both OK and Cancel buttons for the user
     * to click on to dismiss the dialog.
     *
     * @param message
     */
    public static Alert buildConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        return alert;
    }

}
