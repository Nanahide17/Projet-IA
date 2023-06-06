class FFTCplx
{
	public final static int TailleFFTtest = 16;
	public final static double Periode = 1;

// 	private int taille;
// 
// // Indiquer la taille permettra des optimisations par la suite
// 	public FFTCplx(int taille)
// 	{
// 		this.taille = taille;
// 	}

	// Sous-signal obligatoirement découpé par pas de deux
	private static Complexe[] demiSignal(Complexe[] signal, int depart)
	{
		//On divise la taille du signal d'entrée par deux 
		Complexe[] sousSignal = new Complexe[signal.length/2];
		for (int i = 0; i < sousSignal.length; ++i)
			//Met dans sous signal, la valeur du signal à partir du départ donné et prend un élément sur deux
			sousSignal[i] = signal[depart+2*i];
		return sousSignal;
	}
	
	public static Complexe[] appliqueSur(Complexe[] signal)
	{
		//Création d'un nouveau signal de taille du signal en entrée
		Complexe[] trSignal = new Complexe[signal.length];
		if (signal.length == 1)	// Cas trivial de la FFT d'une valeur unique
			//Création d'un complexe cartésien
			trSignal[0] = new ComplexeCartesien(signal[0].reel(), signal[0].imag());
		else
		{
			// Découpage des deux sous-groupes de données sur lesquels on applique la FFT
			// (voir définition de cours)
			
			//On coupe en deux le signal 
			//Ici pour valeur pair
			final Complexe[] P0 = appliqueSur(demiSignal(signal, 0));
			//Ici valeur impair
			final Complexe[] P1 = appliqueSur(demiSignal(signal, 1));
			// On applique le regroupement "papillon" pour créer le résultat final
			for (int k = 0; k < signal.length/2; ++k)
			{
				//Création du complexe de la forme expo=1(cos(-2pik/16)+isin(-2pik/16))
				//Ici division par 16 car longueur signal = 16
				final ComplexePolaire expo = new ComplexePolaire(1., -2.*Math.PI*k/signal.length);
				final Complexe temp = P0[k];
				//Contient à partie du début de la liste jusqu'à la moitiè P0[k]+(expo*P1[k])
				trSignal[k] = temp.plus(expo.fois(P1[k]));
				//COntient de la moitiè de la liste jusqu'à la fin P0[k]-(expo*P1[k])
				trSignal[k+signal.length/2] = temp.moins(expo.fois(P1[k]));
			}
		}
		return trSignal;
	}

	public static void main(String[] args)
	{
		// Création d'un signal test simple
		Complexe[] signalTest = new Complexe[TailleFFTtest];
		for (int i = 0; i < TailleFFTtest; ++i)
		//Ici création du signal cos(2pi*i/16*1)
			signalTest[i] = new ComplexeCartesien(Math.cos(2.*Math.PI*i/TailleFFTtest*Periode), 0);
		// On applique la FFT sur ce signal
		Complexe[] resultat = appliqueSur(signalTest);
		// On affiche les valeurs du résultat
		for (int i = 0; i < resultat.length; ++i) {
			System.out.print(i+" : ("+(float)resultat[i].reel()+" ; "+(float)resultat[i].imag()+"i)");
			System.out.println(", ("+(float)resultat[i].mod()+" ; "+(float)resultat[i].arg()+" rad)");
		}
	}
}
