import java.util.Arrays;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.nio.ByteOrder;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFormat;

public class Main {
    // Copiez ici le code de la classe Son

    // Copiez ici le code de la classe FFTCplx avec les modifications nécessaires

    public static void main(String[] args) {
        String fichierSon = "chemin/vers/fichier.wav";
        
        Son son = new Son(fichierSon);
        
        // Utilisez les méthodes de la classe Son selon vos besoins
        int frequence = son.frequence();
        float[] donnees = son.donnees();
        
        // Utilisez les valeurs retournées selon vos besoins
        // ...
        
        Complexe[] signal = convertirEnComplexe(donnees);
        
        Complexe[] resultatFFT = FFTCplx.appliqueSur(signal);
        
        // Utilisez les valeurs du résultat FFT selon vos besoins
        // ...
    }
    
    private static Complexe[] convertirEnComplexe(float[] donnees) {
        Complexe[] signal = new Complexe[donnees.length];
        for (int i = 0; i < donnees.length; i++) {
            signal[i] = new ComplexeCartesien(donnees[i], 0);
        }
        return signal;
    }
}
