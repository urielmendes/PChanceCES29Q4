package source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;

public class CSVReader {

	public static void main(String[] args) {

		final String slash = File.separator;
		final String csvFile = slash + "Users"+slash+"mkyong"+slash+"csv"+slash+"users.csv";
		final String cvsSplitBy = ",";
		String line = "";
		User[] users = new User[1];
		
		//o int i era usado apenas no escopo do while, então
		//criou um for para diminuir o escopo dele
		
		/*Para prevenir ataques DoS, foi imposto um limite de 2KB no buffer
		Desse modo, um usuario malicioso nao podera passar linhas muito grandes*/
		
		//O tamanho de users agora eh definido em tempo de execucao
		final int max=10;
		users = (User[]) changeArraySize(users, max);
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile),2048)) {
			//Aqui eh garantido que o tamanho do vetor users nao sera excedido
			for (int i = 0; line != null && i < max -1; line = br.readLine(), i++) {

				// use comma as separator
				String[] usersLine = line.split(cvsSplitBy);
				User user = new User((usersLine[0]), usersLine[1], usersLine[2], 
						usersLine[3], usersLine[4]);
				users[i]=user;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//O tamanho do array agora pode ser modificado em tempo de execucao
	//da forma que for conveniente para o desenvolvedor
	public static Object changeArraySize(Object obj, int len) {
		Class<?> arr = obj.getClass().getComponentType();
		Object newArray = Array.newInstance(arr, len);
 
		//do array copy
		int co = Array.getLength(obj);
		System.arraycopy(obj, 0, newArray, 0, co);
		return newArray;
	}
}


