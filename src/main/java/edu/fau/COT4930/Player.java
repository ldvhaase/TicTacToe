package edu.fau.COT4930;
/**
The Player class for the COT4930 project. This class must be extended for the project.
*/

public class Player
{
	private String name;

	/**
		The constructor creates a default Player object.
	*/
	public Player()
	{
		name = "";
	}
	/**
		The constructor creates a Player object with the specified name.
		@param n represents the name of the Player.
	*/
	public Player(String n)
	{
		name = n;
	}

	/**
		Method to retrieve the name of the player.
		@return a String representing the name of the Player.
	*/
	public String getName()
	{ // return the name
		return name;
	}

	/**
		Method to set the Players name.
		@param n represents the name of the Player.
	*/
	public void setName(String n)
	{ // set the PLayers name
		name = n;
	}
}
