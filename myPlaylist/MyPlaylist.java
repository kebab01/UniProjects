
import java.util.Scanner;
import java.util.ArrayList;

class MyPlaylist {

	public static void main(String[] args){

		String name;
		String artist;
		String genre;
		int duration;
		int numOfSongs;

		//Scanner object
		Scanner input = new Scanner(System.in);
		// creating new instance of playlist
		Playlist playlist = new Playlist();

		System.out.println("\nWelcome to my playlist!");

		boolean loop = true;
		while(loop){

			//Display options
			System.out.println("\n1 - Add a new song");
			System.out.println("2 - List all songs");
			System.out.println("3 - Delete an existing song");
			System.out.println("4 - Search for a song");
			System.out.println("5 - Display total playtime");
			System.out.println("6 - exit");
			System.out.print("Please enter your choice: ");

			int choice = input.nextInt();
			System.out.print("\n");

			//Switch statement for user choices
			switch(choice){

				case(1):
					// Add song
					input.nextLine();
					System.out.println("Title of song:");
					name = input.nextLine();

					System.out.println("Name of artist:");
					artist = input.nextLine();

					System.out.println("Type of song:");
					genre = input.nextLine();

					System.out.println("Duration (seconds): ");
					duration = input.nextInt();

					playlist.addSong(name, artist, genre, duration);

					break;

				case(2):
					//list all songs
					numOfSongs = playlist.getNumOfSongs();

					for (int i = 0; i < numOfSongs; i ++){
						playlist.printSong(i);

						//Checks if song is not last song in list, if so skips a line
						if (i+1 != numOfSongs){

							System.out.println();
						}
					}

					break;

				case(3):
					//Delete song
					numOfSongs = playlist.getNumOfSongs();
					System.out.printf("There are %d song(s) in your library:\n", numOfSongs);

					for(int i = 0; i < numOfSongs; i++){

						System.out.printf("%d: %s\n", i + 1, playlist.getSongName(i));
					}

					System.out.print("Which song would you like to delete (please input the index)?: ");
					int selection = input.nextInt();
					int index = selection - 1;

					name = playlist.getSongName(index);
					playlist.deleteSong(index);
					System.out.printf("%s is removed from your library.\n", name);
					break;

				case(4):
					//Search for a song
					input.nextLine();
					System.out.print("Please enter title of song: ");
					name = input.nextLine();

					index = playlist.getSongIndex(name);

					if (index != -1){

						System.out.println("The song was found in your library, here are the details: ");
						System.out.printf("\nTrack %d\n", index + 1);
						playlist.printSong(index);
					}
					else{

						System.out.println("Song not found");
					}

					break;
				case(5):
					//display total playtime;
					numOfSongs = playlist.getNumOfSongs();
					duration = 0;
					int mins;
					int seconds;

					for (int i = 0; i < numOfSongs; i ++)
						duration += playlist.getDuration(i);

					mins = duration/60;
					seconds = duration%60;

					System.out.printf("The total playtime for %d song(s) (", numOfSongs);

					for (int i = 0; i < numOfSongs; i++){
						System.out.print(playlist.getSongName(i));

						//checks if index is for last song in list, if not, prints a comma
						if (i+1 != numOfSongs){

							System.out.print(", ");
						}
					}

					System.out.printf(") is: %d minutes and %d seconds\n", mins, seconds);
					break;
				case(6):

					System.out.println("Thank you. Bye!");
					loop = false;
					break;
				
			}

		}

	}
}

class Playlist{

	private ArrayList<String> nameOfSongs = new ArrayList<String>();
	private ArrayList<String> artitsOfSongs = new ArrayList<String>();
	private ArrayList<String> genreOfSongs = new ArrayList<String>();
	private ArrayList<Integer> durationOfSongs = new ArrayList<Integer>();

	public void addSong(String name, String artist, String genre, int duration){

		nameOfSongs.add(name);
		artitsOfSongs.add(artist);
		genreOfSongs.add(genre);
		durationOfSongs.add(duration);
	}

	public void deleteSong(int index){

		nameOfSongs.remove(index);
		artitsOfSongs.remove(index);
		genreOfSongs.remove(index);
		durationOfSongs.remove(index);
	}

	public String getSongName(int index){

		String name = nameOfSongs.get(index);

		return name;
	}

	public int getDuration(int index){

		int duration = 0;

		duration = durationOfSongs.get(index);

		return duration;
	}

	public int getNumOfSongs(){

		return nameOfSongs.size();
	}

	public int getSongIndex(String name){

		int index = -1;
		int numOfSongs = getNumOfSongs();

		//Cicles through all songs in array
		for (int i = 0; i < numOfSongs; i++){
			if (nameOfSongs.get(i).equals(name) == true){
				index = i;
			}
		}
		return index;
	}

	public void printSong(int index){

		System.out.println("Title of song: " + nameOfSongs.get(index));
		System.out.println("Name of artist: " + artitsOfSongs.get(index));		
		System.out.println("Type of song: " + genreOfSongs.get(index));
		System.out.printf("Duration (seconds): %d\n ", durationOfSongs.get(index));
	}
}















