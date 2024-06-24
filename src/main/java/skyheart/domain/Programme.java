package skyheart.domain;

public class Programme {
	
	private String programmeName;
    private String provider;
    private int viewershipRating;
    private String genre;
    private String certificate;
    private int score;

    public int getScore() {
		return score;
	}

	public Programme(String programmeName, String provider, int viewershipRating, String genre, String certificate, int score) {
		super();
		this.programmeName = programmeName;
		this.provider = provider;
		this.viewershipRating = viewershipRating;
		this.genre = genre;
		this.certificate = certificate;
		this.score = score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getProgrammeName() {
        return programmeName;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getViewershipRating() {
        return viewershipRating;
    }

    public void setViewershipRating(int viewershipRating) {
        this.viewershipRating = viewershipRating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
}
