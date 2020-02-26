/*
 * Notes 0.0.0
 *
 * Anthony Mesa 2019
 *
 * This java app is a short-form note taking app for the terminal environment.
 *	- Notes can be saved to a category
 *	- notes can be retrieved from a category
 * 
 * - List all clips
 */

import java.util.Arrays;
import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.lang.StringBuilder;

public class Application{
	
	public static String clip_name;
	
	public static void main(String args[]){
		
		Vector<String> args_vector = new Vector<String>(Arrays.asList(args));
		if (args_vector.size() > 0){
			try{
				clip_name = args_vector.elementAt(1);
			} catch (ArrayIndexOutOfBoundsException e) {
				if (args_vector.elementAt(0) == "h"){
					displayHelp();
				}
			}
			
			ClipObject clip_object = new ClipObject(clip_name);

			switch(args_vector.elementAt(0)){
				case "-g":
					clip_object.showContents();
					break;
				case "-a":
					args_vector.remove(0);
					args_vector.remove(0);					
					StringBuilder bullet = new StringBuilder();
					for (int i = 0; i < args_vector.size(); i++){
						if (i == args_vector.size() - 1){
							bullet.append(args_vector.elementAt(i));
						} else {
							bullet.append(args_vector.elementAt(i) + " ");
						}
					}						
					clip_object.setBullet(bullet.toString() + "\n");
					break;
				case "-dc":
					clip_object.deleteClip();
					break;
				case "-db":
					int index = Integer.parseInt(args_vector.elementAt(2));
					try{
						clip_object.deleteBullet(index);
					} catch (ArrayIndexOutOfBoundsException e){
						System.out.println("\n --- Bullet does not exist");
					}
					break;
				case "-h":
					displayHelp();
					break;
				default:
					System.out.println("\n --- Command not recognized, '-h' for list of commands.");
			}
		} else {
			System.out.println("\n --- No commands provided, '-h' for list of commands.");
		}
	}

	private static void displayHelp(){

		String help = "\n" +
			
			"	Bullet Notes 0.0.0 | Anthony Mesa 2019\n\n" +
			
			"	Bullet Notes allows users to save and retrive notes in the\n" +
			"	command line. A Clip is a collection of notes, all notes\n" +
		       	"	must belong to a Clip.\n\n" +

			"	Syntax:\n\n" +
				
			"		java bullet {COMMAND} {CLIP} {BULLET}\n\n" +

			"	The {BULLET} Variable is not a single string, but rather the\n" + 
			"	entire text after the Clip, e.g.\n\n" +

			"		java Bullet {-a} {Todo} {Pick up the newspaper}\n\n" +	

			"	Commands:\n\n" +

			"		-g {CLIP}\n" +
			"			Gets and displays all bullet notess in the desigated Clip\n" +
			"		-a {CLIP} {BULLET} \n" +
			"			Adds a bullet note to the designated Clip\n" +
			"		-dc {CLIP}\n" +
			"			Deletes designated Clip and all its data\n" +
			"		-db {CLIP} {INDEX_OF_BULLET}\n" +
			"			Deletes the Bullet within the specified clip by using\n" +
			"			the Bullet's index in the clip (starting at 1)\n";
		System.out.println(help);
	}
}

class ClipObject{

	private String clip_path;
	private String clip_name;
	private Vector<String> current_clip;
	private String clip_string;
	private File clip_file;
	private Vector<String> bullets;

	ClipObject(String name){
		clip_name = name;
		clip_path = "./res/data/" + clip_name;

		clip_file = new File(clip_path);
	}

	public void setBullet(String bullet){
		try{
			FileWriter writer = new FileWriter(clip_path, true);
			writer.write(bullet);
			writer.close();
		} catch (Exception e) {System.out.println(e);}
	}

	public void showContents(){
		bullets = getBullets();
		System.out.println();
		for(int i = 0; i < bullets.size(); i++){
			System.out.println(bullets.elementAt(i));
		}
	}

	public void deleteClip(){
		clip_file.delete();
	}

	public void deleteBullet(int index){
		bullets = getBullets();
		bullets.remove(index);

		deleteClip();
		for(int i = 1; i < bullets.size(); i++){
			setBullet(bullets.elementAt(i));
		}
	}

	public Vector<String> getBullets(){
		Vector<String> builder = new Vector<String>();
		Scanner current_clip_scanner;
		
		try{
			current_clip_scanner = new Scanner(clip_file);
		} catch (IOException e){
			System.out.println("\n --- Scanner Failed, Clip may not exist");
			return builder;
		}
		
		builder.add(" --- " + clip_name.toUpperCase());
		
		while(current_clip_scanner.hasNextLine()){
			builder.add("     - " + current_clip_scanner.nextLine());
		}
		current_clip_scanner.close();

		return builder;
	}
}