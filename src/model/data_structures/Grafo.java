package model.data_structures;

import java.io.Serializable;

import java.util.Iterator;

import model.vo.VOWay;

public class Grafo <K extends Comparable<K>, V, A extends Comparable<A>> implements Serializable
{
	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	private TablaHash<K, Vertice> vertices;

	private LinkedList<Arco> arcos;

	private int cantVertices;

	private int cantEnlaces;

	// -----------------------------------------------------------------
	// Contructores
	// -----------------------------------------------------------------
	public Grafo()
	{
		vertices = new TablaHash<K, Vertice>();
		arcos = new LinkedList<Arco>();
		cantVertices = 0;
		cantEnlaces = 0;
	}
	// -----------------------------------------------------------------
	// M�todos
	// -----------------------------------------------------------------

	//	public Vertice buscarVerticeMasCercano(double pLatitud, double pLongitud) {
	//		
	//	}

	public int V() 
	{
		return cantVertices;
	}

	public int E() 
	{
		return cantEnlaces;
	}

	public void addVertex(K idVertex, V infoVertex) 
	{
		Vertice nuevoVertice = new Vertice(idVertex, infoVertex);
		vertices.put(idVertex, nuevoVertice);
		cantVertices++;
	}

	public void addVertexSecondForm(K idVertex, V infoVertex, LinkedList<Arco> adj) 
	{
		Vertice nuevoVertice = new Vertice(idVertex, infoVertex, adj);
		vertices.put(idVertex, nuevoVertice);
		cantVertices++;
		NodeList<Arco> actAdj=  adj.getFirstNode();

		while(actAdj!=null && actAdj.getelem() != null)
		{
			arcos.add(actAdj.getelem());
			actAdj=actAdj.getNext();
			cantEnlaces++;
		}

	}


	public void addEdge(K idVertexInit, K idVertexFin, A infoArc) 
	{
		Vertice verticeInicio = getVertice(idVertexInit);
		Vertice verticeFin = getVertice(idVertexFin);
		Arco nuevoArco = new Arco(infoArc, verticeInicio, verticeFin);
		if(verticeInicio.getArco(infoArc)==null)
		{
			verticeInicio.getArcos().add(nuevoArco);
			//verticeFin.getArcos().add(new Arco(infoArc, verticeFin, verticeInicio));
			//arcos.addNotRepeated(nuevoArco);
			arcos.add(nuevoArco);
			cantEnlaces++;
		}
	}

	public void addEdgeSecondForm(K idVertexInit, K idVertexFin, A infoArc) 
	{
		Vertice verticeInicio = getVerticeSecondForm(idVertexInit);
		Vertice verticeFin = getVerticeSecondForm(idVertexFin);
		Arco nuevoArco = new Arco(infoArc, verticeInicio, verticeFin);
		verticeInicio.getArcos().add(nuevoArco);
		arcos.add(nuevoArco);
		cantEnlaces++;
	}

	public V getInfoVertex(K idVertex) 
	{
		V rta = null;
		Vertice temp = vertices.get(idVertex);
		if(temp!=null)
		{
			rta = temp.getInfo();
		}
		return rta;
	}

	public void setInfoVertex(K idVertex, V infoVertex) 
	{
		Vertice buscado = getVertice(idVertex);
		buscado.setInfoVertex(idVertex, infoVertex);
	}


	public A getInfoArc(K idVertexIni, K idVertexFin) 
	{
		A rta = null;
		Arco arcoBuscado = arcos.getObject(new Arco(null, new Vertice(idVertexIni, null), new Vertice(idVertexFin, null)));
		if(arcoBuscado!=null)
		{
			rta = arcoBuscado.getInfoArco();
		}
		return rta;
	}


	public void setInfoArc(K idVertexIni, K idVertexFin, A infoArc) 
	{
		Arco buscado = getArco(idVertexIni, idVertexFin);
		buscado.setInfoArc(infoArc);
	}

	public Iterable<K> adj(K idVertex) 
	{
		Vertice verticeInteres = getVertice(idVertex);
		LinkedList<Arco> arcosVertice = verticeInteres.getArcos();
		return new IterableKeysAdjuntas(arcosVertice);
	}

	public Iterator<K> iteratorLlaves()
	{
		return vertices.keys();
	}

	public Iterator<V> iteratorValores()
	{
		return new IteratorValores();
	}

	public Iterator<A> iteratorArcos()
	{
		return new IteratorArcos();
	}

	public Iterator<Vertice> iteratorVertices()
	{
		return vertices.values();
	}

	public void marcar(K idVertex)
	{
		getVertice(idVertex).marcar();
	}

	public boolean estaMarcado(K idVertex)
	{
		return getVertice(idVertex).estaMarcado();
	}

	public void desmarcarVertices()
	{
		Iterator<Vertice> iterator = iteratorVertices();
		while(iterator.hasNext())
		{
			iterator.next().desmarcar();
		}
	}

	public Vertice getVertice(K idVertex)
	{
		return vertices.get(idVertex);
	}

	public TablaHash<K, Vertice> getVertices()
	{
		return vertices;
	}
	private Vertice getVerticeSecondForm(K idVertex)
	{
		return vertices.getSecondForm(idVertex);
	}

