package com.gsd09.entity;

/** 封装英文生词、中文翻译、存放时间 */
public class NewWords {
	private String chineseNewTranslation;
	private String englishNewWords;
	private String librarySource;
	private int libraryPicture;
	private long created;

	public NewWords() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewWords(String chineseNewTranslation, String englishNewWords,
			String librarySource, int libraryPicture, long created) {
		super();
		this.chineseNewTranslation = chineseNewTranslation;
		this.englishNewWords = englishNewWords;
		this.librarySource = librarySource;
		this.libraryPicture = libraryPicture;
		this.created = created;
	}

	public String getChineseNewTranslation() {
		return chineseNewTranslation;
	}

	public void setChineseNewTranslation(String chineseNewTranslation) {
		this.chineseNewTranslation = chineseNewTranslation;
	}

	public String getEnglishNewWords() {
		return englishNewWords;
	}

	public void setEnglishNewWords(String englishNewWords) {
		this.englishNewWords = englishNewWords;
	}

	public String getLibrarySource() {
		return librarySource;
	}

	public void setLibrarySource(String librarySource) {
		this.librarySource = librarySource;
	}

	public int getLibraryPicture() {
		return libraryPicture;
	}

	public void setLibraryPicture(int libraryPicture) {
		this.libraryPicture = libraryPicture;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "NewWords [chineseNewTranslation=" + chineseNewTranslation
				+ ", englishNewWords=" + englishNewWords + ", librarySource="
				+ librarySource + ", libraryPicture=" + libraryPicture
				+ ", created=" + created + "]\n";
	}

}
