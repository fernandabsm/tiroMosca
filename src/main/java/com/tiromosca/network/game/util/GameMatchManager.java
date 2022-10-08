package com.tiromosca.network.game.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameMatchManager {

    public static List<Integer> getResultOfAMatch(String attempt, String aim) {
        // Se o palpite for exatamente igual ao esperado, o jogador acertou 3 moscas
        if (attempt.equals(aim)) {
            return List.of(0, 3);
        }
        
        List<Character> individualCharactersAim = new ArrayList<>();
        individualCharactersAim.add(aim.charAt(0));
        individualCharactersAim.add(aim.charAt(1));
        individualCharactersAim.add(aim.charAt(2));

        List<Character> individualCharactersAttempt = new ArrayList<>();
        individualCharactersAttempt.add(attempt.charAt(0));
        individualCharactersAttempt.add(attempt.charAt(1));
        individualCharactersAttempt.add(attempt.charAt(2));

        int shot = 0, fly = 0;

        // Verificar quantos / se há tiros na mosca
        for(int i = 0; i < 3; i ++) {
          if (individualCharactersAttempt.get(i).equals(individualCharactersAim.get(i))) {
              fly++;
          }
        }

        // Verifica quantos / se há tiros acertados
        for(int i = 0; i < 3; i ++) {
            for(int j = 0; j < 3; j++) {
                if (individualCharactersAttempt.get(j).equals(individualCharactersAim.get(i)) && i != j) {
                    shot++;
                }
            }
        }

        LinkedList<Integer> result = new LinkedList<>();
        result.addFirst(shot);
        result.addLast(fly);

        return result;
    }

    public static List<String> formatResultOfAMatch(Integer shot, Integer fly) {
        String shots = shot + " tiros";
        String flies = fly + " moscas";

        LinkedList<String> result = new LinkedList<>();
        result.addFirst(shots);
        result.addLast(flies);

        return result;
    }

//    public static void main(String[] args) {
//        String aim = "111";
//        String target = "111";
//
//        List<Integer> list = getResultOfAMatch(target, aim);
//        List<String> result = formatResultOfAMatch(list.get(0), list.get(1));
//        System.out.println(result.get(0));
//        System.out.println(result.get(1));
//
//    }
}
