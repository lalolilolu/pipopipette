package poubelle;
import java.util.*;

public class Graphe 
{
	
	private String graphe;
	private int longueur;
	private int largeur;

	
	/**
	 * 
	 */
	//1 contour,0 sans contour
	public Graphe(int lon,int larg,int cont)
	{
		
		longueur = lon;
		largeur = larg;
		graphe = "";
		if (cont == 1 ){
			for(int i=0;i<2*lon-1;i++)
			{
				for(int j=0;j<2*larg-1;j++)
				{
					
					//trait plein
					if (i==0 || i==2*lon-2)
					{ //première et dernière ligne
						if (j%2==0)
						{
							graphe+=".";
						}
						else
						{
							graphe+="-";
						}
					}
					//barres verticales et espaces
					if(i%2!=0)// pour chaque ligne impaire
					{		
						if(j!=0 && j!=2*larg-2)
						{
							graphe+=" ";
						}
						else
						{
							graphe+="|";
						}
					}
					
					//ligne a remplir (en jouant)
					if(i!=0 && i!=2*lon-2 && i%2==0)// pour toute ligne qui n'est pas la première
					{ 
						if (j%2==0)
						{
							graphe+=".";
						}
						else
						{
							graphe+=" ";
						}
					}
				}
				graphe+="\n";
			}
		}
		else
		{
			//graphe sans bords
			for(int i=0;i<2*lon-1;i++)
			{
				for(int j=0;j<2*larg-1;j++)
				{
					
					//trait plein
					if (i==0 || i==2*lon-2)//première et dernière ligne
					{ 
						if (j%2==0)
						{
							graphe+=".";
						}
						else
						{
							graphe+=" ";
						}
					}
					//barres verticales et espaces
					if(i%2!=0)// pour chaque ligne impaire
					{		
						if(j!=0 || j!=2*larg-2)
						{
							graphe+=" ";
						}
						else
						{
							graphe+=" ";
						}
					}
					
					//ligne a remplir (en jouant)
					if(i!=0 && i!=2*lon-2 && i%2==0) // pour toute ligne qui n'est pas la première
					{ 
						if (j%2==0)
						{
							graphe+=".";
						}
						else
						{
							graphe+=" ";
						}
					}
				}
				graphe+="\n";
			}
		}
	}
	
	
	/**
	 * parcour le graphe
	 * @param p1 un des 2 point jouer
	 * @param p2 l'autre
	 * @return la position entre les deux point
	 */
	public int parcour(int p1, int p2)
	{
		int compt1 = -1;
		int compt2 = 0;
		if(p1 - p2 == 1 || p1 - p2 == -1)
		{
			// si le coup est un _
			while(compt1 != p1)
			{
				if(graphe.charAt(compt2) == '.')
				{
					compt1++;
				}
				compt2++;
			}
			compt2 += p1 - p2+1;
		}
		else
		{
			// si le coup est un |
			while(compt1 != p1)
			{
				if(graphe.charAt(compt2) == '.')
				{
					compt1++;
				}
				compt2++;
			}
			if (p1 < p2)
			{
				compt2 += largeur+largeur-1;
			}
			else
			{
				compt2 -= largeur+largeur-1;
			}
		}
		return compt2;
	}
	
	/**
	 * verifie si le coup est possible
	 * @param p1 un des 2 point jouer
	 * @param p2 l'autre
	 * @return si le coup est possible
	 */
	public boolean coupPossible(int p1, int p2)
	{
		boolean res = true;
		if((Math.abs(p1 - p2) == this.largeur) || (Math.abs(p1 - p2) == 1))
		{
			if(graphe.charAt(this.parcour(p1, p2)) == '-' || graphe.charAt(this.parcour(p1, p2)) == '|')
			{
				res = false;
			}
		}
		else
		{
			res = false;
		}
		
		return res;
	}
	
	/**
	 * change un charactere lorsque l'on joue
	 * @param p1 un des 2 point jouer
	 * @param p2 l'autre
	 * @param tour tour du joueur qui agit
	 * @return le tour du joueur si il a changer ou non (marquer un point ou non)
	 */
	public int coup (int p1, int p2, int tour)
	{
		int res = tour;
		char c = ' ';
		int pos = this.parcour(p1, p2);
		System.out.println(pos);
		// choix du charactere a mettre
		if (p1 - p2 == -1 || p1 - p2 == 1)
		{
			c = '-';
		}
		else
		{
			c ='|';
		}
		graphe = graphe.substring(0,pos) + c +graphe.substring(pos+1); // insere le charactere a la place voulu
		return res;
	}
	
	public String toDot(int i)
	{
		String s="N"+i+" [label=\"N"+i+":V=7\n";
		String res = graphe.replaceAll("\\n", "\\\\n");
		s+= res;
		s+="\"]";
		return s;
	}
	
	
	
	public String getGraphe() 
	{
		return graphe;
	}


	public void setGraphe(String graphe)
	{
		this.graphe = graphe;
	}


	public int getLongueur()
	{
		return longueur;
	}


	public void setLongueur(int longueur) 
	{
		this.longueur = longueur;
	}


	public int getLargeur() 
	{
		return largeur;
	}


	public void setLargeur(int largeur) 
	{
		this.largeur = largeur;
	}
	
	public static void main (String [] args){
		Graphe a = new Graphe(4,4,1);
		System.out.print(a.graphe);
		a.coup(1, 5, 1);
		System.out.print(a.graphe);
	}
}
