package ericmm.maven;

public class App
{
  public static void main(String[] args)
  {
	  Database db = new Database("jdbc:sqlite:sample.db");
	  new Window(db);
  }
}