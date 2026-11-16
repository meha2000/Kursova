package logik;

import javax.sound.sampled.*;
import java.io.File;

public class SoundPlayer {
    private Clip bgClip;

    // Відтворення короткого звукового ефекту (правильна / неправильна відповідь)
    public void playEffect(String filePath) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) return;

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            System.out.println("Помилка відтворення звуку: " + e.getMessage());
        }
    }

    // Запуск фонової музики по колу під час гри
    public void startBackgroundMusic(String filePath) {
        try {
            stopBackgroundMusic(); // Зупиняємо попередню, якщо була запущена
            File soundFile = new File(filePath);
            if (!soundFile.exists()) return;

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            bgClip = AudioSystem.getClip();
            bgClip.open(audioIn);
            bgClip.loop(Clip.LOOP_CONTINUOUSLY); // Нескінченний повтор
            bgClip.start();
        } catch (Exception e) {
            System.out.println("Помилка фонової музики: " + e.getMessage());
        }
    }

    // Зупинка фонової музики
    public void stopBackgroundMusic() {
        if (bgClip != null && bgClip.isRunning()) {
            bgClip.stop();
            bgClip.close();
        }
    }
}