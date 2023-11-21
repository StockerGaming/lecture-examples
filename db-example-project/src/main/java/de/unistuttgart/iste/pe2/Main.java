package de.unistuttgart.iste.pe2;

import java.util.List;

import de.unistuttgart.iste.pe2.Assignment.LetterWithIDs;
import de.unistuttgart.iste.pe2.Assignment.ORMAssignment;
import de.unistuttgart.iste.pe2.Assignment.SumAndAverage;

public class Main {

	public static void main(String[] args) {
		ORMAssignment ormAssignment = new ORMAssignment();
		System.out.println(ormAssignment.getLetters());

		List<LetterWithIDs> letterWithIDs = ormAssignment.getIDs();
		for(LetterWithIDs letterWithID: letterWithIDs){
			System.out.println(letterWithID);
		}

		SumAndAverage sumAndAverage = ormAssignment.sumAndAverage();
		System.out.println(sumAndAverage);
	}
}
