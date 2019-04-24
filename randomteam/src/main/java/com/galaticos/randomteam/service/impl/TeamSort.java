package com.galaticos.randomteam.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.galaticos.randomteam.entity.ListPlayer;
import com.galaticos.randomteam.entity.Player;

/**
 * Class to sort teams
 * 
 * @author <a href="mailto:willian.ribeiro@gmail.com">Willian Ribeiro</a>
 * 
 */

public class TeamSort {

    private ListPlayer[] lPlayers;
    private Map<Float,Player> map;
    private int nextTeam, nextStep;
    private ArrayList<Player>[] lTeams;
    
    private int qtdPlayers, qtdTeams, maxGrade;
    private boolean showPosition;
    public final String QTD_PLAYERS = "QTD_JOGADORES";
    public final String QTD_TEAMS = "QTD_TIMES";
    public final String MAX_GRADE = "MAIOR_NOTA";
    public final String SHOW_POSITION = "MOSTRAR_POSICAO";
    
    public TeamSort() {
        this.qtdPlayers = 16;
        this.qtdTeams = 2;
        this.maxGrade = 5;
        this.showPosition = false;
        
        createTeams();
    }
    
    public void addPlayer(ArrayList<Player> al) {
        if (al != null && al.size() > 0) {
            for (int i = 0; i < al.size(); i ++) {
                Player p = al.get(i);
                (lPlayers[(int) (p.getGrade() - 1)]).addPlayer(p);
            }
        }
    }
    
    public void addPlayer(Player p) {
        if (p != null) {
            (lPlayers[(int) (p.getGrade() - 1)]).addPlayer(p);
        }
    }
    
    public float getTotalGrade() {
        float grade = 0;
        
        for (int i = 0; i < lPlayers.length; i++) {
            grade += lPlayers[i].getTotalGrade();
        }
        
        return grade;
    }
    
    public int getQtdPlayer() {
        int numPlayer = 0;
        
        for (int i = 0; i < lPlayers.length; i++) {
            numPlayer += lPlayers[i].getQtdPlayer();
        }
        
        return numPlayer;
    }
    
