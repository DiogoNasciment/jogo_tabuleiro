import java.util.*;
import java.io.IOException;

import javax.lang.model.util.ElementScanner14;

public class JogoTabuleiro {

    public static void main(String args[]){
        Tabuleiro partida = new Tabuleiro();
        Menu menu = new Menu();
        Jogador newJogador;
        Scanner leitor = new Scanner(System.in);
        
        int dados, opcao = 0; 
        boolean checkGanhador = false;
        String comando;


            while(opcao != 1){
                menu.mostrarMenuInicial();
                opcao = leitor.nextInt();
                clearBuffer(leitor);
                menu.ClearConsole();
                menu.mostrarTransição(1100);

                if(opcao==1){
                    if(partida.getJogadores().size()>1){
                        break;
                    }
                    else{
                        menu.enviarMensagemErro();
                        opcao=0;
                    }
                }
                else{
                    newJogador = partida.definirJogador();

                    if(partida.addJogador(newJogador)){
                        newJogador.setCor(partida.getJogadores().indexOf(newJogador));

                        menu.mostrarTelaDeAdição(newJogador);
                        menu.mostrarTransição(4500);
                        menu.ClearConsole();
                    } else {  
                    System.out.println("Limite de jogadores atingido, você só pode criar 6 jogadores");
                    }

                    menu.mostrarDadosDeJogadores(partida.getJogadores());
                    System.out.println("");

                }
                
            }

        do{
            partida.setRodada(1);
            System.out.println("---------------   RODADA " + partida.getRodada()+    "   ---------------");
            for (Jogador jogador : partida.getJogadores()) {
                
                if(jogador.podeJogar == false){
                    partida.checkCasasEspeciais(jogador);
                    continue;
                               }
                do{
                    if(jogador.isJogaDadosNovamente()){  
                        System.out.println("---------------- "+jogador.getCor() + " joga novamente ----------------");
                        menu.mostrarTransição(1200);
                    }
                    menu.iniciarJogadas(jogador);
                    menu.mostrarJogadores(partida.getJogadores());

                    System.out.println("Aperte enter para rolar os dados");
                    comando = leitor.nextLine();
                    dados = jogador.jogarDados();
                    jogador.setPosição(dados);
                //mostrar posição 
                partida.checkCasasEspeciais(jogador);
                menu.ClearConsole();
                }while(jogador.isJogaDadosNovamente());

                if(jogador.posição >= 40){  
                    jogador.setPosição();
                    jogador.setPosição(40);
                    checkGanhador = true;
                    break;
                }
                menu.mostrarJogadores(partida.getJogadores());
            }

        }while(checkGanhador != true);
        System.out.println("Fim de jogo");
        leitor.close();
    }   
    private static void clearBuffer(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }
}
