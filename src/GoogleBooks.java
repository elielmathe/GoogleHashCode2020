
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoogleBooks {	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File input = new File("example_files/a_example.txt");
			BufferedReader br = new BufferedReader(new FileReader(input));
			String line = "";
			int i = 0;
			String firstLine[] = {};String secondLine[] = {};
			List<String> linesOfFile = new ArrayList<String>();
			
			int numberOfBooks = 0;
			int numberOfLibraries = 0;
			int numberOfDays = 0;
			int scoresOfBooks[] = {};// = {1,2,3,6,5,4};
			int libraryDescription[][] = {{}};// = {{5,2,2},{4,3,1}};
			int registrationPeriodOfLibraries[] = {};// = {2,3};
			//int booksInLibrariesWithScore[][][] = {{{0,1},{1,2},{2,3},{3,6},{4,5}},{{3,6},{2,3},{5,4},{0,1}}};
			int booksInLibraries[][] = {{}};// = {{0,1,2,3,4},{3,2,5,0}};
			int blindBooksScores[][] = {{1,2,3,6,5},{6,3,4,1}};
			int librariesCounter = 0;
			
			
			while((line = br.readLine()) != null) {
				String val[] = line.split(" ");
				if(i == 0) {
					numberOfBooks = Integer.valueOf(val[0]);
					numberOfLibraries = Integer.valueOf(val[1]);
					numberOfDays = Integer.valueOf(val[2]);
					registrationPeriodOfLibraries = new int[numberOfLibraries];
					libraryDescription = new int[numberOfLibraries][];
					booksInLibraries = new int[numberOfLibraries][];
				}else if(i == 1) {
					scoresOfBooks = new int[numberOfBooks];
					for(int l = 0;l < val.length; l++) {
						scoresOfBooks[l] = Integer.valueOf(val[l]);
					}
				}else {
					if(i % 2 == 0) {
						//System.out.println(librariesCounter + " / " + numberOfLibraries);
						if(librariesCounter == numberOfLibraries) continue;
						libraryDescription[librariesCounter] = 
								new int[val.length];
						for(int l = 0;l < val.length;l++) {
							libraryDescription[librariesCounter][l] = Integer.valueOf(val[l]);
							if(l == 1) {
								//System.out.println("Here we go " + val[l]); 
								registrationPeriodOfLibraries[librariesCounter] = Integer.valueOf(val[l]);
							}
						}
						librariesCounter++;
					}else {
						booksInLibraries[librariesCounter - 1] = 
								new int[val.length];
						for(int l = 0;l < val.length;l++) {
							booksInLibraries[librariesCounter - 1][l] = Integer.valueOf(val[l]);
						}
					}
				}
				//linesOfFile.add(line);
				i++;
			}
			br.close();
			
			
			
			/*int maxNumber = Integer.valueOf(firstLine[0]);
			int numberOfTypes = Integer.valueOf(firstLine[1]);
			int types[] = new int[numberOfTypes];
			for(int c = 0;c < secondLine.length;c++) {
				types[c] = Integer.valueOf(secondLine[c]);
			}*/
			String output = solvingIssue(numberOfBooks,numberOfLibraries,
					numberOfDays,scoresOfBooks,libraryDescription,
					registrationPeriodOfLibraries,
					booksInLibraries,
					blindBooksScores);
			System.out.println(output);
			
			//String out = provideBestPizza(numberOfTypes,types,maxNumber);
			FileWriter fwriter = new FileWriter("output_files/a_example.out");
			fwriter.write(output);
			fwriter.close();
			//System.out.println();
		}catch(FileNotFoundException fException) {
			fException.printStackTrace();
		}catch(IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	public static String solvingIssue(
			int numberOfBooks,int numberOfLibraries,
			int numberOfDays,int[] scoresOfBooks,int[][] libraryDescription,
			int[] registrationPeriodOfLibraries,
			int[][] booksInLibraries,
			int[][] blindBooksScores
			) {
		
		
		
		int librariesRegistration[] = new int[numberOfLibraries];
		
		int numberOfLibrariesToBeSignedUp = 0;
		
		for(int i = 0;i < numberOfDays;numberOfLibrariesToBeSignedUp++) {
			if(numberOfLibrariesToBeSignedUp > registrationPeriodOfLibraries.length - 1) break;
			i += registrationPeriodOfLibraries[numberOfLibrariesToBeSignedUp];
			librariesRegistration[numberOfLibrariesToBeSignedUp] = registrationPeriodOfLibraries[numberOfLibrariesToBeSignedUp];
		}
		System.out.println(Arrays.toString(registrationPeriodOfLibraries));
		//int bestLibraries[] = librariesRegistration;
		Arrays.sort(librariesRegistration);
		
		String output = "";
		output +=  registrationPeriodOfLibraries.length + "\n";
		//System.out.println("We can register " + registrationPeriodOfLibraries.length + " libs, which are " + Arrays.toString(librariesRegistration));
		
		//Getting the books to register
		int remainingRegDays = 0;
		for(int i = librariesRegistration.length - 1; i >= 0; i--) {
			int indexOfLibrary = Arrays.binarySearch(registrationPeriodOfLibraries, librariesRegistration[i]);
			System.out.println( "--> " + librariesRegistration[i]);
			if(indexOfLibrary == -1 ) continue;
			System.out.println("Index of array is " + indexOfLibrary);
			int numberOfBooksToScan = (int) (numberOfDays - 
					libraryDescription[indexOfLibrary][1]) *
					libraryDescription[indexOfLibrary][2] ; 
			System.out.println(indexOfLibrary + " " + numberOfBooksToScan);
			output += indexOfLibrary + " " + numberOfBooksToScan + "\n";
			int scoresOfArray[] = Arrays.copyOfRange(scoresOfBooks, 0, 2);
			//System.out.println("Max length is " + blindBooksScores.length);
			Arrays.sort(blindBooksScores[indexOfLibrary]);
			for(int j = blindBooksScores[indexOfLibrary].length - 1,k = 0;j >=0 ;j--,k++) {
				if(k < numberOfBooksToScan) {
					output += booksInLibraries[indexOfLibrary][Arrays.binarySearch(blindBooksScores[indexOfLibrary],blindBooksScores[indexOfLibrary][j])] + " ";
					//System.out.print(booksInLibraries[indexOfLibrary][Arrays.binarySearch(blindBooksScores[indexOfLibrary],blindBooksScores[indexOfLibrary][j])] + " ");
				}
			}
			numberOfDays -= libraryDescription[indexOfLibrary][1];
			output += "\n";
			
		}
		
		return output;
	}
	
}
