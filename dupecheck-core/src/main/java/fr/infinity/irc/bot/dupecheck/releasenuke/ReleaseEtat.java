package fr.infinity.irc.bot.dupecheck.releasenuke;

public enum ReleaseEtat {

    NUKE("Release nuked"),
    UNNUKE("Release Unnuked"),
    MODNUKE("Release Modnuked"),
    DELPRE("Release deleted")
    ;

    /**
     * Libellé.
     */
    private final String libelle;

    /**
     * Constructeur.
     *
     * @param libelle libellé de l'état
     */
    ReleaseEtat(final String libelle) {
        this.libelle = libelle;
    }

    /**
     * Getter pour libelle.
     *
     * @return Retourne le libelle.
     */
    public String getLibelle() {
        return this.libelle;
    }



}