	private Arco getArco(K idVertexIni, K idVertexFin)
	{
		Arco arcoBuscado = new Arco(null, new Vertice(idVertexIni, null), new Vertice(idVertexFin, null));
		return arcos.getObject(arcoBuscado);
	}

	// -----------------------------------------------------------------
	// Clases
	// -----------------------------------------------------------------
	public class Vertice implements Serializable
	{
		private K key;

		private V info;

		private LinkedList<Arco> arcos;

		private boolean marcado;

		public Vertice(K pKey, V pInfo)
		{
			key = pKey;
			info = pInfo;
			arcos = new LinkedList<Arco>();
			marcado = false;
		}

		public Vertice(K pKey, V pInfo, LinkedList<Arco> pAdj )
		{
			key = pKey;
			info = pInfo;
			arcos = pAdj;
			marcado = false;
		}

		public K getKey()
		{
			return key;
		}

		public V getInfo()
		{
			return info;
		}

		public LinkedList<Arco> getArcos()
		{
			return arcos;
		}

		public Arco getArco(A a)
		{

			if(arcos==null||arcos.getSize()==0)
			{
				return null;
			}
			else
			{
				NodeList actual= arcos.getFirstNode();
				while(actual!=null)
				{
					Arco actArco=(Arco)actual.getelem();
					if(actArco.getInfoArco().equals(a))
					{
						Arco resp=actArco;
						return resp;
					}
					actual=actual.getNext();
				}
			}
			return null;
		}
		public void setInfoVertex(K pKey, V pInfo)
		{
			key = pKey;
			info = pInfo;
		}

		public boolean estaMarcado()
		{
			return marcado;
		}

		public void marcar()
		{
			marcado = true;
		}

		public void desmarcar()
		{
			marcado = false;
		}
	}

	public class Arco implements Comparable<Arco>, Serializable
	{
		private A infoArco;

		private Vertice verticeInit;

		private Vertice verticeFin;

		public Arco(A pInfoArco, Vertice pVerticeInit, Vertice pVerticeFin)
		{
			infoArco = pInfoArco;
			verticeInit = pVerticeInit;
			verticeFin = pVerticeFin;
		}

		public A getInfoArco()
		{
			return infoArco;
		}

		public Vertice getVerticeInit()
		{
			return verticeInit;
		}

		public Vertice getVerticeFin()
		{
			return verticeFin;
		}

		public void setInfoArc(A pInfoArc)
		{
			infoArco = pInfoArc;
		}

		@Override
		public int compareTo(Arco o) 
		{
			int respuesta = -1;
			if(verticeInit.getKey().compareTo(o.getVerticeInit().getKey())==0
					&& verticeFin.getKey().compareTo(o.getVerticeFin().getKey())==0)
			{
				respuesta = 0;
			}
			return respuesta;
		}
	}

	private class IterableKeysAdjuntas implements Iterable<K>
	{
		private Iterator<K> iterator;

		public IterableKeysAdjuntas(LinkedList<Arco> pArcos) 
		{
			iterator = new IteratorKeysAdjuntas(pArcos);
		}

		@Override
		public Iterator<K> iterator() 
		{
			return iterator;
		}
	}

	private class IteratorKeysAdjuntas implements Iterator<K>
	{		
		private K proximo;

		private Iterator<Arco> iteratorArcos;

		public IteratorKeysAdjuntas(LinkedList<Arco> pArcos) 
		{
			iteratorArcos = pArcos.iterator();
			Arco primerArco = iteratorArcos.next();
			if(primerArco != null)
			{
				proximo = primerArco.getVerticeFin().getKey();
			}
			else
			{
				proximo = null;
			}
		}

		@Override
		public boolean hasNext() 
		{
			return proximo!=null;
		}

		@Override
		public K next() 
		{
			K siguiente = proximo;
			Arco actual = iteratorArcos.next();
			if(actual != null)
			{
				proximo = actual.getVerticeFin().getKey();
			}
			else
			{
				proximo = null;
			}
			return siguiente;
		}
	}

	private class IteratorArcos implements Iterator<A>
	{
		private A proximo;

		private Iterator<Arco> iterator;

		public IteratorArcos() 
		{
			iterator = arcos.iterator();

			Arco primerArco = iterator.next();
			if(primerArco != null)
			{
				proximo = primerArco.getInfoArco();
			}
			else
			{
				proximo = null;
			}
		}		

		@Override
		public boolean hasNext() 
		{
			return proximo!=null;
		}

		@Override
		public A next() 
		{
			A siguiente = proximo;
			Arco actual = iterator.next();
			if(actual != null)
			{
				proximo = actual.getInfoArco();
			}
			else
			{
				proximo = null;
			}
			return siguiente;
		}
	}

	private class IteratorValores implements Iterator<V>
	{
		private V proximo;

		private Iterator<K> iteratorKeys;


		public IteratorValores() 
		{
			iteratorKeys = iteratorLlaves();
			proximo = getInfoVertex(iteratorKeys.next());
		}

		@Override
		public boolean hasNext() 
		{
			return proximo!=null;
		}

		@Override
		public V next() 
		{
			V siguiente = proximo;
			K sigLlave = iteratorKeys.next();
			if(sigLlave!=null)
			{
				proximo = getInfoVertex(sigLlave);
			}
			else
			{
				proximo = null;
			}
			return siguiente;
		}
	}
}

