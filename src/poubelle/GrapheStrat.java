package poubelle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class GrapheStrat 
{
	private Graphe initial;
	private Graphe suivant;
	private String lien;
	private String nom;
	private File fic;
	
	
	
	public GrapheStrat(Graphe g)
	{
		lien = "";
		nom = "strat.dot";
		File f = new File (nom);
		fic = f;
		int compteur = 0;
		int largeur = g.getLargeur()+2;
		int longueur = g.getLongueur();
		int changement = 0;
		boolean alternance = false;
		try 
		{
			Scanner sc = new Scanner(f);
			try
			{
				FileWriter fw = new FileWriter(f);
				PrintWriter pw = new PrintWriter(fw);
				pw.print("digraph default {\n graph[labelloc=\"t\"  fontsize=16 fontcolor=\"blue\"\n label=\"Graphe des configurations d'un jeu de pipopipette\n et calcul d'une stratégie gagnante\n\n\"]\n");
				pw.println("\n");
				pw.println(initial.toDot(0));
				while (!sc.nextLine().equals(g.toDot(0)))
				{
					sc.nextLine();
				}
				
				while(sc.hasNextLine())
				{
					String casDeBase = sc.next();
					for (int i=12; i<casDeBase.length(); i++)
					{
						String newCas = casDeBase;
						if(newCas.charAt(i)=='.')
						{
							alternance = false;
						}
						if(newCas.charAt(i)=='|')
						{
							alternance = true;
							changement = 0;
						}
						changement++;
						if (newCas.charAt(i)==' ')
						{
							if (alternance)
							{
								if(changement % 2 == 0)
								{
									newCas = newCas.substring(0,i) + '|' +newCas.substring(i+1);
									if (!verification(newCas))
									{
										pw.println(toDot(newCas,compteur));
										compteur++;
										changement++;
									}
									else
									{
										int e = positionEspace(casDeBase);
										lien += casDeBase.substring(0, e-1) + "->N"+compteur;
									}
								}
								else
								{
									changement++;
								}
							}
							else
							{
								newCas = newCas.substring(0,i) + '_' +newCas.substring(i+1);
								if(!verification(newCas))
								{
									pw.println(toDot(newCas,compteur));
									compteur++;
								}
								else
								{
									int e = positionEspace(casDeBase);
									lien += casDeBase.substring(0, e-1) + "->N"+compteur;
								}
							}
						}
					}
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean verification (String s)
	{
		boolean res = false;
		Scanner sc;
		try 
		{
			sc = new Scanner(this.fic);
			String t = "";
			while (sc.hasNextLine())
			{
				t = sc.nextLine();	
				String verif = t.substring(positionPoint(t)-1);
				t = s.substring(positionPoint(s)-1);
				if(s == t )
				{
					res = true;
				}
			}
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return res;
		
		
	}
	
	public static int positionPoint (String s)
	{
		int res = 0;
		while (s.charAt(res)!='.')
		{
			res++;
		}
		return res;
	}
	
	public static int positionEspace (String s)
	{
		int res = 0;
		while (s.charAt(res)!=' ')
		{
			res++;
		}
		return res;
	}
	
	
	public static String toDot (String s, int i)
	{
		
			String res="N"+i+" [label=\"N"+i+":V=7\n";
			String r = s.replaceAll("\\n", "\\\\n");
			return res+= r + "\"]";
	}
	
	public static void main (String [] args)
	{
		Graphe a = new Graphe(4,4,1);
		GrapheStrat g = new GrapheStrat(a);
	}
	
}
