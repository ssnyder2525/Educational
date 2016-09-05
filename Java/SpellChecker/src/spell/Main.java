package spell;

import java.io.IOException;

import spell.ISpellCorrector.NoSimilarWordFoundException;

public class Main {

	public static void main(String[] args) throws IOException, NoSimilarWordFoundException {
		SpellCorrector spell = new SpellCorrector();
		spell.useDictionary(args[0]);
		String answer = spell.suggestSimilarWord(args[1]);
		System.out.println(answer);
	}

}
