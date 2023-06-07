public class main {
    public static void main(String[] args) {
        if (args.length > 1) {      
        		System.out.println("Apprentissage"); 			
            System.out.println("Lecture du fichier WAV " + args[0]);
            Son son = new Son(args[0]);

            // System.out.println("Fichier "+args[0]+" : "+son.donnees().length+"
            // échantillons à "+son.frequence()+"Hz");
            // System.out.println("Bloc 1 : "+son.bloc_deTaille(1, 512).length+"
            // échantillons à "+son.frequence()+"Hz");

            float[] donnees = son.donnees();

            int nombreEchantillon = 1024; // Taille de votre bloc
            int nombreBlocs = donnees.length / nombreEchantillon;

            double freq=0;            
						
						double[][] Amplitude = new double[nombreBlocs][nombreEchantillon];
						
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
                    Amplitude[bloc][i] = ResultatFonction[i].mod();
                }
            }
            
            final float[][] entrees=new float[nombreBlocs][nombreEchantillon];
            for(int i=0; i<nombreBlocs; i++){
            	for(int j=0; j<nombreEchantillon; j++){
            		entrees[i][j] = (float)Amplitude[i][j];
            	}
            }
            
            final float[] resultats = {0,1,1,1,1,1,1,1,0,0};
            final iNeurone n = new NeuroneHeavyside(entrees[0].length);
            System.out.println("Nombre de tours : "+n.apprentissage(entrees, resultats));
            //final Neurone vueNeurone = (Neurone)n;
						//System.out.print("Synapses : ");
						//for (final float f : vueNeurone.synapses())
							//System.out.print(f+" ");
						//System.out.print("\nBiais : ");
						//System.out.println(vueNeurone.biais());
		
						// On affiche chaque cas appris
						for (int i = 0; i < entrees.length; ++i)
						{
							// Pour une entrée donnée
							final float[] entree = entrees[i];
							// On met à jour la sortie du neurone
							n.metAJour(entree);
							// On affiche cette sortie
							System.out.println("Entree "+i+" : "+n.sortie());
						}
						
						System.out.println("Test");
						System.out.println("Lecture du fichier WAV " + args[1]);
						Son son2 = new Son(args[1]);

            float[] donnees2 = son2.donnees();

            int nombreBlocs2 = donnees2.length / nombreEchantillon;           
						
						double[][] Amplitude2 = new double[nombreBlocs2][nombreEchantillon];
						
            for (int bloc = 0; bloc < nombreBlocs2; bloc++) {

                // Créer un tableau de complexe pour le bloc actuel
                float[] donneesparBloc2 = son2.bloc_deTaille(bloc, nombreEchantillon);
                Complexe[] cplx2 = new Complexe[nombreEchantillon];
        
                for (int i = 0; i < nombreEchantillon; i++) {
                    cplx2[i] = new ComplexeCartesien(donneesparBloc2[i], 0);
                }
        
                // Appliquer la FFT
                Complexe[]  ResultatFonction2= FFTCplx.appliqueSur(cplx2);
        
                // Chercher le pic
                int indexPicBloc2 = 0;

                for (int i = 0; i < ResultatFonction2.length/2; i++) {
                    Amplitude2[bloc][i] = ResultatFonction2[i].mod();
                }
            }
            
            final float[][] entrees2=new float[nombreBlocs2][nombreEchantillon];
            for(int i=0; i<nombreBlocs2; i++){
            	for(int j=0; j<nombreEchantillon; j++){
            		entrees2[i][j] = (float)Amplitude2[i][j];
            	}
            }
            
            for(int i=0; i<nombreBlocs; i++){
            	n.metAJour(entrees2[i]);
            	System.out.println("Entrée"+i+" : "+n.sortie());
            }
        }
        
        else {
            System.out.println("Veuillez donner le nom d'un fichier WAV en paramètre SVP.");
        }
    }
}