    public boolean readParams(String line) {
        if (line == null || line.equals("")) {
            return false;
        }
        
        if (line.startsWith("#")) {
            return false;
        }
        
        String param = line.substring(0, line.indexOf("="));
        
        if (line.startsWith(param)) {
            try {
                if (param.equals(QTD_PLAYERS)) {
                    this.qtdPlayers = (new Integer(line.substring(line.indexOf("=") + 1))).intValue();
                    return true;
                }
                
                if (param.equals(QTD_TEAMS)) {
                    this.qtdTeams = (new Integer(line.substring(line.indexOf("=") + 1))).intValue();
                    return true;
                }
                
                if (param.equals(MAX_GRADE)) {
                    this.maxGrade = (new Integer(line.substring(line.indexOf("=") + 1))).intValue();
                    createTeams();
                    return true;
                }
                
                if (param.equals(SHOW_POSITION)) {
                    this.showPosition = (line.substring(line.indexOf("=") + 1).equals("S"));
                    return true;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        
        return false;
    }
    
    public int getQtdFilePlayers() {
        return this.qtdPlayers;
    }
    
    public void setQtdFilePlayers(int qtdPlayers) {
        this.qtdPlayers = qtdPlayers;
    }
    
    public int getQtdTeams() {
        return this.qtdTeams;
    }
    
    public void setQtdTeams(int qtdTeams) {
        this.qtdTeams = qtdTeams;
    }
    
    public int getMaxGrade() {
        return this.maxGrade;
    }
    
    public void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
    }
    
    public boolean getShowPosition() {
        return this.showPosition;
    }
    
    public void setShowPosition(boolean showPosition) {
        this.showPosition = showPosition;
    }
    
    public static Player createPlayer(String line, boolean showPosition) {
        // verificando jogadores comentados
        if (line.startsWith("#")) {
            return null;
        }
        
        // pattern
        Pattern p = Pattern.compile("(\\w+) (\\d.*\\d*) (\\w)");
        Matcher m = p.matcher(line);
        
        if (!m.matches()) {
            return null;
        }
        
        String name = m.group(1);
        float grade = (new Float(m.group(2))).floatValue();
        String sPosition = m.group(3);
        
        if (grade < 1 || grade > 5) {
            System.out.println("A nota deve ser um valor entre 1 e 5");
            return null;
        }
        
        if (!(sPosition != null && (sPosition.equalsIgnoreCase("A") || sPosition.equalsIgnoreCase("D") || sPosition.equalsIgnoreCase("M") || sPosition.equalsIgnoreCase("G")))) {
            System.out.println("As posicoes possiveis sao: A=Ataque, D=Defesa, M=Meio Campo ou G=Goleiro");
            return null;
        }
        
        int iPosition = 0;
        if (sPosition.equalsIgnoreCase("G")) {
            iPosition = Player.GOALKEEPER;
        }
        if (sPosition.equalsIgnoreCase("A")) {
            iPosition = Player.ATTACK;
        }
        if (sPosition.equalsIgnoreCase("M")) {
            iPosition = Player.MIDFIELD;
        }
        if (sPosition.equalsIgnoreCase("D")) {
            iPosition = Player.DEFENSE;
        }
        
        return new Player(name, grade, iPosition, showPosition);
    }
    
    public void sortTeamAsc() {
        startSort();
        
        for (int i = 0; i < lPlayers.length; i++) {
        	sortTeam(lPlayers[i].getAttackList());
        	sortTeam(lPlayers[i].getDefenseList());
        	sortTeam(lPlayers[i].getGoalList());
        	sortTeam(lPlayers[i].getMidfieldList());
        	
        }
        
        finalizeSort();
    }
    
    public void sortTeamDesc() {
        startSort();
        
        for (int i = lPlayers.length - 1; i >= 0; i--) {
        	sortTeam(lPlayers[i].getGoalList());
        	sortTeam(lPlayers[i].getDefenseList());
        	sortTeam(lPlayers[i].getMidfieldList());
        	sortTeam(lPlayers[i].getAttackList());
            
        }
        
        finalizeSort();
    }
    
    public String getTeam(int teamNum){
        
        if (lTeams == null || teamNum < 1 || teamNum > this.qtdTeams) {
            return null;
        }
        
        float totalGrade = 0;
        Collections.sort(lTeams[teamNum - 1]);
        
        for (int i = 0; i < lTeams[teamNum - 1].size(); i++) {
            totalGrade = totalGrade + lTeams[teamNum - 1].get(i).getGrade();
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        
        return "\nQtd Jogadores = " + lTeams[teamNum - 1].size() + "\nTime " + teamNum + ": " + lTeams[teamNum - 1] + " Nota Time = " + df.format(totalGrade);
    }
    
    public float getTeamTotalGrade(int teamNum){
    	float totalGrade = 0;
    	
    	for (int i = 0; i < lTeams[teamNum - 1].size(); i++) {
            totalGrade = totalGrade + lTeams[teamNum - 1].get(i).getGrade();
        }
    	return totalGrade;
    	
    }
    
    private void createTeams() {
        lPlayers = new ListPlayer[maxGrade];
        for (int i = 1; i <= maxGrade; i++) {
            lPlayers[i-1] = new ListPlayer(i);
        }
    }
    
    @SuppressWarnings("unchecked")
	private void startSort() {
        if (this.qtdTeams <= 0) {
            throw new IllegalArgumentException("Quantidade inv�lidade de times");
        }
        
        nextTeam = 1;
        nextStep = 1;
        lTeams = new ArrayList[qtdTeams];
        
        for (int i = 0; i < this.qtdTeams; i++) {
            lTeams[i] = new ArrayList<Player>();
        }
    }
    
    private void finalizeSort() {
        for (int i = 0; i < this.qtdTeams; i++) {
            Collections.sort(lTeams[i]);
        }
    }
    
    private void sortTeam(ArrayList<Player> ap) {
        ArrayList<Player> sorted = new ArrayList<Player>();
        
        
        while (ap.size() > sorted.size()) {
            int numDefesaTeam1 = 0;
            int numAttackTeam1 = 0;
            int numGoalTeam1 = 0;
            int numMidfieldTeam1 = 0;
            
            int numDefesaTeam2 = 0;
            int numAttackTeam2 = 0;
            int numGoalTeam2 = 0;
            int numMidfieldTeam2 = 0;
            
            int random = (int) ((int) (Math.random() * 100 % (ap.size())));
            
            Player p = ap.get(random);
            
            if (sorted.contains(p)) {
                continue;
            }
            
            for(Player player : lTeams[0]){
            	if(player.getPosition() == 1){
            		numAttackTeam1++;
            	}
            	if(player.getPosition() == 2){
            		numDefesaTeam1++;
            	}
            	if(player.getPosition() == 3){
            		numGoalTeam1++;
            	}
            	if(player.getPosition() == 4){
            		numMidfieldTeam1++;
            	}
            }
            
            for(Player player : lTeams[1]){
            	if(player.getPosition() == 1){
            		numAttackTeam2++;
            	}
            	if(player.getPosition() == 2){
            		numDefesaTeam2++;
            	}
            	if(player.getPosition() == 3){
            		numGoalTeam2++;
            	}
            	if(player.getPosition() == 4){
            		numMidfieldTeam2++;
            	}
            }
            
            //this.checkPlayersByPosition(lTeams[0], numDefesaTeam1, numAttackTeam1);
            //this.checkPlayersByPosition(lTeams[1], numDefesaTeam2, numAttackTeam2);
            
            if(p.getPosition() == 1){
            	if(numAttackTeam1 <= numAttackTeam2 && lTeams[0].size() <= lTeams[1].size()){
            		lTeams[0].add(p);
            	}else{
            		lTeams[1].add(p);
            	}
            }
            
            if(p.getPosition() == 2){
            	if(numDefesaTeam1 <= numDefesaTeam2 && lTeams[0].size() <= lTeams[1].size()){
            		lTeams[0].add(p);
            	}else{
            		lTeams[1].add(p);
            	}
            }
            
            if(p.getPosition() == 3){
            	if(numGoalTeam1 <= numGoalTeam2 && lTeams[0].size() <= lTeams[1].size()){
            		lTeams[0].add(p);
            	}else{
            		lTeams[1].add(p);
            	}
            }
            
            if(p.getPosition() == 4){
            	if(numMidfieldTeam1 <= numMidfieldTeam2 && lTeams[0].size() <= lTeams[1].size()){
            		lTeams[0].add(p);
            	}else{
            		lTeams[1].add(p);
            	}
            }

            
            //(lTeams[nextTeam - 1]).add(p);
            
            sorted.add(p);
            
            // Usado para sorteio em cenario com mais de 2 times.
//            nextTeam += nextStep;
//            
//            if (nextTeam > 2) {
//                nextTeam = 2;
//                nextStep = -1;
//            }
//            
//            if (nextTeam == 0) {
//                nextTeam = 1;
//                nextStep = 1;
//            }
        }
    }

    
//	private void checkPlayersByPosition(ArrayList<Player> lTeams, int numDefesa, int numAttack) {
//        for(Player player : lTeams){
//        	if(player.getPosition() == 1){
//        		numAttack++;
//        	}
//        	if(player.getPosition() == 2){
//        		numDefesa++;
//        	}
//        }
//		
//	}
    
}
