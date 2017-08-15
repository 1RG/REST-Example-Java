package lt.rg.example.controler;

public class Element {
	private int id;
	private String name;
	private int kiekis;
	private String spalva;
	
	public Element() {}
	
	public Element(int id, String name, int kiekis, String spalva) {
		this.id = id;
		this.name = name;
		this.kiekis = kiekis;
		this.spalva = spalva;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public int getKiekis() {
		return kiekis;
	}

	public String getSpalva() {
		return spalva;
	}
}
