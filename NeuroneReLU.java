public class NeuroneReLU extends Neurone {
	// Fonction d'activation d'un neurone (peut facilement être modifiée par
	// héritage)
	protected float activation(final float valeur) {
		//return valeur >= 0 ? 1.f : 0.f;
		return Math.max(0.f, valeur);
		//OBJ recuperer le x de la classe mère 
	}

	// Constructeur
	public NeuroneReLU(final int nbEntrees) {
		super(nbEntrees);
	}
}