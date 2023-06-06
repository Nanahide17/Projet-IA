import javax.print.event.PrintEvent;

public class main {
    public static void main(String[] args) {
        if (args.length == 1) {
            System.out.println("Lecture du fichier WAV " + args[0]);
            Son son = new Son(args[0]);

            // System.out.println("Fichier "+args[0]+" : "+son.donnees().length+"
            // échantillons à "+son.frequence()+"Hz");
            // System.out.println("Bloc 1 : "+son.bloc_deTaille(1, 512).length+"
            // échantillons à "+son.frequence()+"Hz");

            float[] donnees = son.donnees();

            int nombreEchantillon = 4096; // Taille de votre bloc
            int nombreBlocs = donnees.length / nombreEchantillon;

            double freq=0;

            double AmplitudeMaxPic = 0;
            double AmplitudePicIteration = 0;
            double AmplitudeMAX=0;
            

            for (int bloc = 0; bloc < nombreBlocs; bloc++) {

                // Créer un tableau de complexe pour le bloc actuel
                float[] donneesparBloc = son.bloc_deTaille(bloc, nombreEchantillon);
                Complexe[] cplx = new Complexe[nombreEchantillon];
        
                for (int i = 0; i < nombreEchantillon; i++) {
                    cplx[i] = new ComplexeCartesien(donneesparBloc[i], 0);
                }
        
                // Appliquer la FFT
                Complexe[]  ResultatFonction= FFTCplx.appliqueSur(cplx);
        
                // Chercher le pic
                int indexPicBloc = 0;

                for (int i = 0; i < ResultatFonction.length/2; i++) {
                    AmplitudePicIteration = ResultatFonction[i].mod();
                    if (AmplitudePicIteration > AmplitudeMaxPic) {
                        AmplitudeMaxPic = AmplitudePicIteration;
                        indexPicBloc = i;
                    }
                }
                //Affichage de la valeur de max de l'amplitude par bloc
                System.out.println("Dans le bloc " + bloc + " l'amplitude vaut : "+ AmplitudePicIteration);

                if (AmplitudeMAX < AmplitudePicIteration){
                    AmplitudeMAX = AmplitudePicIteration;
                }
            }

            //Affichage de l'amplitude max tous les blocs
            System.out.println("amplitude max entre tous les blocs : " + AmplitudeMAX);
        }
        else {
            System.out.println("Veuillez donner le nom d'un fichier WAV en paramètre SVP.");
        }
    }
}
