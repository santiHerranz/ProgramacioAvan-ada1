package edlineals;
/**
 * @author Developer
 *
 * @param <E>
 */
public class CuaEnll<E> implements Cua<E> {

	// adre�a al node final de la cua
	private Node<E> fi;
	
	public CuaEnll(){
		super();
		this.inicialitzar();
	}	
	
	@Override
	public void encuar(E value) {

		//System.out.println(value);
		
		Node<E> node = new Node<E>(value);

		// si la cua es buida
		if(fi == null){
			this.fi = node;
			this.fi.seg = node; //Referencia circular
		} else {
			node.seg = this.fi.seg;
			this.fi.seg = node;
			this.fi = node;
		}
	}

	@Override
	public E desEncuar() throws Exception {
		if(cuaBuida() == true) throw new Exception("La cua est� buida");
		
		// treure el cap (el node seg�ent al fi) 
		Node<E> cap = this.fi.seg;
		E value = cap.inf;

		// el cap �s el fi (nom�s hi ha un element)
		if(this.fi.equals(cap))
			this.fi = null;
		else
			this.fi.seg = cap.seg;
		
		return value;
	}

	@Override
	public boolean cuaBuida() {
		return (this.fi == null);
	}

	@Override
	public void inicialitzar() {
		if(!cuaBuida()) buidar();
		this.fi = null;
	}

	@Override
	public E consulta() throws Exception {
		if(cuaBuida() == true) throw new Exception("La cua est� buida");
		return fi.seg.inf;
	}

	@Override
	public void buidar() {

		while(!cuaBuida()) {
			try {
				desEncuar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	/*
	 * Metode que calcula i retorna el nombre d�elements que t� la Cua
	 * la implementaci� ha de ser recursiva
	 */
	public int quants(){
		return quants(this.fi);
	}
	
	private int quants( Node<E> node){
		// 
		if(node == null) return 0;
		if (node.seg!=null && !node.seg.equals(this.fi))
			return 1 + quants(node.seg);
		else
			return 1;
	}
	
	
//TODO CuaEnll.Equals
/*
 * dues cues s�n iguals si les seves corresponents seq��ncies enlla�ades fan refer�ncia 
 * a objectes iguals (equals) i en el mateix ordre.
 */
	public boolean equals(CuaEnll<E> o) {
		if(this.quants()!= o.quants()) return false;
		
		boolean result = false;
		if(!this.cuaBuida() && !o.cuaBuida())  {
			result = true;
			Node<E> aux1 = this.fi.seg;
			Node<E> aux2 = o.fi.seg;
			while(!aux1.equals(this.fi)){
				if(aux1.inf != aux2.inf){
					result = false;
					break;
				}
				aux1 = aux1.seg;
				aux2 = aux2.seg;
			}
		}		
		return result;
	}
	
	public String toString() {
		
		StringBuilder aux = new StringBuilder();

		if(this.fi == null) return "";

		if(this.fi.seg.equals(this.fi))
			aux.append(this.fi.inf.toString());
		else {
			Node<E> item = this.fi.seg;
			while(!item.equals(this.fi)){
				aux.append(item.inf.toString());
				item = item.seg;
			}
			aux.append(item.inf.toString());
		}

		return aux.toString();
	}
	
	// CuaEnll.Clonar
	public CuaEnll<E> clone() throws CloneNotSupportedException {
		CuaEnll<E> copia = new CuaEnll<E>();

		if(!cuaBuida()) {
			Node<E> cap = this.fi.seg;
			Node<E> item = cap;
			copia.encuar(item.inf);
			item = item.seg;
			while(!item.equals(cap)){
				copia.encuar(item.inf);
				item = item.seg;
			}
		}
		
	    return copia;
	}		

	
	private class Node<K> {
		private K inf;
		private Node<K> seg;

		Node(K data){
			this.inf = data;
		}
	}


}